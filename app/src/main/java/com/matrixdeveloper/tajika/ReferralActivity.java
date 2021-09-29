package com.matrixdeveloper.tajika;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReferralActivity extends AppCompatActivity {

    private ImageView shareThroughMessage, shareThroughWhatsapp, shareThroughFacebook, backPress;
    private TextView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);
        shareThroughMessage = findViewById(R.id.iv_shareThroughMessage);
        shareThroughWhatsapp = findViewById(R.id.iv_shareThroughWhatsapp);
        shareThroughFacebook = findViewById(R.id.iv_shareThroughFacebook);
        share = findViewById(R.id.txt_share);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> ReferralActivity.super.onBackPressed());

        shareThroughMessage.setOnClickListener(view -> {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", "");
            sendIntent.setType("vnd.android-dir/mms-sms");
            startActivity(sendIntent);
        });

        shareThroughWhatsapp.setOnClickListener(view -> {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "App Link will be pasted here");
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ReferralActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
        });

        shareThroughFacebook.setOnClickListener(view -> {
            Intent fbIntent = new Intent(Intent.ACTION_SEND);
            fbIntent.setType("text/plain");
            fbIntent.setPackage("com.facebook.katana");
            fbIntent.putExtra(Intent.EXTRA_TEXT, "");

            try {
                startActivity(fbIntent);
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(ReferralActivity.this, "Facebook have not been installed.", Toast.LENGTH_SHORT).show();
            }
        });

        share.setOnClickListener(view -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "App Link will be pasted here");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Share Via");
            startActivity(Intent.createChooser(shareIntent, "App Link will be pasted here"));
        });
    }

    public void checkPoints(View view) {
        startActivity(new Intent(getApplicationContext(), CoinsWalletActivity.class));
    }
}