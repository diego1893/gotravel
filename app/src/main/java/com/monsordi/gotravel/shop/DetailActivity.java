package com.monsordi.gotravel.shop;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.monsordi.gotravel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.detail_toolbar) Toolbar toolbar;
    @BindView(R.id.detail_view_flipper) ViewFlipper viewFlipper;
    @BindView(R.id.detail_image1) ImageView imageView1;
    @BindView(R.id.detail_image2) ImageView imageView2;
    @BindView(R.id.detail_image3) ImageView imageView3;
    @BindView(R.id.detail_name) TextView nameTextView;
    @BindView(R.id.detail_price) TextView priceTextView;
    @BindView(R.id.detail_description) TextView descriptionTextView;
    @BindView(R.id.detail_fab) FloatingActionButton addToCartButton;

    private int position;
    private int numberOfAddings;
    private TravelGestureDetector travelGestureDetector;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        travelGestureDetector = new TravelGestureDetector(viewFlipper);
        gestureDetector = new GestureDetector(this,travelGestureDetector);

        numberOfAddings = 0;
        position = getIntent().getIntExtra(getString(R.string.position_key),-1);
        Travel travel = (Travel) getIntent().getSerializableExtra(getString(R.string.travel_key));
        initializeUI(travel);
    }

    private void initializeUI(Travel travel) {
        getSupportActionBar().setTitle(travel.getVisitedPlace());
        Glide.with(this).load(travel.getImages()[0]).into(imageView1);
        Glide.with(this).load(travel.getImages()[1]).into(imageView2);
        Glide.with(this).load(travel.getImages()[2]).into(imageView3);
        nameTextView.setText(travel.getTitle());
        priceTextView.setText(new StringBuilder(getString(R.string.price)).append(travel.getPrice()));
        descriptionTextView.setText(travel.getDescription());
        addToCartButton.setOnClickListener(this);
        viewFlipper.setInAnimation(this,android.R.anim.fade_in);
        viewFlipper.setOutAnimation(this,android.R.anim.fade_out);
        viewFlipper.startFlipping();
        viewFlipper.setFlipInterval(1500);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewFlipper.stopFlipping();
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.detail_fab:
                numberOfAddings++;
                Toast.makeText(this,getString(R.string.added_to_cart),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra(getString(R.string.position_key),position);
                intent.putExtra(getString(R.string.addings),numberOfAddings);
                setResult(RESULT_OK,intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
