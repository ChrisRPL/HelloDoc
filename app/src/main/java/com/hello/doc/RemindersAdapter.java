package com.hello.doc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RemindersAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> date;
    private final ArrayList<String> time;
    private final ArrayList<String> amount;
    private final ArrayList<Boolean> isChecked;

    public RemindersAdapter(@NonNull Activity context, ArrayList<String> date, ArrayList<String> time, ArrayList<String> amount, ArrayList<Boolean> isChecked) {
        super(context, R.layout.reminders, date);
        this.context = context;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.isChecked = isChecked;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.reminders, null,true);

       TextView dateText = rowView.findViewById(R.id.date);
       TextView timeText = rowView.findViewById(R.id.time);
       TextView amountText = rowView.findViewById(R.id.amount);
        final CheckBox checkBox = rowView.findViewById(R.id.checkBox);

       dateText.setText("Data: " + date.get(position));
       timeText.setText("Godzina: " + time.get(position));
       amountText.setText("Dawka: " + amount.get(position));

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    isChecked.set(position, true);
                }
                else {
                    isChecked.set(position, false);
                }
            }
        });



       return  rowView;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.reminders, null,true);
        final CheckBox checkBox = rowView.findViewById(R.id.checkBox);

        return date.get(position) + ", " + time.get(position) + ", " + amount.get(position) + ", " + isChecked.get(position);


    }


}
