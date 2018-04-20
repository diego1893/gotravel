package com.monsordi.gotravel.shop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.monsordi.gotravel.R;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Diego on 02/04/18.
 */

public class TravelAdapter extends ArrayAdapter<Travel>{

    private Context context;
    private OnClickListener listener;

    public TravelAdapter(Context context,ArrayList<Travel> travelList,OnClickListener listener){
        super(context, R.layout.row_main,travelList);
        this.context = context;
        this.listener = listener;
    }

    public interface OnClickListener{
        void OnClick(int position);
    }

    static class ViewHolder{
        @BindView(R.id.row_main_title) TextView titleTextView;
        @BindView(R.id.row_main_image) ImageView imageView;
        @BindView(R.id.row_main_price) TextView priceTextView;
        @BindView(R.id.row_main_buy_button) Button buyButton;

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

        Travel currentTravel = getItem(position);

        Random r = new Random();
        int index = r.nextInt(3);

        viewHolder.titleTextView.setText(currentTravel.getTitle());
        viewHolder.priceTextView.setText(
                new StringBuilder(context.getString(R.string.euro)).append(currentTravel.getPrice()));
        Glide.with(parent.getContext())
                .load(currentTravel.getImages()[index])
                .into(viewHolder.imageView);
        viewHolder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClick(position);
            }
        });

        return convertView;
    }
}
