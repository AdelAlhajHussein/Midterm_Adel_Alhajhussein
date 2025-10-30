package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etNumber;
    Button btnGenerate;
    ListView lvResults;
    ArrayList<String> tableList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        btnGenerate = findViewById(R.id.btnGenerate);
        lvResults = findViewById(R.id.lvResults);

        tableList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tableList);
        lvResults.setAdapter(adapter);

        btnGenerate.setOnClickListener(v -> generateTable());

        lvResults.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = tableList.get(position);


            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete \"" + selectedItem + "\"?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        tableList.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Deleted: " + selectedItem, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

    }

    private void generateTable() {
        String input = etNumber.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            return;
        }

        int number = Integer.parseInt(input);
        tableList.clear();

        for (int i = 1; i <= 10; i++) {
            tableList.add(number + " × " + i + " = " + (number * i));
        }

        adapter.notifyDataSetChanged();
    }
}
