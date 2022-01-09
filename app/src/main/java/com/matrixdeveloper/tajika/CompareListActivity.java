package com.matrixdeveloper.tajika;

import static com.matrixdeveloper.tajika.utils.Global.compareAddOneServiceID;
import static com.matrixdeveloper.tajika.utils.Global.compareAddTwoServiceID;
import static com.matrixdeveloper.tajika.utils.Global.firstItemToCompare;
import static com.matrixdeveloper.tajika.utils.Global.secondItemToCompare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.matrixdeveloper.tajika.network.ServiceNames;

public class CompareListActivity extends AppCompatActivity {

    private ImageView backPress, fpImage, spImage;
    private TextView fpName, fpRating, fpCompletedJobs, fpEducationLevel, fpAddress, fbDistance;
    private TextView spName, spRating, spCompletedJobs, spEducationLevel, spAddress, sbDistance;
    private Button fpSelectButton, spSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_list);

        initViews();
        initListeners();
        setViews();

    }

    private void setViews() {
        Glide.with(this).load(ServiceNames.PRODUCTION_API + firstItemToCompare.get(0).getProfileimage()).placeholder(R.drawable.app_logo).into(fpImage);
        fpName.setText(firstItemToCompare.get(0).getName());
        fpRating.setText(firstItemToCompare.get(0).getRating() + " Star");
        fpCompletedJobs.setText("" + firstItemToCompare.get(0).getJobCompleted());
        fpEducationLevel.setText(firstItemToCompare.get(0).getEducationLevel());
        String FPskills = "";
        if (firstItemToCompare.get(0).getSkills() != null) {
            for (int i = 0; i < firstItemToCompare.get(0).getSkills().size(); i++) {
                FPskills = FPskills +"\n"+ firstItemToCompare.get(0).getSkills().get(i);
                fpAddress.setText(FPskills.trim());
            }
        }
        fbDistance.setText("" + firstItemToCompare.get(0).getDistance());

        Glide.with(this).load(ServiceNames.PRODUCTION_API + secondItemToCompare.get(0).getProfileimage()).placeholder(R.drawable.app_logo).into(spImage);
        spName.setText(secondItemToCompare.get(0).getName());
        spRating.setText(secondItemToCompare.get(0).getRating() + " Star");
        spCompletedJobs.setText("" + secondItemToCompare.get(0).getJobCompleted());
        spEducationLevel.setText(secondItemToCompare.get(0).getEducationLevel());

        String skills = "";
        if (secondItemToCompare.get(0).getSkills() != null) {
            for (int i = 0; i < secondItemToCompare.get(0).getSkills().size(); i++) {
                skills = skills +"\n"+ secondItemToCompare.get(0).getSkills().get(i);
                spAddress.setText(skills.trim());
            }
        }

        sbDistance.setText("" + secondItemToCompare.get(0).getDistance());
    }

    private void initViews() {
        backPress = findViewById(R.id.iv_backPress);
        fpImage = findViewById(R.id.iv_fpImage);
        fpName = findViewById(R.id.txt_fpName);
        fpRating = findViewById(R.id.txt_fpRating);
        fpCompletedJobs = findViewById(R.id.txt_fpCompletedJobs);
        fpEducationLevel = findViewById(R.id.txt_fpEducationLevel);
        fpAddress = findViewById(R.id.txt_fpAddress);
        fbDistance = findViewById(R.id.txt_fpDistance);
        fpSelectButton = findViewById(R.id.btn_fpSelectButton);

        spImage = findViewById(R.id.iv_spImage);
        spName = findViewById(R.id.txt_spName);
        spRating = findViewById(R.id.txt_spRating);
        spCompletedJobs = findViewById(R.id.txt_spCompletedJobs);
        spEducationLevel = findViewById(R.id.txt_spEducationLevel);
        spAddress = findViewById(R.id.txt_spAddress);
        sbDistance = findViewById(R.id.txt_spDistance);
        spSelectButton = findViewById(R.id.btn_spSelectButton);
    }

    private void initListeners() {
        backPress.setOnClickListener(view -> CompareListActivity.super.onBackPressed());
        fpSelectButton.setOnClickListener(view -> startActivity(new Intent(this,RequestServiceActivity.class)
                .putExtra("provider_id",firstItemToCompare.get(0).getId())
                .putExtra("service_name",firstItemToCompare.get(0).getBusinessCategories())
                .putExtra("service_id",compareAddOneServiceID)));
        spSelectButton.setOnClickListener(view -> startActivity(new Intent(this,RequestServiceActivity.class)
                .putExtra("provider_id",secondItemToCompare.get(0).getId())
                .putExtra("service_name",secondItemToCompare.get(0).getBusinessCategories())
                .putExtra("service_id",compareAddTwoServiceID)));

    }
}