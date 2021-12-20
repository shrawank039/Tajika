package com.matrixdeveloper.tajika.SPbusiness;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPBbusinessPhotosVideoAdapter;
import com.matrixdeveloper.tajika.model.SPBbusinessPhotosVideosModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class SpbEditProfileActivity extends AppCompatActivity {

    private RecyclerView recViewPhotosVideos;
    private SPBbusinessPhotosVideoAdapter mAdapter;
    private ImageView backPress, choosePhoto, profilePicture;
    private TextView txtUserName;
    private EditText edtUserName, userMobileNumber, userEmail;
    private Button submit;
    private PrefManager pref;
    private final String TAG = "SpiProfileEditAct";
    private RelativeLayout profileContainer;
    private ImageView profileImage;
    private String base64String="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spb_edit_profile);

        pref = new PrefManager(this);
        recViewPhotosVideos = findViewById(R.id.recView_SPBusinessPhotos);
        backPress = findViewById(R.id.iv_backPress);
        profileContainer = findViewById(R.id.rl_profileContainer);
        profileImage = findViewById(R.id.iv_profileImage);
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

        profileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(SpbEditProfileActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri fileUri = data.getData();
            File file = new File(fileUri.getPath());

            byte[] fileContent = new byte[0];
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fileContent = Files.readAllBytes(file.toPath());
                    base64String = Base64.getEncoder().encodeToString(fileContent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            profileImage.setImageURI(fileUri);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitProfileUpdate() {
        String name = edtUserName.getText().toString().trim();
        String phoneNumber = userMobileNumber.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String service_area = userEmail.getText().toString().trim();
        String business_categories = userEmail.getText().toString().trim();
        String service_description = userEmail.getText().toString().trim();
        String year_of_experience = userEmail.getText().toString().trim();
        String bussiness_link = userEmail.getText().toString().trim();
        String minimum_charge = userEmail.getText().toString().trim();
        String education_level = userEmail.getText().toString().trim();
        String passportnumber = userEmail.getText().toString().trim();
        String upload_passportid = userEmail.getText().toString().trim();
        String professional_qualification = userEmail.getText().toString().trim();
        String qualification_certification = userEmail.getText().toString().trim();

        JSONObject data = new JSONObject();
        try {
            data.put("id", pref.getString("id"));
            data.put("name", name);
            data.put("phone", phoneNumber);
            data.put("email", email);
            data.put("service_area", service_area);
            data.put("business_categories", business_categories);
            data.put("service_description", service_description);
            data.put("year_of_experience", year_of_experience);
            data.put("bussiness_link", bussiness_link);
            data.put("minimum_charge", minimum_charge);
            data.put("education_level", education_level);
            data.put("passportnumber", passportnumber);
            data.put("upload_passportid", upload_passportid);
            data.put("professional_qualification", professional_qualification);
            data.put("qualification_certification", qualification_certification);
            data.put("profileimage", base64String);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.UPDATE_PROVIDER_PROFILE_BUSI, data, response -> {
            Utils.log(TAG, response.toString());

            if (response.optString("status").equals("200")) {
                Utils.toast(this, response.optString("message"));
                finish();
            } else {
                Toast.makeText(this, response.optString("message"), Toast.LENGTH_LONG).show();
            }
        });
    }
}