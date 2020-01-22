package com.motivation.first.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.motivation.first.myapplication.BasicAction;
import com.motivation.first.myapplication.DataBaseProject.NoteAppDataBase;
import com.motivation.first.myapplication.Model.Utils;
import com.motivation.first.myapplication.R;
import com.motivation.first.myapplication.adapter.MotivationAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Utils> list;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton dialogVisible;
    private long backPressedTime;
    private Toast backToast;

    private NoteAppDataBase noteAppDataBase;
    private BasicAction basicAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        createNote();
        list.addAll(noteAppDataBase.getNoteDao().getAllNotes());

    }

    private void createNote() {
        Intent intent = getIntent();

        if (intent != null) {

            long id = intent.getLongExtra("id", 0);

            Utils utils = noteAppDataBase.getNoteDao().getNote(id);

            if (utils != null) {

                list.add(0, utils);
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Сраный метод
     */
    private void updateNote() {
        Intent detailIntent = getIntent();

        if (detailIntent != null) {

            long id = detailIntent.getLongExtra("id", 0);

            Utils utils = noteAppDataBase.getNoteDao().getNote(id);

            noteAppDataBase.getNoteDao().updateNote(utils);
            list.set((int) id, utils);
            adapter.notifyDataSetChanged();
        }
    }

    private void deleteNote() {
        noteAppDataBase.getNoteDao().deleteNote(list);
        list.removeAll(noteAppDataBase.getNoteDao().getAllNotes());
        list.addAll(noteAppDataBase.getNoteDao().getAllNotes());
        adapter.notifyDataSetChanged();
    }

    /**Инициализация всех полей*/
    private void init() {
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MotivationAdapter(list, this);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        dialogVisible = findViewById(R.id.add_recycler);
        dialogVisible.setOnClickListener(this);

        basicAction = new BasicAction();

        noteAppDataBase = NoteAppDataBase.getInstance(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_recycler:

                startActivity(new Intent(getApplicationContext(), AddNoteActivity.class));
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                deleteNote();
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
