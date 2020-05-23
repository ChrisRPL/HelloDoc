package com.hello.doc.reminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.hello.doc.R;
import com.hello.doc.alarm.AlertReceiver;

import java.util.ArrayList;

public class RemindersListAdapter extends ArrayAdapter<String> {
    private final Activity context1;
    private final ArrayList<String> timeClock, medicineText, amountText, dateRemind;
    SharedPreferences sharedPreferences;


    public RemindersListAdapter(Activity context1, ArrayList<String> timeClock, ArrayList<String> medicineText, ArrayList<String> amountText, ArrayList<String> dateRemind) {
        super(context1, R.layout.list_reminders, timeClock);
        this.context1 = context1;
        this.timeClock = timeClock;
        this.medicineText = medicineText;
        this.amountText = amountText;
        this.dateRemind = dateRemind;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater=context1.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_reminders, null,true);
        TextView timeClockView = rowView.findViewById(R.id.remindHour);
        TextView medicineTextView = rowView.findViewById(R.id.remindMedic);
        TextView amountTextView = rowView.findViewById(R.id.amountMedic);
        ImageButton delete = rowView.findViewById(R.id.deleteMedicRemind);
        sharedPreferences = context1.getSharedPreferences("com.hello.doc", Context.MODE_PRIVATE);

        timeClockView.setText(timeClock.get(position));
        medicineTextView.setText("Nazwa leku: " + medicineText.get(position));
        amountTextView.setText("Dawka: " + amountText.get(position));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Usuwanie przypomnienia")
                        .setMessage("Czy na pewno chcesz usunąć te przypomnienie?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String dateTime = dateRemind.get(position) + " " + timeClock.get(position);
                                int requestCode = 0;
                                String[] parseData = sharedPreferences.getString("reminderData", "").split("%!%");
                                String[] przypomnienia = sharedPreferences.getString("lekiReminder", "").split("%!%");
                                String[] datesToMark = sharedPreferences.getString("datesToMark", "").split("%!%");
                                String newreminderData = "";
                                String newPrzypomnienia = "";
                                String noweDatesToMark = "";

                                for (int i=0; i<parseData.length; i++){
                                    if (parseData[i].contains(dateTime)){
                                        requestCode = Integer.parseInt(parseData[i].split("  ")[3]);
                                        parseData[i] = "";
                                        continue;
                                    }
                                    newreminderData += parseData[i] + "%!%";
                                }

                                for (int i=0; i<datesToMark.length; i++){
                                    Log.i("DATES TO MARK AND DATETIME", datesToMark[i] + "  |||  " + dateTime);
                                    if (datesToMark[i].contains(dateTime)){
                                        datesToMark[i] = "";
                                        continue;
                                    }
                                    noweDatesToMark += datesToMark[i] + "%!%";
                                }

                                for (int i=0; i<przypomnienia.length; i++){
                                    if (przypomnienia[i].contains(dateTime)){
                                        przypomnienia[i] = "";
                                        continue;
                                    }
                                    newPrzypomnienia += przypomnienia[i] + "%!%";
                                }

                                timeClock.remove(position);
                                medicineText.remove(position);
                                amountText.remove(position);
                                dateRemind.remove(position);



                                sharedPreferences.edit().putString("reminderData", newreminderData).apply();
                                sharedPreferences.edit().putString("lekiReminder", newPrzypomnienia).apply();
                                sharedPreferences.edit().putString("datesToMark", noweDatesToMark).apply();

                                AlarmManager alarmManager = (AlarmManager) context1.getSystemService(Context.ALARM_SERVICE);
                                Intent myIntent = new Intent(context1, AlertReceiver.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        context1, requestCode, myIntent, 0);

                                alarmManager.cancel(pendingIntent);
                               // new CalendarPage().setAmountText(new ArrayList<String>());
                               // new CalendarPage().setDateRemind(new ArrayList<String>());
                               // new CalendarPage().setMedicineText(new ArrayList<String>());
                               // new CalendarPage().setTimeClock(new ArrayList<String>());
                                notifyDataSetChanged();



                                Toast.makeText(context1, "Przypomnienie zostało usunięte!", Toast.LENGTH_SHORT).show();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        return rowView;

    }
}
