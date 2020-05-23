package com.hello.doc.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlertReceiver extends BroadcastReceiver {

    private Intent alarmIntent;
    private SharedPreferences sharedPreferences;
    private String[] daneLekarstw;
    private SimpleDateFormat df;
    private Date calendar;
    private String currentDateTime;
    private String[] remindersData;
    private String dosage, medicineName, medicineContent, newMedicinesData, newMedicineContent, kindOfDosage;
    
    @Override
    public void onReceive(Context context, Intent intent) {

        alarmIntent = new Intent();
        alarmIntent.setClassName("com.hello.doc", "com.hello.doc.alarm.AlarmActivity");
        sharedPreferences = context.getSharedPreferences("com.hello.doc", Context.MODE_PRIVATE);
        daneLekarstw = sharedPreferences.getString("medicinesData", "").split("%!%");
        df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        calendar = Calendar.getInstance().getTime();
        currentDateTime = df.format(calendar);
        remindersData = sharedPreferences.getString("reminderData", "").split("%!%");

        for (int i=0; i<remindersData.length; i++){
            if (remindersData[i].contains(currentDateTime)){
                dosage = remindersData[i].replace(currentDateTime, "").split("  ")[2];
                medicineName = remindersData[i].replace(currentDateTime, "").split("  ")[1];
            }
        }

        for (int i=0; i<daneLekarstw.length; i++){
            if (daneLekarstw[i].contains(medicineName)){
                medicineContent = daneLekarstw[i].split("  ")[2];

                kindOfDosage = medicineContent.split(" ")[1];
                newMedicineContent = Integer.toString(Integer.parseInt(medicineContent.split(" ")[0]) - Integer.parseInt(dosage.split(" ")[0])) + " " + kindOfDosage;
                if (Integer.parseInt(newMedicineContent.replace(" " + kindOfDosage, "")) <= 0)
                    newMedicineContent = "brak";
                daneLekarstw[i] = daneLekarstw[i].replace(medicineContent, newMedicineContent);
            }
            newMedicinesData+=daneLekarstw[i] + "%!%";
        }
        sharedPreferences.edit().putString("medicinesData", newMedicinesData).apply();

        String[] markedDates = sharedPreferences.getString("datesToMark", "").split("%!%");
        String splittedMarkedDates = "";

        for (int i=0; i<markedDates.length; i++){
            if (markedDates[i].contains(currentDateTime)){
                markedDates[i] = "";
                continue;
            }
            splittedMarkedDates += markedDates[i] + "%!%";
        }
        sharedPreferences.edit().putString("datesToMark", splittedMarkedDates).apply();



        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(alarmIntent);
    }
}
