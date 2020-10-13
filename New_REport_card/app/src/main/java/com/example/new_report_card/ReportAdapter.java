package com.example.new_report_card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ReportAdapter  extends ArrayAdapter<SUB> {
    Context context;
    int resource;
    List<SUB> words;

    public ReportAdapter(@NonNull Context context, int resource, @NonNull List<SUB> words) {
        super(context,resource,words);
        this.context = context;
        this.resource = resource;
        this.words = words;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item,null);
        TextView marktext = view.findViewById(R.id.mark_list_text);
        TextView subText = view.findViewById(R.id.sub_list_text);
        SUB currentWord = words.get(position);
        marktext.setText(currentWord.getMark());
        subText.setText(currentWord.getSubject());
        return view;

    }

}
