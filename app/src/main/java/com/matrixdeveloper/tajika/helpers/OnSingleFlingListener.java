package com.matrixdeveloper.tajika.helpers;

import android.view.MotionEvent;

public interface OnSingleFlingListener {
    boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
}
