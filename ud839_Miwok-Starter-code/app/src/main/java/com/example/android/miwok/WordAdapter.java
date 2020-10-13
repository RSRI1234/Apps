
package com.example.android.miwok;

        import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
        import androidx.core.content.ContextCompat;

        import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {

    Context context;
    int resource;
    List<Word> words;
    private int mcolor;
    public WordAdapter(@NonNull Context context, int resource, @NonNull List<Word> words,int color) {
        super(context,resource,words);
        this.context = context;
        this.resource = resource;
        this.words = words;
        this.mcolor=color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, null);

        TextView MiwokText = view.findViewById(R.id.Miwok_list_text);
        TextView DefaultText = view.findViewById(R.id.Default_list_text);
        Word currentWord = words.get(position);
        MiwokText.setText(currentWord.getMiwokTranslation());
        DefaultText.setText(currentWord.getDefaultTranslation());
        ImageView icon = view.findViewById(R.id.fig);
        if(currentWord.hasimage()) {
            icon.setImageResource(currentWord.getImageID());
            icon.setVisibility(view.VISIBLE);
        }
        else
            icon.setVisibility(view.GONE);
        View textcontainer=view.findViewById(R.id.list_item_layout);
        int color= ContextCompat.getColor(getContext(),mcolor);
        textcontainer.setBackgroundColor(color);
        return view;
    }
   
    }

