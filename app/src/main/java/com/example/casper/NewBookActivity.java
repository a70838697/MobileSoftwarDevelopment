package com.example.casper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewBookActivity extends AppCompatActivity {

    private Button buttonOK, buttonCancel;
    private EditText editTextBookTitle, editTextBookPrice;
    private int insertPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        buttonOK = findViewById(R.id.button_ok);
        buttonCancel = findViewById(R.id.button_cancel);
        editTextBookTitle = findViewById(R.id.edit_text_book_title);
        editTextBookPrice = findViewById(R.id.edit_text_book_price);

        editTextBookTitle.setText(getIntent().getStringExtra("title"));
        editTextBookPrice.setText(getIntent().getDoubleExtra("price", 0) + "");
        insertPosition = getIntent().getIntExtra("insert_position", 0);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("title", editTextBookTitle.getText().toString());
                intent.putExtra("insert_position", insertPosition);
                intent.putExtra("price", Double.parseDouble(editTextBookPrice.getText().toString()));
                setResult(RESULT_OK, intent);
                NewBookActivity.this.finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewBookActivity.this.finish();
            }
        });

    }
}
