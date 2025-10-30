package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    ArrayList<String> resultsList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    // ✅ Static list to track all numbers generated for the History screen
    public static ArrayList<Integer> generatedNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        btnGenerate = findViewById(R.id.btnGenerate);
        lvResults = findViewById(R.id.lvResults);

        // ✅ Adapter for displaying multiplication results
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultsList);
        lvResults.setAdapter(adapter);

        // ✅ Generate table button click
        btnGenerate.setOnClickListener(v -> {
            String input = etNumber.getText().toString().trim();

            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
                return;
            }

            int number = Integer.parseInt(input);

            // ✅ Add number to the generated numbers list if not already there
            if (!generatedNumbers.contains(number)) {
                generatedNumbers.add(number);
            }

            // ✅ Clear old results and generate new table
            resultsList.clear();
            for (int i = 1; i <= 10; i++) {
                String line = number + " × " + i + " = " + (number * i);
                resultsList.add(line);
            }

            adapter.notifyDataSetChanged();
        });

        // ✅ Delete an item when clicked
        lvResults.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = resultsList.get(position);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Item")
                    .setMessage("Do you want to delete: " + selectedItem + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        resultsList.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,
                                "Deleted: " + selectedItem, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    // ✅ Inflate top-right menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // ✅ Handle History button click in menu
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.menu_history) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);  // ✅ No need to send "history" extra anymore
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
