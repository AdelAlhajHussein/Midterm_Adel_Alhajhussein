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

    public static ArrayList<Integer> generatedNumbers = new ArrayList<>();
    public static ArrayList<String> allResultsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        btnGenerate = findViewById(R.id.btnGenerate);
        lvResults = findViewById(R.id.lvResults);

        // Show allResultsList instead of resultsList
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allResultsList);
        lvResults.setAdapter(adapter);

        // Restore previously generated tables (no need to copy anymore)
        adapter.notifyDataSetChanged();

        // Generate table
        btnGenerate.setOnClickListener(v -> {
            String input = etNumber.getText().toString().trim();

            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
                return;
            }

            int number = Integer.parseInt(input);

            // Keep number in history list (for DetailActivity)
            if (!generatedNumbers.contains(number)) {
                generatedNumbers.add(number);
            }

            // Add new lines to the shared allResultsList (do NOT clear)
            for (int i = 1; i <= 10; i++) {
                String line = number + " × " + i + " = " + (number * i);
                allResultsList.add(line);
            }

            adapter.notifyDataSetChanged();
        });

        // Delete selected item when clicked
        lvResults.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = allResultsList.get(position);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Item")
                    .setMessage("Do you want to delete: " + selectedItem + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        allResultsList.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,
                                "Deleted: " + selectedItem, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }


    // Inflate top-right menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Handle History button click in menu
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_history) {
            // Navigate to History screen
            ArrayList<String> historyList = new ArrayList<>();
            for (int num : generatedNumbers) {
                historyList.add(String.valueOf(num));
            }

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putStringArrayListExtra("history", historyList);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_clear) {
            // Show confirmation dialog
            new AlertDialog.Builder(this)
                    .setTitle("Clear All")
                    .setMessage("Are you sure you want to delete all rows?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        allResultsList.clear();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "All rows cleared", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
