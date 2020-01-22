package com.motivation.first.myapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.motivation.first.myapplication.BasicAction;
import com.motivation.first.myapplication.DataBaseProject.NoteAppDataBase;
import com.motivation.first.myapplication.Model.Utils;
import com.motivation.first.myapplication.R;
import com.motivation.first.myapplication.activity.DetailItemsActivity;
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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Utils utils = list.get(position);

            Intent intent = new Intent(context, DetailItemsActivity.class);
            intent.putExtra(BasicAction.TITLE_EXTRA, utils.getTitle());
            intent.putExtra(BasicAction.DESCRIPTION_EXTRA, utils.getDescription());
            context.startActivity(intent);
        }
    }
}
