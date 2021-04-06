 package com.matrixdeveloper.tajika;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.matrixdeveloper.tajika.adapter.ConversationListAdapter;
import com.matrixdeveloper.tajika.model.ConversationListModel;

public class ConversationListActivity extends AppCompatActivity {

    private RecyclerView conversationListRec;
    private ConversationListAdapter myAdapter;
    private ImageView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        backPress = findViewById(R.id.iv_backPress);
        backPress.setOnClickListener(view -> ConversationListActivity.super.onBackPressed());

        ConversationListModel[] convModel = new ConversationListModel[]{
                new ConversationListModel(1, R.drawable.app_logo, "Super Catering", "Yesterday"),
                new ConversationListModel(2, R.drawable.app_logo, "E sewa", "Today"),
                new ConversationListModel(3, R.drawable.app_logo, "Plumbing", "Tomorrow"),

        };

        conversationListRec = findViewById(R.id.revView_conversationList);
        myAdapter = new ConversationListAdapter(this, convModel);
        conversationListRec.setHasFixedSize(true);
        conversationListRec.setLayoutManager(new LinearLayoutManager(this));
        conversationListRec.setAdapter(myAdapter);
    }
}