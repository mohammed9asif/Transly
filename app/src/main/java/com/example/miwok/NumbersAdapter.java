package com.example.miwok;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class NumbersAdapter extends ArrayAdapter<Word> {


    private int colorId;
    public NumbersAdapter(@NonNull Context context,@NonNull List<Word> objects,int colorId) {
        super(context, 0, objects);
        this.colorId = colorId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);



    }

        Word currentWord = getItem(position);

        TextView miwokText = (TextView) listItemView.findViewById(R.id.miwok_word);

        miwokText.setText(currentWord.getMiwokTranslation());


        TextView defaultText = (TextView) listItemView.findViewById(R.id.english_word);

        defaultText.setText(currentWord.getDefaultTranslation());

        ImageView image = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()){


            image.setImageResource(currentWord.getImageId());

            image.setVisibility(View.VISIBLE);
        }
        else{
            image.setVisibility(View.GONE);
        }

        View layout = listItemView.findViewById(R.id.color);

        int color = ContextCompat.getColor(getContext(),colorId);

        layout.setBackgroundColor(color);


        
        return listItemView;
}
}
