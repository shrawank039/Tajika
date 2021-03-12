package com.matrixdeveloper.tajika.network;

import android.content.Context;
import com.android.volley.VolleyError;
import com.matrixdeveloper.tajika.utils.App;

public class VolleyErrorHandler {
    private static String TAG = VolleyErrorHandler.class.getSimpleName();

    public static void handle(String url, VolleyError volleyError) {
        Context context = App.getContext();

       /* try {
            Utils.log(TAG, "handle:url " + volleyError.networkResponse.statusCode);

            switch (volleyError.networkResponse.statusCode) {
                case 500:
                case 401:
                    Utils.toast(context, context.getResources().getString(R.string.some_thing_went_wrong));
                    break;
                case 404:
                    Utils.toast(context, "Error 404 Not Found!!");
                    break;
            }
        } catch (Exception e) {
            Utils.toast(context, context.getResources().getString(R.string.some_thing_went_wrong));
            e.printStackTrace();
        }*/
    }

}
