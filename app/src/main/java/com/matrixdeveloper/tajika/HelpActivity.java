package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.FaqAdapter;
import com.matrixdeveloper.tajika.model.FaqModel;

import java.util.List;

public class HelpActivity extends AppCompatActivity {

    private RecyclerView faqRecyclerView;
    private FaqAdapter faqAdapter;
    private ImageView backPress, callSupport, mailSupport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initViews();
        initListeners();


        FaqModel[] myListData = new FaqModel[]{
                new FaqModel(1, getResources().getString(R.string.how_can_i_change_my_password), getResources().getString(R.string.dummy_text)),
                new FaqModel(2, getResources().getString(R.string.how_can_i_change_my_password), getResources().getString(R.string.dummy_text)),
                new FaqModel(3, getResources().getString(R.string.how_can_i_change_my_password), getResources().getString(R.string.dummy_text)),
        };

        faqRecyclerView = (RecyclerView) findViewById(R.id.recView_FAQ);
        faqAdapter = new FaqAdapter(this, myListData);
        faqRecyclerView.setHasFixedSize(true);
        faqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        faqRecyclerView.setAdapter(faqAdapter);
    }

    private void initViews() {

        backPress = findViewById(R.id.iv_backPress);
        callSupport = findViewById(R.id.iv_callSupport);
        mailSupport = findViewById(R.id.ive_mailSupport);
    }

    private void initListeners() {
        backPress.setOnClickListener(view -> HelpActivity.super.onBackPressed());
        callSupport.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            String temp = "tel:1234567890";
            callIntent.setData(Uri.parse(temp));

            startActivity(callIntent);
        });

        mailSupport.setOnClickListener(view -> {
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@tajika.com"});
            final PackageManager pm = getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches)
                if (info.activityInfo.packageName.endsWith(".gm") ||
                        info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
            if (best != null)
                intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            startActivity(intent);
        });

    }
}