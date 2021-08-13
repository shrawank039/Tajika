package com.matrixdeveloper.tajika.utils;

import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.helpers.CustomDialog;

public class Utils {

    public static Boolean isShowing = false;
    private static CustomDialog customDialog;
    private static String TAG = Utils.class.getSimpleName();


    public static void show(Context context) {
        if (!isShowing) {
            isShowing = true;
            customDialog = new CustomDialog(context);
            customDialog.setCancelable(false);
            customDialog.setCanceledOnTouchOutside(false);
            customDialog.setContentView(R.layout.dialog_loading);
            Window window = customDialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            customDialog.show();
        }
    }

    public static void dismiss() {
        if (isShowing) {
            isShowing = false;
            try {
                customDialog.dismiss();
            } catch (Exception e) {

            }
        }
    }

    public static void toast(Context context, String toastmsg) {
        Toast.makeText(context, toastmsg, Toast.LENGTH_SHORT).show();
    }

    public static void log(String TAG, String content) {
        Log.d(TAG, "" + content);
    }


}
