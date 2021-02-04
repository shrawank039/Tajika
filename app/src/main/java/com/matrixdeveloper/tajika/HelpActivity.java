package com.matrixdeveloper.tajika;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.Adapter.FaqAdapter;
import com.matrixdeveloper.tajika.Model.FaqModel;

public class HelpActivity extends AppCompatActivity {

    private RecyclerView faqRecyclerView;
    private FaqAdapter faqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

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