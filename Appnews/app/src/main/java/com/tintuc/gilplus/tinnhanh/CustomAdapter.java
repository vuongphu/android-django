package com.tintuc.gilplus.tinnhanh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Zenphone on 5/15/2017.
 */

public class CustomAdapter extends ArrayAdapter<ItemList> {
    private Context context;
    private int layoutResource;
    private ArrayList<ItemList> arrayList;

    public CustomAdapter(Context context, int layoutResource, ArrayList<ItemList> arrayList) {
        super(context, layoutResource, arrayList);
        this.context = context;
        this.layoutResource = layoutResource;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layoutResource, null);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        TextView txtTitle = (TextView)convertView.findViewById(R.id.txtTitle);
        TextView txtSource = (TextView)convertView.findViewById(R.id.txtSource);
        TextView txtTime = (TextView)convertView.findViewById(R.id.txtTime);
        TextView txtSumCm = (TextView)convertView.findViewById(R.id.txtSumCm);
//        imageView.setImageResource(arrayList.get(position).getImage());
        txtTitle.setText(arrayList.get(position).getText());
        txtSource.setText(arrayList.get(position).getSource());
        txtTime.setText(arrayList.get(position).getSpantime());
//        txtSumCm.setText(arrayList.get(position).getSumcomment()+" Bình luận");
        Picasso.with(getContext()).load(arrayList.get(position).getImage()).into(imageView);


        return convertView;
    }

}
