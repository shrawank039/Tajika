package com.matrixdeveloper.tajika.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiCall {

    private static final int MY_SOCKET_TIMEOUT_MS = 5000;
    private static final String TAG = "APPTAJIKA";

    public static void postMethod(final Context context, final String url, final JSONObject jsonObject, final VolleyCallback volleyCallback) {
        Utils.log(TAG, "getMethod:" + ", url: " + url);
        Utils.show(context);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + url + ",response:" + response);
                        Utils.dismiss();
                        if (response.has("error")) {
                            if (response.optString("error").equalsIgnoreCase("false")) {
                                volleyCallback.onSuccess(response);
                            } else {
                                Utils.toast(context, response.optString("message"));
                            }
                        } else {
                            volleyCallback.onSuccess(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + url + ",onErrorResponse:" + error);
                Utils.dismiss();
                VolleyErrorHandler.handle(url, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                Log.d(TAG, "url:" + url);
                return headers;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(jsonObjReq);

    }

    public static void postMethodWithoutProgress(final Context context, final String url, final JSONObject jsonObject, final VolleyCallback volleyCallback) {
        Utils.log(TAG, "getMethod:" + ", url: " + url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + url + ",response:" + response);
                        if (response.has("error")) {
                            if (response.optString("error").equalsIgnoreCase("false")) {
                                volleyCallback.onSuccess(response);
                            } else {
                                Utils.toast(context, response.optString("message"));
                            }
                        } else {
                            volleyCallback.onSuccess(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + url + ",onErrorResponse:" + error);
                VolleyErrorHandler.handle(url, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                Log.d(TAG, "url:" + url);
                return headers;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(jsonObjReq);

    }

    public static void getMethod(final Context context, final String url, final VolleyCallback volleyCallback) {
        Utils.log(TAG, "getMethod:" + ", url: " + url);
        Utils.show(context);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + url + ",response:" + response);
                        Utils.dismiss();
                        if (response.has("error")) {
                            if (response.optString("error").equalsIgnoreCase("false")) {
                                volleyCallback.onSuccess(response);
                            } else {
                                Utils.toast(context, response.optString("message"));
                            }
                        } else {
                            volleyCallback.onSuccess(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + url + ",onErrorResponse:" + error);
                Utils.dismiss();
                VolleyErrorHandler.handle(url, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                Log.d(TAG, "url:" + url);
                return headers;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(jsonObjReq);

    }

    public static void getMethodJSONArray(final Context context, final String url, final VolleyJSONArrayCallback volleyCallback) {
        Utils.log(TAG, "getMethod:" + ", url: " + url);
        Utils.show(context);
        JSONArray jsonArray = new JSONArray();
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                url, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: " + url + ",response:" + response);
                        Utils.dismiss();

                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + url + ",onErrorResponse:" + error);
                Utils.dismiss();
                VolleyErrorHandler.handle(url, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                Log.d(TAG, "url:" + url);
                return headers;
            }
        };

        jsonArrReq.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(jsonArrReq);
    }

    public static void postStringMethod(final Context context, final String url, Map<String, String> params, final VolleyStringCallback volleyCallback) {
        Utils.log(TAG, "getMethod:" + ", url: " + url);
        Utils.show(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                response -> {
                    Log.d(TAG, "onResponse: " + url + ",response:" + response);
                    Utils.dismiss();

                        volleyCallback.onSuccess(response);

                }, error -> {
                    Log.d(TAG, "onResponse: " + url + ",onErrorResponse:" + error);
                    Utils.dismiss();
                    VolleyErrorHandler.handle(url, error);
                }) {
            @Override
            public Map<String, String> getParams(){
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }
}
