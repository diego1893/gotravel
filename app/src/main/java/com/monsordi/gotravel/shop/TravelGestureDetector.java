package com.monsordi.gotravel.shop;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

/**
 * Created by diego on 03/04/18.
 */

public class TravelGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private ViewFlipper viewFlipper;

    public TravelGestureDetector(ViewFlipper viewFlipper) {
        this.viewFlipper = viewFlipper;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Swipe left (next)
        if (e1.getX() > e2.getX()) {
            viewFlipper.showNext();
        }

        // Swipe right (previous)
        if (e1.getX() < e2.getX()) {
            viewFlipper.showPrevious();
        }

        return super.onFling(e1, e2, velocityX, velocityY);
    }


}
