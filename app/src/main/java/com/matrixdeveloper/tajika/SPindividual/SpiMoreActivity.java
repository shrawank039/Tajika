package com.matrixdeveloper.tajika.SPindividual;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.AboutUsActivity;
import com.matrixdeveloper.tajika.HelpActivity;
import com.matrixdeveloper.tajika.LandingPage;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.ReferralActivity;
import com.matrixdeveloper.tajika.SPbusiness.SpbMyServicesActivity;
import com.matrixdeveloper.tajika.SPbusiness.SpbProfileActivity;
import com.matrixdeveloper.tajika.adapter.SPIMoreOptionsBaseAdapter;
import com.matrixdeveloper.tajika.utils.PrefManager;

public class SpiMoreActivity extends AppCompatActivity {

    private ListView moreOptions;
    private PrefManager prf;
    private ImageView businessImage;
    private TextView businessName, businessRating;
    String[] settingsList = {"My Profile", "Services you offer", "All requests", "All bookings",
            "Credit wallet", "Refer Friends", "Switch to user", "Rate App", "Contact us", "About us", "Logout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_more);

        prf = new PrefManager(this);
        moreOptions = findViewById(R.id.lv_moreOptions);
        businessImage = findViewById(R.id.iv_profileImage);
        businessName = findViewById(R.id.txt_businessName);
        businessRating = findViewById(R.id.txt_businessRating);

        Glide.with(this).load(prf.getString("profileImage")).placeholder(R.drawable.app_logo).into(businessImage);
        businessName.setText(prf.getString("name"));
        businessRating.setText(prf.getString("rating") + " star ratings");

        SPIMoreOptionsBaseAdapter customAdapter = new SPIMoreOptionsBaseAdapter(getApplicationContext(), settingsList);
        moreOptions.setAdapter(customAdapter);

        moreOptions.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                    if (prf.getString("role").equals("3")) {
                        startActivity(new Intent(getApplicationContext(), SpiProfileActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), SpbProfileActivity.class));
                    }
                    break;
                case 1:
                    if (prf.getString("role").equals("3")) {
                        startActivity(new Intent(getApplicationContext(), SpiMyServicesActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), SpbMyServicesActivity.class));
                    }
                    break;
                case 2:
                    startActivity(new Intent(getApplicationContext(), SpiAllRequestActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(getApplicationContext(), SpiAllBookingsActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(getApplicationContext(), SpiCreditWalletActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(getApplicationContext(), ReferralActivity.class));
                    break;
                case 6:
                    openAlertDialog("Switch User", "Are you sure you want to switch user?");
                    break;
                case 10:
                    openAlertDialog("Confirm Logout", "Are you sure you want to Logout?");
                    break;
                case 7:
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                    break;
                case 8:
                    startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                    break;
                case 9:
                    startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                    break;
                default:
                    Toast.makeText(SpiMoreActivity.this, "" + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openAlertDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    prf.setString("id", "");
                    Intent intent = new Intent(this, LandingPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("No", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG))
                .show();
    }
}