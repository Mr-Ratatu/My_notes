package com.motivation.first.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.motivation.first.myapplication.BasicAction;
import com.motivation.first.myapplication.Model.Utils;
import com.motivation.first.myapplication.R;
import com.motivation.first.myapplication.adapter.MotivationAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog dialog;
    public static ArrayList<Utils> list;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView inform;
    private FloatingActionButton dialogVisible;
    private String saveTitle;
    private String saveDescription;
    private long backPressedTime;
    private Toast backToast;

    private BasicAction basicAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MotivationAdapter(list, this);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        inform = findViewById(R.id.inform);
        dialogVisible = findViewById(R.id.add_recycler);
        dialogVisible.setOnClickListener(this);

        basicAction = new BasicAction();

        basicAction.checkExistingItems(list, inform);

        getNoteFromIntent();
    }

    private void getNoteFromIntent() {
        Intent intent = getIntent();
        saveTitle = intent.getStringExtra("title");
        saveDescription = intent.getStringExtra("description");

        if (intent != null) {
            list.add(new Utils(saveTitle, saveDescription, 0));
            adapter.notifyDataSetChanged();

            basicAction.checkExistingItems(list, inform);

        }
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
    public void onBackPressed() {
        //Сравниваем текущее время с временем больше на 2 секунды
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel(); //закрывает уведомление вместе с приложением
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT);
            backToast.show();
        }

        //присваиваем время нажатия кнопки
        backPressedTime = System.currentTimeMillis();
    }
}
