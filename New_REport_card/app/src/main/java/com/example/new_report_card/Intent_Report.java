package com.example.new_report_card;

import android.os.Bundle;

import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Intent_Report extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent__report);
        mTextView = (TextView) findViewById(R.id.text);
            ArrayList<SUB> sub = new ArrayList<>();
            sub.add(new SUB("Chemistry", 75));
            sub.add(new SUB("Maths", 95));
            sub.add(new SUB("Physics", 70));
            sub.add(new SUB("English", 85));
            sub.add(new SUB("Hindi", 80));
            ReportAdapter adapter = new ReportAdapter(this, R.layout.list_item,sub);

           ListView repo=(ListView) findViewById(R.id.reports);
           repo.setAdapter(adapter);
        }

    }
