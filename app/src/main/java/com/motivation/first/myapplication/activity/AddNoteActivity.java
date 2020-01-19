package com.motivation.first.myapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.motivation.first.myapplication.BasicAction;
import com.motivation.first.myapplication.DataBaseProject.NoteAppDataBase;
import com.motivation.first.myapplication.Model.Utils;
import com.motivation.first.myapplication.R;

public class AddNoteActivity extends AppCompatActivity {

    private FloatingActionButton createRecycler;
    private EditText addTitle;
    private EditText addDescription;

    private boolean checkFields;
    private NoteAppDataBase noteAppDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addTitle = findViewById(R.id.add_title);
        addDescription = findViewById(R.id.add_description);

        noteAppDataBase = Room.databaseBuilder(this, NoteAppDataBase.class, "NotesDb")
                .allowMainThreadQueries()
                .build();

        createRecycler = findViewById(R.id.create_recycler);
        createRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataTransfer();
            }
        });
    }

    private void dataTransfer() {
        if (checkingEmptyFields()) {

            String title = addTitle.getText().toString();
            String description = addDescription.getText().toString();

            Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
            long id = noteAppDataBase.getNoteDao().addNote(new Utils(title, description, 0));

            intent.putExtra("id", id);
            startActivity(intent);
            finish();

        }
    }

    private boolean checkingEmptyFields() {
        if (TextUtils.isEmpty(addTitle.getText().toString())
                || TextUtils.isEmpty(addDescription.getText().toString())) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        } else {
            checkFields = true;
        }

        return checkFields;
    }
}
