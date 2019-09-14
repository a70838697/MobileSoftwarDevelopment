package com.example.casper;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ListViewMainActivity extends AppCompatActivity {

    ListView listViewBooks;
    String[] bookNames = {"Android开发", "xin", "创新工程实践"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_main);
        listViewBooks = this.findViewById(R.id.list_view_books);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ListViewMainActivity.this, android.R.layout.simple_list_item_1, bookNames);
        listViewBooks.setAdapter(adapter);
    }
}
