package com.monsordi.gotravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.monsordi.gotravel.R;
import com.monsordi.gotravel.dto.Orden;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Diego on 02/04/18.
 */

public class OrderAdapter extends ArrayAdapter<Orden> {

    public OrderAdapter(Context context, List<Orden> travelList){
        super(context, R.layout.row_main,travelList);
    }

    static class ViewHolder{
        @BindView(R.id.row_main_title)
        TextView titleTextView;
        @BindView(R.id.row_main_image)
        ImageView imageView;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        }
        else {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_main,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        Orden currentOrden = getItem(position);

        viewHolder.titleTextView.setText(currentOrden.getStatus().getStatus_string());
        if(currentOrden.getCelular() != null) {
            Glide.with(parent.getContext())
                    .load(currentOrden.getCelular().getImageUrl())
                    .into(viewHolder.imageView);
        }

        return convertView;
    }
}
