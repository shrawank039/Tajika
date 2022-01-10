package com.matrixdeveloper.tajika;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

public class PrivacyPolicyActivity extends AppCompatActivity {

   // private TextView privacyPolicy;
    private WebView wv1;
    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
      //  privacyPolicy = findViewById(R.id.txt_privacy_policy);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> PrivacyPolicyActivity.super.onBackPressed());

        wv1=(WebView)findViewById(R.id.webview);
        wv1.setWebViewClient(new MyBrowser());

        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(ServiceNames.PRIVACY_TnC);

//        ApiCall.getMethod(this, ServiceNames.PRIVACY_POLICY, response -> {
//
//            Utils.log("aboutus: ", response.toString());
//
//            privacyPolicy.setText(response.optString("data"));
//
//        });
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}