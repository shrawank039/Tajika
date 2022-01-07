package com.matrixdeveloper.tajika;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.WebView.WebAppInterface;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentWebViewActivity extends AppCompatActivity implements View.OnTouchListener, Handler.Callback {

    WebView myWebView;
    String url, amount, description, planId;
    String TAG = "PaymentWebViewAct";
    private PrefManager prf;
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;
    private final Handler handler = new Handler(this);

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        getToken();
        prf = new PrefManager(this);
        amount = getIntent().getStringExtra("amount");
        description = getIntent().getStringExtra("order_desc");
        planId = getIntent().getStringExtra("plan_id");
        myWebView = findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        myWebView.requestFocus();
        myWebView.getSettings().setGeolocationEnabled(true);
        myWebView.setSoundEffectsEnabled(true);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String callback_url) {
                Utils.log(TAG, callback_url);
                myWebView.loadData(callback_url, "text/html", "UTF-8");
                if (callback_url.contains("transactionStatus=SUCCESS")) {
                    setResult(1);
                    finish();
                    return true;
                } else if (callback_url.contains("transactionStatus=CANCELLED")) {
                    setResult(0);
                    finish();
                    return true;
                }

                return false;
            }
        });


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

        Map<String, String> params = new HashMap<>();
        params.put("accessToken", token);
        params.put("user_id", prf.getString("id"));
        params.put("amount", amount);
        params.put("plan_id", amount);
        params.put("order_desc", description);

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
        if (v.getId() == R.id.webview && event.getAction() == MotionEvent.ACTION_DOWN) {
            handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
        }
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == CLICK_ON_URL) {
            handler.removeMessages(CLICK_ON_WEBVIEW);
            return true;
        }
        return msg.what == CLICK_ON_WEBVIEW;
    }
}

