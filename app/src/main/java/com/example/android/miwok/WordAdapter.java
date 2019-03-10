package com.example.android.miwok;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourcId;
    public WordAdapter(Context context, ArrayList<Word> words,int colorResourcId) {
        super(context,0,words);
        mColorResourcId=colorResourcId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        View LitItem=convertView;
        if (LitItem == null) {
            LitItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Word currentWord = getItem(position);


        TextView miwokTextView = (TextView) LitItem.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getmMiwokTranslation());

        ImageView iconView = (ImageView) LitItem.findViewById(R.id.miwok_Image_view);
//        iconView.setImageResource(currentWord.getmImageMiwok());
        int imageID = currentWord.getmImageMiwok();
        if (imageID != 0) iconView.setImageResource(imageID);
        else iconView.setVisibility(View.GONE);

        TextView defaultTextView = (TextView) LitItem.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getmDefaultTranslation());
        View textContainer =LitItem.findViewById(R.id.text_container);
        int color=ContextCompat.getColor(getContext(),mColorResourcId);

        textContainer.setBackgroundColor(color);
        return LitItem;
    }


}
