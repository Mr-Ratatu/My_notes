package com.motivation.first.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.motivation.first.myapplication.DataBaseProject.NoteAppDataBase;
import com.motivation.first.myapplication.Model.Utils;
import com.motivation.first.myapplication.NoteViewModel;
import com.motivation.first.myapplication.R;
import com.motivation.first.myapplication.adapter.MyNoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ADD_NOTE_REQUEST = 1;

    private NoteViewModel noteViewModel;
    private ArrayList<Utils> list;
    private MyNoteAdapter adapter;

    /**Следующие два поля нужны для метода onBackPressed*/
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new MyNoteAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        /**Инициализация ViewModel*/
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Utils>>() {
            @Override
            public void onChanged(@Nullable List<Utils> utils) {
                adapter.setNotes(utils);
            }
        });

        init();

    }

    /**Инициализация всех полей*/
    private void init() {

        FloatingActionButton dialogVisible = findViewById(R.id.add_recycler);
        dialogVisible.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_recycler:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);

            Utils utils = new Utils(title, description);
            noteViewModel.insert(utils);

            Toast.makeText(this, "Заметка создана", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
        }
    }


    /**-----------------------------Методы не требующие разъекснений----------------------------*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
