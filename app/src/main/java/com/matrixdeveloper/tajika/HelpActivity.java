package com.matrixdeveloper.tajika;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.FaqAdapter;
import com.matrixdeveloper.tajika.model.FaqModel;
import com.matrixdeveloper.tajika.network.ApiCall;
import com.matrixdeveloper.tajika.network.MySingleton;
import com.matrixdeveloper.tajika.network.ServiceNames;
import com.matrixdeveloper.tajika.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    private RecyclerView faqRecyclerView;
    private FaqAdapter faqAdapter;
    private TextView txtMailSupport, txtCallSupport;
    private ImageView backPress, callSupport, mailSupport;
    private String mailId, telNumber;
    private List<FaqModel> myListData = new ArrayList<>();
    private String TAG = "HelpActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initViews();
        initListeners();

        getFaqDetails();

    }

    private void getFaqDetails() {
        ApiCall.getMethod(this, ServiceNames.HELP_FAQ, response -> {
            Utils.log(TAG, response.toString());
            JSONObject jsonObject = null;
            try {
                jsonObject = response.getJSONObject("data");
                mailId = jsonObject.getString("user_support_email");
                telNumber = jsonObject.getString("user_support_phone");

                txtCallSupport.setText(telNumber);
                txtMailSupport.setText(mailId);

                JSONArray jsonArray = jsonObject.getJSONArray("faqs");

                for (int i = 0; i < jsonArray.length(); i++) {
                    FaqModel faqModel = MySingleton.getGson().fromJson(jsonArray.getJSONObject(i).toString(), FaqModel.class);
                    myListData.add(faqModel);

                }
                faqRecyclerView = findViewById(R.id.recView_FAQ);
                faqAdapter = new FaqAdapter(this, myListData);
                faqRecyclerView.setHasFixedSize(true);
                faqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                faqRecyclerView.setAdapter(faqAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void initViews() {

        backPress = findViewById(R.id.iv_backPress);
        callSupport = findViewById(R.id.iv_callSupport);
        mailSupport = findViewById(R.id.ive_mailSupport);
        txtMailSupport = findViewById(R.id.textView5);
        txtCallSupport = findViewById(R.id.textView2);
    }

    private void initListeners() {
        backPress.setOnClickListener(view -> HelpActivity.super.onBackPressed());
        callSupport.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            String temp = "tel:" + telNumber;
            callIntent.setData(Uri.parse(temp));

            startActivity(callIntent);
        });

        mailSupport.setOnClickListener(view -> {
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailId});
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