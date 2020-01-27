package com.motivation.first.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.motivation.first.myapplication.DataBaseProject.NoteAppDataBase;
import com.motivation.first.myapplication.Model.Utils;
import com.motivation.first.myapplication.R;
import com.motivation.first.myapplication.activity.DetailItemsActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.MyNoteViewHolder> {

    private List<Utils> list = new ArrayList<>();
    private Context context;

    public MyNoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item, parent, false);

            return new MyNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNoteViewHolder holder, final int position) {
        final Utils utils = list.get(position);

        holder.title.setText(utils.getTitle());
        holder.description.setText(utils.getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setNotes(List<Utils> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyNoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView description;

        public MyNoteViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_motivation);
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(  );
            Utils utils = list.get(position);

            Intent intent = new Intent(context, DetailItemsActivity.class);
            intent.putExtra("title", utils.getTitle());
            intent.putExtra("description", utils.getDescription());
            context.startActivity(intent);
        }

    }
}
