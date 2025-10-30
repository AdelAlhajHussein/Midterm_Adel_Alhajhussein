package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ListView lvHistory;
    ArrayList<String> historyList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        lvHistory = findViewById(R.id.lvHistory);

        historyList = getIntent().getStringArrayListExtra("history");

        if (historyList != null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
            lvHistory.setAdapter(adapter);
        }
    }
}
