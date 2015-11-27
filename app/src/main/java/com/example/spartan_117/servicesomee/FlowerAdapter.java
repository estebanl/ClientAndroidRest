package com.example.spartan_117.servicesomee;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.spartan_117.servicesomee.model.Flower;

import java.util.List;

public class FlowerAdapter extends ArrayAdapter<Flower> {

    private Context context;

   // @SuppressWarnings("unused")
    private List<Flower> flowerList;


    public FlowerAdapter(Context context, int resource, List<Flower> objects) {
        super(context, resource,objects);
        this.context = context;
        this.flowerList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_flower, parent, false);

        Flower flower = flowerList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(flower.getName());

        return view;
    }
}
