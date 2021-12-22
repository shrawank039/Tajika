package com.matrixdeveloper.tajika.SPbusiness;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.matrixdeveloper.tajika.R;
import com.matrixdeveloper.tajika.adapter.SPBbusinessPhotosVideoAdapter;
import com.matrixdeveloper.tajika.model.SPBbusinessPhotosVideosModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.PrefManager;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SpbEditProfileActivity extends AppCompatActivity {

    private RecyclerView recViewPhotosVideos;
    private SPBbusinessPhotosVideoAdapter mAdapter;
    private ImageView backPress, profileImage;
    private TextView txtBusinessName;
    private EditText edtName, edtPhone, edtEmail, edtBusinessName, edtCategory, edtExperience, edtBusinessLink, edtBusinessDesc;
    private Button submit;
    private PrefManager pref;
    private final String TAG = "SpbProfileEditAct";
    private RelativeLayout profileContainer;
    private String base64String = "";
    private String profileBase64String = "";
    private List<SPBbusinessPhotosVideosModel> imageList = new ArrayList<>();
    private JSONArray encodeImageList = new JSONArray();
    private List<Uri> imageUri = new ArrayList<>();
    private int selectionType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spb_edit_profile);

        pref = new PrefManager(this);
        recViewPhotosVideos = findViewById(R.id.recView_SPBusinessPhotos);
        backPress = findViewById(R.id.iv_backPress);
        profileContainer = findViewById(R.id.rl_profileContainer);
        profileImage = findViewById(R.id.iv_profileImage);

        txtBusinessName = findViewById(R.id.txt_businessNameTop);
        edtName = findViewById(R.id.edt_providerName);
        edtPhone = findViewById(R.id.edt_providerPhone);
        edtEmail = findViewById(R.id.edt_providerEmail);
        edtBusinessName = findViewById(R.id.edt_businessName);
        edtCategory = findViewById(R.id.edt_businessCategory);
        edtExperience = findViewById(R.id.edt_experience);
        edtBusinessLink = findViewById(R.id.edt_businessLink);
        edtBusinessDesc = findViewById(R.id.edt_businessDesc);
        submit = findViewById(R.id.btn_submit);

        findViewById(R.id.iv_addPhotosVideos).setOnClickListener(v -> openPhotoChooser(1));

        backPress.setOnClickListener(view -> SpbEditProfileActivity.super.onBackPressed());

        profileContainer.setOnClickListener(v -> openPhotoChooser(0));

        submit.setOnClickListener(v -> submitProfileUpdate());

        getProfile();
    }

    private void getProfile() {

        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.GET_PROVIDER_PROFILE_BUSI, data, response -> {
            Utils.log(TAG, response.toString());
            try {
                JSONObject jsonObject = response.getJSONObject("data");
                if (response.optString("status").equals("200")) {
                    edtName.setText(jsonObject.optString("name"));
                    edtPhone.setText(jsonObject.optString("phone"));
                    edtEmail.setText(jsonObject.optString("email"));
                    edtCategory.setText(jsonObject.optString("business_categories"));
                    edtExperience.setText(jsonObject.optString("year_of_experience"));
                    edtBusinessLink.setText(jsonObject.optString("bussiness_link"));
                    edtBusinessDesc.setText(jsonObject.optString("service_description"));

                    Glide.with(this).load(jsonObject.optString("profileimage")).placeholder(R.drawable.app_logo).into(profileImage);

                    //Not in response
                    txtBusinessName.setText(jsonObject.optString("business_name"));

                    // imageList.add(new SPBbusinessPhotosVideosModel("0", "https://www.freepnglogos.com/uploads/plus-icon/add-plus-icon-28.png"));
                    JSONArray jsonArray = jsonObject.optJSONArray("service_offerd_image");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        imageList.add(new SPBbusinessPhotosVideosModel(jsonObject1.optString("id"), ServiceNames.PRODUCTION_API + jsonObject1.optString("link"),0));
                    }

                    mAdapter = new SPBbusinessPhotosVideoAdapter(this, imageList, "editProfile");
                    recViewPhotosVideos.setHasFixedSize(true);
                    recViewPhotosVideos.setLayoutManager(new LinearLayoutManager(
                            SpbEditProfileActivity.this,
                            LinearLayoutManager.HORIZONTAL,
                            false));
                    recViewPhotosVideos.setAdapter(mAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
                    if (selectionType == 0) {
                        profileImage.setImageURI(fileUri);
                        profileBase64String = base64String;
                    } else if (selectionType == 1) {
                        encodeImageList.put(base64String);
                        imageUri.add(fileUri);
                        imageList.add(new SPBbusinessPhotosVideosModel("0", fileUri.getPath(),1));
                        mAdapter.notifyDataSetChanged();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitProfileUpdate() {

        String name = edtName.getText().toString().trim();
        String phoneNumber = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String businessName = edtBusinessName.getText().toString().trim();
        String business_categories = edtCategory.getText().toString().trim();
        String year_of_experience = edtExperience.getText().toString().trim();
        String bussiness_link = edtBusinessLink.getText().toString().trim();
        String service_description = edtBusinessDesc.getText().toString().trim();

        JSONObject data = new JSONObject();
        try {
            data.put("id", pref.getString("id"));
            data.put("name", name);
            data.put("phone", phoneNumber);
            data.put("email", email);
            data.put("business_name", businessName);
            data.put("business_categories", business_categories);
            data.put("service_description", service_description);
            data.put("year_of_experience", year_of_experience);
            data.put("bussiness_link", bussiness_link);
            data.put("profileimage", profileBase64String);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.UPDATE_PROVIDER_PROFILE_BUSI, data, response -> {
            Utils.log(TAG, response.toString());

            if (response.optString("status").equals("200")) {
                if (encodeImageList.length() > 0) {
                    uploadBusinessImage();
                } else {
                    Utils.toast(this, response.optString("message"));
                    finish();
                }
            } else {
                Toast.makeText(this, response.optString("message"), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadBusinessImage() {
        JSONObject data = new JSONObject();
        try {
            data.put("user_id", pref.getString("id"));
            data.put("image", encodeImageList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiCall.postMethod(this, ServiceNames.UPLOAD_MULTIPLE_IMAGE, data, response -> {
            Utils.log(TAG, response.toString());

            if (response.optString("status").equals("200")) {
                Utils.toast(this, response.optString("message"));
                finish();
            }
        });

    }

    public void openPhotoChooser(int type) {
        selectionType = type;
        ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }
}