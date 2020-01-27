package com.motivation.first.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.motivation.first.myapplication.R;
import com.motivation.first.myapplication.adapter.MyNoteAdapter;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.motivation.first.myapplication.activity.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.motivation.first.myapplication.activity.EXTRA_DESCRIPTION";

    private EditText addTitle;
    private EditText addDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        addTitle = findViewById(R.id.add_title);
        addDescription = findViewById(R.id.add_description);

        FloatingActionButton createRecycler = findViewById(R.id.create_recycler);
        createRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    /**Улучшенная версия передачи данных*/
    private void saveNote() {
        String title = addTitle.getText().toString();
        String description = addDescription.getText().toString();

        if (checkingEmptyFields()) {

            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);

            setResult(RESULT_OK, data);
            finish();
        }
    }

    /**ПРоверка на пустоту*/
    private boolean checkingEmptyFields() {
        if (TextUtils.isEmpty(addTitle.getText().toString().trim())
                || TextUtils.isEmpty(addDescription.getText().toString().trim())) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }

    }
}
