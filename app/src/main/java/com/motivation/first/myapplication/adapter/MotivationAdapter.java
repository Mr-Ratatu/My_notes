package com.motivation.first.myapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.motivation.first.myapplication.DataBaseProject.NoteAppDataBase;
import com.motivation.first.myapplication.Model.Utils;
import com.motivation.first.myapplication.R;
import com.motivation.first.myapplication.activity.MainActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class MotivationAdapter extends RecyclerView.Adapter<MotivationAdapter.MotivationViewHolder> {

    private ArrayList<Utils> list;
    private Context context;
    private NoteAppDataBase noteAppDataBase;

    public MotivationAdapter(ArrayList<Utils> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private void deleteNote(Utils utils, int position) {

        list.remove(position);
        noteAppDataBase.getNoteDao().deleteNote(utils);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MotivationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);

        return new MotivationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MotivationViewHolder holder, final int position) {
        final Utils utils = list.get(position);

        holder.title.setText(utils.getTitle());
        holder.description.setText(utils.getDescription());
        holder.dialogTitle.setText(utils.getTitle());
        holder.dialogDescription.setText(utils.getDescription());

        holder.deleteForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(utils, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MotivationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView description;
        public ImageButton deleteForm;
        public Dialog formDialog;
        public TextView dialogTitle;
        public TextView dialogDescription;
        public TextView inform;

        public MotivationViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_motivation);
            description = itemView.findViewById(R.id.description);
            deleteForm = itemView.findViewById(R.id.delete_form);
            inform = itemView.findViewById(R.id.inform);

            noteAppDataBase = Room.databaseBuilder(context, NoteAppDataBase.class, "NotesDb")
                    .allowMainThreadQueries()
                    .build();

            formDialog = new Dialog(context); //Создаем новое диалоговое окно
            formDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //Скрываем заголовк
            formDialog.setContentView(R.layout.clarifying_dialog); //путь к макету диалогового окна
            dialogTitle = formDialog.findViewById(R.id.dialog_title);
            dialogDescription = formDialog.findViewById(R.id.dialog_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            formDialog.show();
        }
    }
}
