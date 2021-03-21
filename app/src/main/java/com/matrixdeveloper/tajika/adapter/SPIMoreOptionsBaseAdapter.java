package com.matrixdeveloper.tajika.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.matrixdeveloper.tajika.R;

public class SPIMoreOptionsBaseAdapter extends BaseAdapter {
    String moreOptionsList[];
    LayoutInflater inflter;

    public SPIMoreOptionsBaseAdapter(Context applicationContext, String[] moreOptionsList) {
        this.moreOptionsList = moreOptionsList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return moreOptionsList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_more_options, null);
        TextView options = view.findViewById(R.id.txt_options);
        options.setText(moreOptionsList[i]);

        return view;
    }
}