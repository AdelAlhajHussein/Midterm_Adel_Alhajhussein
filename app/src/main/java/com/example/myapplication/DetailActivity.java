package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ListView lvHistory;
    private ArrayList<String> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        lvHistory = findViewById(R.id.lvHistory);

        // Retrieve the history list passed from MainActivity
        historyList = getIntent().getStringArrayListExtra("historyList");

        if (historyList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, historyList);
            lvHistory.setAdapter(adapter);
        }
    }
}
