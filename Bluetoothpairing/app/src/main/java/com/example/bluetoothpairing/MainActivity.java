package com.example.bluetoothpairing;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button listen,listdevices,send;
    ListView list;
    TextView msg,status;
    EditText writemessage;
    BluetoothAdapter bluetoothAdapter;
    SendReceive sendReceive;
    BluetoothDevice[] btarray;
    static final int STATE_LISTENING=1;
    static final int STATE_CONNECTING=2;
    static final int STATE_CONNECTED=3;
    static final int STATE_CONNECTION_FAILED=4;
    static final int STATE_MESSAGE_RECEIVED=5;
    int REQUEST_ENABLE_BLUETOOTH=1;
    private static final String APP_NAME="BluetoothChat";
    private static final UUID MY_UUID=UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listen=(Button) findViewById(R.id.listen_view);
        listdevices=(Button) findViewById(R.id.list_devices);
        send=(Button) findViewById(R.id.send_View);
        list=(ListView) findViewById(R.id.list_view);
        msg=(TextView) findViewById(R.id.msg);
        status=(TextView) findViewById(R.id.status_view);
        writemessage=(EditText)findViewById(R.id.write_message);
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableintent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableintent,REQUEST_ENABLE_BLUETOOTH);
        }
        implementListeners();
    }

    private void implementListeners() {
        listdevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> bt=bluetoothAdapter.getBondedDevices();
                String[] strings=new String[bt.size()];
                btarray=new BluetoothDevice[bt.size()];
                int index=0;
                if(bt.size()>0)
                {
                    for(BluetoothDevice device:bt)
                    {
                        btarray[index]=device;
                        strings[index]=device.getName();
                        index++;
                    }
                    ArrayAdapter<String>arrayAdapter= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    list.setAdapter(arrayAdapter);
                }
            }
        });
        listen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ServerClass serverClass=new ServerClass();
                serverClass.start();
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClientClass clientClass=new ClientClass(btarray[position]);
                clientClass.start();
                status.setText("Connecting");
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string=String.valueOf(writemessage.getText());
                sendReceive.write(string.getBytes());
            }
        });
    }
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case STATE_LISTENING:
                        status.setText("Listening");
                        break;
                case STATE_CONNECTING:
                    status.setText("Connecting");
                    break;
                case STATE_CONNECTED:
                    status.setText("Connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    status.setText("Connection_failed");
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuff=(byte[]) msg.obj;
                    String tempMsg=new String(readBuff,0,msg.arg1);
                    writemessage.setText(tempMsg);
                    break;
            }
            return true;
        }
    });
    private class ServerClass extends Thread{
        private BluetoothServerSocket ServerSocket;
        public ServerClass(){
            try {
                ServerSocket=bluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME,MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        public void run()
        {
            BluetoothSocket socket=null;
            while(socket==null)
            {
                try{
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTED;
                    handler.sendMessage(message);
                    socket=ServerSocket.accept();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTION_FAILED;
                    handler.sendMessage(message);
                }
                if(socket!=null)
                {
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTED;
                    handler.sendMessage(message);
                    sendReceive=new SendReceive(socket);
                    sendReceive.start();
                    break;
                }
            }
        }
    }
    private class ClientClass extends Thread
    {
        private BluetoothDevice device;
        private BluetoothSocket socket;
        public ClientClass(BluetoothDevice device1)
        {
            device=device1;
            try{
                socket=device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        public void run()
        {
            try {
                    socket.connect();
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTED;
                    handler.sendMessage(message);
                    sendReceive=new SendReceive(socket);
                    sendReceive.start();
            }catch(IOException e)
            {
                e.printStackTrace();
                Message message=Message.obtain();
                message.what=STATE_CONNECTION_FAILED;
                handler.sendMessage(message);
            }
        }
    }
    private class SendReceive extends Thread
    {
        private final BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket)
        {
            bluetoothSocket=socket;
            InputStream tempIN=null;
            OutputStream tempOut=null;
            try {
                tempIN = bluetoothSocket.getInputStream();
                tempOut = bluetoothSocket.getOutputStream();
            }catch(IOException e){
                e.printStackTrace();
            }
            inputStream=tempIN;
            outputStream=tempOut;
        }
        public void run()
        {
            byte[] buffer=new byte[1024];
            int bytes;
            while(true)
            {
                try {
                   bytes= inputStream.read(buffer);
                   handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void write(byte[] bytes)
        {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}