package com.motivation.first.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.motivation.first.myapplication.BasicAction;
import com.motivation.first.myapplication.DataBaseProject.NoteAppDataBase;
import com.motivation.first.myapplication.Model.Utils;
import com.motivation.first.myapplication.R;
import com.motivation.first.myapplication.adapter.MotivationAdapter;

public class DetailItemsActivity extends AppCompatActivity {

    private FloatingActionButton editFields;
    private EditText titleDetail;
    private EditText descriptionDetail;
    private NoteAppDataBase noteAppDataBase;

    private boolean checkButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_items);

        titleDetail = findViewById(R.id.title_detail);
        descriptionDetail = findViewById(R.id.description_detail);
        editFields = findViewById(R.id.edit_fields);

        noteAppDataBase = NoteAppDataBase.getInstance(this);

        editFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editValueFields();
            }
        });

        getExtraData();
    }

    private void getExtraData() {
        Intent intent = getIntent();
        String fieldTitle;
        String fieldDescription;

        if (intent != null) {
            fieldTitle = intent.getStringExtra(BasicAction.TITLE_EXTRA);
            fieldDescription = intent.getStringExtra(BasicAction.DESCRIPTION_EXTRA);

            titleDetail.setText(fieldTitle);
            descriptionDetail.setText(fieldDescription);
        }
    }


    private void editValueFields() {
        String fieldTitle;
        String fieldDescription;

        if (checkButton) {
            editFields.setImageResource(R.drawable.ic_check_add_24dp);

            titleDetail.setEnabled(true);
            descriptionDetail.setEnabled(true);
            checkButton = false;


        } else {
            editFields.setImageResource(R.drawable.ic_edit_white_24dp);

            titleDetail.setEnabled(false);
            descriptionDetail.setEnabled(false);

            checkButton = true;

            fieldTitle = titleDetail.getText().toString();
            fieldDescription = descriptionDetail.getText().toString();

            Intent detailIntent = new Intent(DetailItemsActivity.this, MainActivity.class);
            long id = noteAppDataBase.getNoteDao().addNote(new Utils(fieldTitle, fieldDescription, 0));

            detailIntent.putExtra("editId", id);
            startActivity(detailIntent);
        }
    }
}
