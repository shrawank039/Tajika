package com.matrixdeveloper.tajika.SPindividual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.matrixdeveloper.tajika.AboutUsActivity;
import com.matrixdeveloper.tajika.LoginActivity;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPIMoreOptionsBaseAdapter;
import com.matrixdeveloper.tajika.utils.PrefManager;

public class SpiMoreActivity extends AppCompatActivity {

    private ListView moreOptions;
    private PrefManager prf;
    String settingsList[] = {"My Profile", "Services you offer", "All requests", "All bookings",
            "Credit wallet", "Contact us", "About us", "Logout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spi_more);
        moreOptions = findViewById(R.id.lv_moreOptions);

        prf = new PrefManager(this);

        SPIMoreOptionsBaseAdapter customAdapter = new SPIMoreOptionsBaseAdapter(getApplicationContext(), settingsList);
        moreOptions.setAdapter(customAdapter);

        moreOptions.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 1:
                    startActivity(new Intent(getApplicationContext(), SpiMyServicesActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(getApplicationContext(), SpiAllBookingsActivity.class));
                    break;
                case 6:
                    startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(getApplicationContext(), SpiCreditWalletActivity.class));
                    break;
                case 7:
                    prf.setString("id", "");
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(SpiMoreActivity.this, "" + i, Toast.LENGTH_SHORT).show();
            }
        });
    }
}