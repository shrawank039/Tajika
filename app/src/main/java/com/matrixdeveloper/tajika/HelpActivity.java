package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.FaqAdapter;
import com.matrixdeveloper.tajika.model.FaqModel;

public class HelpActivity extends AppCompatActivity {

    private RecyclerView faqRecyclerView;
    private FaqAdapter faqAdapter;
    private ImageView backPress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> HelpActivity.super.onBackPressed());

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
}