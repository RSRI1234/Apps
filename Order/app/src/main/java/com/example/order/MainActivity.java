package com.example.order;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    int q = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {

       EditText name=(EditText) findViewById(R.id.input_name);
        String myname= name.getText().toString();
        CheckBox checkbox = (CheckBox) findViewById(R.id.whipped);
        boolean haswhipped = checkbox.isChecked();
        CheckBox choco = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean haschoco = choco.isChecked();
        int price=calculateprice(q);
        if(haschoco)
            price=price+q*5;
        if(haswhipped)
           price=price+q*1;
        String pricemessage = "Name:" +myname+ "\n Add WhippedCream? " + haswhipped + "\n Add chocolate? " + haschoco + "\nQuantity:" + q + "\n price is" + price;
        pricemessage = pricemessage + "\n thank you";
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "orderfor"+myname);
        intent.putExtra(Intent.EXTRA_TEXT, pricemessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

    }

    public int calculateprice(int quantity) {
        return quantity * 5;
    }

    public void increment(View view) {
        q = q + 1;
        display(q);
    }

    public void decrement(View view) {
        q = q - 1;
        display(q);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}



