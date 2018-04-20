package com.monsordi.gotravel.shop;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.monsordi.gotravel.R;
import com.monsordi.gotravel.dialog.DialogGoTravel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements DialogGoTravel.DialogGoTravelTasks
                            ,CartAdapter.OnClickListener{

    @BindView(R.id.cart_toolbar) Toolbar toolbar;
    @BindView(R.id.cart_listview) ListView listView;
    @BindView(R.id.cart_total) TextView totalTextView;

    private int cartSize;
    private float total=0;
    private ArrayList<Travel> cartList;
    private CartAdapter cartAdapter;

    private DecimalFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        format = new DecimalFormat(getString(R.string.pattern));


        cartList = (ArrayList<Travel>) getIntent().getSerializableExtra(getString(R.string.cart_list_key));
        for(Travel travel: cartList){
            total += travel.getPrice();
        }
        totalTextView.setText(new StringBuilder(getString(R.string.total)).append(format.format(total)));
        cartSize = cartList.size();
        cartAdapter = new CartAdapter(this,cartList,this);
        listView.setAdapter(cartAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(cartSize != cartList.size()) {
            DialogGoTravel dialogGoTravel = new DialogGoTravel(this, this);
            dialogGoTravel.showGoTravelDialog(getString(R.string.changes_made));
        }
        else
            NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void onClick(int position) {
        total -= cartList.get(position).getPrice();
        cartAdapter.remove(cartList.get(position));
        totalTextView.setText(new StringBuilder(getString(R.string.total)).append(format.format(total)));
    }

    //*******************************************************************************************************************

    //Handles dialog methods

    @Override
    public void doCancelTask(Dialog dialog) {
        setResult(RESULT_CANCELED);
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void doOkTask(Dialog dialog) {
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.cart_list_key),cartList);
        setResult(RESULT_OK,intent);
        NavUtils.navigateUpFromSameTask(this);
    }
}
