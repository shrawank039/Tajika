package com.matrixdeveloper.tajika.SPbusiness;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPBbusinessPhotosVideoAdapter;
import com.matrixdeveloper.tajika.model.SPBbusinessPhotosVideosModel;

public class SpbEditProfileActivity extends AppCompatActivity {

    private ImageView backPress;
    private RecyclerView recViewPhotosVideos;
    private SPBbusinessPhotosVideoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spb_edit_profile);

        recViewPhotosVideos = findViewById(R.id.recView_SPBusinessPhotos);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> SpbEditProfileActivity.super.onBackPressed());

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
                SpbEditProfileActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));
        recViewPhotosVideos.setAdapter(mAdapter);
    }
}