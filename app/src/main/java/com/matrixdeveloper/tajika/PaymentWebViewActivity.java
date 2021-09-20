package com.matrixdeveloper.tajika;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.matrixdeveloper.tajika.WebView.WebAppInterface;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentWebViewActivity extends AppCompatActivity implements View.OnTouchListener, Handler.Callback {

    WebView myWebView;
    String url="";
    String TAG = "PaymentWebViewAct";
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;
    private final Handler handler = new Handler(this);

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        getToken();

        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        myWebView.requestFocus();
        myWebView.getSettings().setGeolocationEnabled(true);
        myWebView.setSoundEffectsEnabled(true);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urll) {
                Utils.log(TAG, urll);
                if (urll.contains("checkout/success")) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class)
                    .putExtra("status","1"));
                    finish();
                    return true;
                } else if (urll.contains("checkout/fail")){
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class)
                            .putExtra("status","0"));
                    return true;
                }

                return false;
            }
        });


//        String unencodedHtml = url;
//        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
//                Base64.NO_PADDING);
//        myWebView.loadData(encodedHtml, "text/html", "base64");


    }

    private void getToken() {
        ApiCall.getMethod(this, ServiceNames.PAYMENT_TOKEN, response -> {
            Utils.log(TAG, response.toString());
            JSONObject jsonObject = null;
            try {
                jsonObject = response.getJSONObject("data");
                String accessToken = jsonObject.optString("accessToken");
                getPaymentPage(accessToken);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void getPaymentPage(String token) {
        Map<String,String> params = new HashMap<>();
        params.put("accessToken",token);
        params.put("user_id","77");
        params.put("order_id", "2");
        params.put("amount","1000");
        params.put("order_desc", "test payment");

        ApiCall.postStringMethod(this, ServiceNames.PAYMENT_PAGE, params, response -> {

            url = response;

            myWebView.loadData(url, "text/html", "UTF-8");

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.webview && event.getAction() == MotionEvent.ACTION_DOWN){
            handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
        }
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == CLICK_ON_URL){
            handler.removeMessages(CLICK_ON_WEBVIEW);
            return true;
        }
        if (msg.what == CLICK_ON_WEBVIEW){

            return true;
        }
        return false;
    }
}

