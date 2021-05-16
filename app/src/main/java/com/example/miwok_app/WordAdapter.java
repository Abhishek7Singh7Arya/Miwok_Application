package com.example.miwok_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
   // private static final String LOG_TAG = Word.class.getSimpleName();
 int listColorid;
   public WordAdapter(Activity context, ArrayList<Word> words,int color) {

       super(context,0,words);
       listColorid=color;
   }

    @NonNull
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Word currentWord = getItem(position);
listItemView.findViewById(R.id.inner_list).setBackgroundResource(listColorid);
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.mmiwok_text_View);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        defaultTextView.setText(currentWord.getDefaultTranslation());
        ImageView img = (ImageView)listItemView.findViewById(R.id.imgview);
            if (currentWord.hasImgResource()){

        img.setImageResource(currentWord.getImgResourceId());
            img.setVisibility(View.VISIBLE);
            }
            else{
                img.setVisibility(View.GONE);
            }
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }


}




