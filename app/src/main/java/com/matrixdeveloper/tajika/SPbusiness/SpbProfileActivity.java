package com.matrixdeveloper.tajika.SPbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.SPindividual.SpiProfileEditActivity;
import com.matrixdeveloper.tajika.adapter.SPBbusinessPhotosVideoAdapter;
import com.matrixdeveloper.tajika.model.SPBbusinessPhotosVideosModel;

public class SpbProfileActivity extends AppCompatActivity {
    private TextView profileEdit;
    private ImageView backPress;
    private RecyclerView recViewPhotosVideos;
    private SPBbusinessPhotosVideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spb_profile);

        profileEdit = findViewById(R.id.txt_profileEdit);
        backPress = findViewById(R.id.iv_backPress);
        recViewPhotosVideos = findViewById(R.id.recView_SPBusinessPhotos);
        backPress.setOnClickListener(view -> SpbProfileActivity.super.onBackPressed());
        profileEdit.setOnClickListener(view -> startActivity(new Intent(SpbProfileActivity.this, SpbEditProfileActivity.class)));

        SPBbusinessPhotosVideosModel[] pvModel = new SPBbusinessPhotosVideosModel[]{
                new SPBbusinessPhotosVideosModel(1, R.drawable.provider_image_1x),
                new SPBbusinessPhotosVideosModel(2, R.drawable.giving_high_five_2x),
                new SPBbusinessPhotosVideosModel(3, R.drawable.plumbing),
                new SPBbusinessPhotosVideosModel(4, R.drawable.catering),
                new SPBbusinessPhotosVideosModel(5, R.drawable.badge_check_2x),
                new SPBbusinessPhotosVideosModel(5, R.drawable.business_leader_2x),
                new SPBbusinessPhotosVideosModel(5, R.drawable.businessman_2x),
        };

        mAdapter = new SPBbusinessPhotosVideoAdapter(this, pvModel);
        recViewPhotosVideos.setHasFixedSize(true);
        recViewPhotosVideos.setLayoutManager(new LinearLayoutManager(
                SpbProfileActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));
        recViewPhotosVideos.setAdapter(mAdapter);
    }
}