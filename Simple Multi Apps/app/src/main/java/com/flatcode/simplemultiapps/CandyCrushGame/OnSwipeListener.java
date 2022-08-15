package com.flatcode.simplemultiapps.CandyCrushGame;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeListener implements View.OnTouchListener {

    public GestureDetector gestureDetector;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public OnSwipeListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        public static final int SWIPE_SOLD = 100;
        public static final int SWIPE_VELOCITY_SOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            float yDiff = e2.getY() - e1.getY();
            float xDiff = e2.getX() - e1.getX();
            if (Math.abs(xDiff) > Math.abs(yDiff)) {
                if (Math.abs(xDiff) > SWIPE_SOLD && Math.abs(velocityX) > SWIPE_VELOCITY_SOLD) {
                    if (xDiff > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    result = true;
                }
            } else if (Math.abs(yDiff) > SWIPE_SOLD && Math.abs(velocityY) > SWIPE_VELOCITY_SOLD) {
                if (yDiff > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
            return result;
        }
    }

    public void onSwipeLeft() {
    }

    public void onSwipeRight() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}