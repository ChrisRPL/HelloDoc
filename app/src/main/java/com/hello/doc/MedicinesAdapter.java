package com.hello.doc;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.MyViewHolder> {

    private Context context;
    private List<ListMedicines> list;
    SharedPreferences sharedPreferences;

    public MedicinesAdapter(Context context, List<ListMedicines> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medicines_list, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ListMedicines item = list.get(i);
        myViewHolder.name.setText(item.getMedicineName().toUpperCase());
        myViewHolder.pills.setText("Ilość leku: ".toUpperCase() + item.getPills());
        myViewHolder.reminder.setText("Aktualne przypomnienia: ".toUpperCase() + item.getReminder());
        Picasso.with(context).load(item.getThumbnail()).into(myViewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  void removeItem(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(ListMedicines item, int position)
    {
        list.add(position, item);
        notifyItemInserted(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, effect, pills, reminder;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            pills = itemView.findViewById(R.id.pills);
            reminder = itemView.findViewById(R.id.reminder);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}
