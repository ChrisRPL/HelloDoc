package com.hello.doc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class MakeRemind extends AppCompatActivity {
    TextView dateLabel, timeLabel,amountLabel, labelUp, labelInside;
    CardView cardList, dateCard, timeCard, amountCard;
    ImageButton dateButton, timeButton, amountButton;
    Button addButton, setButton, setButton2, setButton3, setButton4, setButton5, setButton6;
    ListView remindersList;
    RemindersAdapter remindersAdapter;
    ArrayList<String> date, time, amount;
    ArrayList<Boolean> isChecked;
    MCalendarView calendarReminder;
    TimePicker timePicker;
    EditText editText;
    Bundle bundle;
    SharedPreferences sharedPreferences;
    String danePrzypomnienia = "";
    RadioButton tabletkiReminder, mililitryReminder, dawkiReminder;



    public void disableBackground(){
        labelUp.setAlpha(new Float(0.5));
        dateButton.setEnabled(false);
        timeButton.setEnabled(false);
        amountButton.setEnabled(false);
        dateButton.setAlpha(127);
        timeButton.setAlpha(127);
        amountButton.setAlpha(127);
        cardList.setAlpha(new Float(0.5));
        addButton.setEnabled(false);
        addButton.setAlpha(new Float(0.5));

    }

    public void enableBackground(){
        if (date.size() == 0){
            timeButton.setEnabled(false);
            amountButton.setEnabled(false);

            timeButton.setAlpha(60);
            amountButton.setAlpha(60);
        }else {
            timeButton.setEnabled(true);
            amountButton.setEnabled(true);

            timeButton.setAlpha(255);
            amountButton.setAlpha(255);
        }

        labelUp.setAlpha(new Float(1));
        dateButton.setEnabled(true);
        dateButton.setAlpha(255);
        cardList.setAlpha(new Float(1));
        addButton.setEnabled(true);
        addButton.setAlpha(new Float(1));
        labelInside.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_remind);

        dateLabel = findViewById(R.id.dateLabel);
        timeLabel = findViewById(R.id.timeLabel);
        amountLabel = findViewById(R.id.amountLabel);
        labelUp = findViewById(R.id.labelUp);
        cardList = findViewById(R.id.cardList);
        addButton = findViewById(R.id.addButton);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        amountButton = findViewById(R.id.amountButton);
        setButton = findViewById(R.id.setButton);
        calendarReminder = findViewById(R.id.calendarReminder);
        timeCard = findViewById(R.id.timeCard);
        dateCard = findViewById(R.id.dateCard);
        amountCard = findViewById(R.id.amountCard);
        setButton2 = findViewById(R.id.setButton2);
        setButton3 = findViewById(R.id.setButton3);
        timePicker = findViewById(R.id.timePicker);
        labelInside = findViewById(R.id.textView14);
        editText = findViewById(R.id.editText2);
        bundle = getIntent().getExtras();
        sharedPreferences = this.getSharedPreferences("com.hello.doc", Context.MODE_PRIVATE);
        tabletkiReminder = findViewById(R.id.tabletkiReminder);
        mililitryReminder = findViewById(R.id.mililitryReminder);
        dawkiReminder = findViewById(R.id.dawkiReminder);
        setButton4 = findViewById(R.id.setButton4);
        setButton5 = findViewById(R.id.setButton5);
        setButton6 = findViewById(R.id.setButton6);



        timeButton.setEnabled(false);
        amountButton.setEnabled(false);

        timeButton.setAlpha(60);
        amountButton.setAlpha(60);


        tabletkiReminder.setChecked(true);

        tabletkiReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mililitryReminder.setChecked(false);
                dawkiReminder.setChecked(false);
            }
        });

        setButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelInside.setVisibility(View.INVISIBLE);
                dateCard.animate().translationY(-2000).setDuration(1000);
                dateCard.setVisibility(View.VISIBLE);
                enableBackground();
            }
        });

        setButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCard.animate().translationY(-2000).setDuration(1000);
                timeCard.setVisibility(View.VISIBLE);
                enableBackground();
            }
        });

        setButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mililitryReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabletkiReminder.setChecked(false);
                dawkiReminder.setChecked(false);
            }
        });

        dawkiReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabletkiReminder.setChecked(false);
                mililitryReminder.setChecked(false);
            }
        });



        date = new ArrayList<>();
        time = new ArrayList<>();
        amount = new ArrayList<>();
        isChecked = new ArrayList<>();

        dateCard.setY(-1000);
        timeCard.setY(-1000);
        amountCard.setY(-1000);



        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableBackground();
                dateCard.setVisibility(View.VISIBLE);
                dateCard.animate().translationY(0).setDuration(1000);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableBackground();
                timeCard.setVisibility(View.VISIBLE);
                timeCard.animate().translationY(0).setDuration(1000);
            }
        });

        amountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableBackground();
                amountCard.setVisibility(View.VISIBLE);
                amountCard.animate().translationY(0).setDuration(1000);
            }
        });

        final ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<date.size(); i++) {
                    Calendar c = Calendar.getInstance();


                   // c.set(Calendar.YEAR, Integer.parseInt(date.get(i).split("/")[2]));
                    c.set(Calendar.MONTH, Integer.parseInt(date.get(i).split("/")[1]) - 1);
                    c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.get(i).split("/")[0]));
                    c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.get(i).split(":")[0]));
                    c.set(Calendar.MINUTE, Integer.parseInt(time.get(i).split(":")[1]));
                    c.set(Calendar.SECOND, 0);
                    Log.i("asdasdasdasd", c.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() + "");

                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), sharedPreferences.getInt("requestCode", 0) , intent, 0);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                    intentArray.add(pendingIntent);
                    sharedPreferences.edit().putInt("requestCode", sharedPreferences.getInt("requestCode", 0)).apply();
                    sharedPreferences.edit().putString("lekiReminder", sharedPreferences.getString("lekiReminder", "") + bundle.getString("medicineName") + date.get(i) + " " + time.get(i) + ";" + "%!%").apply();
                    sharedPreferences.edit().putString("remindDane", sharedPreferences.getString("remindDane", "") + bundle.getString("urlImage") + "  " + bundle.getString("medicineName") + date.get(i) + " " + time.get(i) + "  " + amount.get(i) + "  " + sharedPreferences.getInt("requestCode", 0) + "%!%").apply();
                    sharedPreferences.edit().putString("datesToMark", sharedPreferences.getString("datesToMark", "") + date.get(i) + " " + time.get(i) + "%!%").apply();
                    Log.i("sadasdasda", Integer.toString(sharedPreferences.getInt("requestCode", 0)));
                    sharedPreferences.edit().putInt("requestCode", sharedPreferences.getInt("requestCode", 0) + 1).apply();
                    Log.i("sadasdasda", Integer.toString(sharedPreferences.getInt("requestCode", 0)));
                }


                String[] pomoc = sharedPreferences.getString("lekiReminder", "").split("%!%");
                String pomocStr = "";

                for (int i=0; i<pomoc.length; i++){
                    if (pomoc[i].contains(bundle.getString("medicineName", "") + "brak przypomnienia")){
                        pomoc[i] = "";
                    }
                    pomocStr+=pomoc[i] + "%!%";
                }
                sharedPreferences.edit().putString("lekiReminder",  pomocStr).apply();
                Toast.makeText(MakeRemind.this, "Przypomnienie ustawione na!", Toast.LENGTH_SHORT).show();

            }
        });

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelInside.setVisibility(View.INVISIBLE);
                dateCard.animate().translationY(-2000).setDuration(1000);
                dateCard.setVisibility(View.VISIBLE);
                enableBackground();
                date = new ArrayList<String>();
                isChecked = new ArrayList<>();

                timeButton.setEnabled(true);
                amountButton.setEnabled(true);
                timeButton.setAlpha(255);
                timeButton.setAlpha(255);


                ArrayList<DateData> dateData = calendarReminder.getMarkedDates().getAll();
                for (int i=0; i<dateData.size(); i++)
                {
                    String dzien, miesiac;

                   if (dateData.get(i).getDay() == 1 || dateData.get(i).getDay() == 2 || dateData.get(i).getDay() == 3 ||
                           dateData.get(i).getDay() == 4 || dateData.get(i).getDay() == 5 || dateData.get(i).getDay() == 6 ||
                           dateData.get(i).getDay() == 7 || dateData.get(i).getDay() == 8 || dateData.get(i).getDay() == 9 )
                       dzien = "0" + Integer.toString(dateData.get(i).getDay());
                   else
                       dzien = Integer.toString(dateData.get(i).getDay());

                    if (dateData.get(i).getMonth() == 1 || dateData.get(i).getMonth() == 2 || dateData.get(i).getMonth() == 3 ||
                            dateData.get(i).getMonth() == 4 || dateData.get(i).getMonth() == 5 || dateData.get(i).getMonth() == 6 ||
                            dateData.get(i).getMonth() == 7 || dateData.get(i).getMonth() == 9 || dateData.get(i).getMonth() == 9 )
                        miesiac = "0" + Integer.toString(dateData.get(i).getMonth());
                    else
                        miesiac = Integer.toString(dateData.get(i).getMonth());

                    date.add(dzien +"/"+ miesiac +"/"+ Integer.toString(dateData.get(i).getYear()));
                    Log.i("adsadasdasdasd", date.get(i));
                    time.add("");
                    amount.add("");
                    isChecked.add(false);
                }
                remindersAdapter = new RemindersAdapter(MakeRemind.this, date, time, amount, isChecked);
                remindersList = findViewById(R.id.insideList);
                remindersList.setAdapter(remindersAdapter);

                for (int i=0; i<isChecked.size(); i++){
                    isChecked.set(i, false);
                }




            }
        });

        setButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCard.animate().translationY(-2000).setDuration(1000);
                timeCard.setVisibility(View.VISIBLE);
                enableBackground();


                for (int i=0; i<date.size(); i++){
                    Log.i("asdasdasdasd", remindersAdapter.getItem(i));
                    if (isChecked.get(i)) {
                        String godzina, minuta;
                        if (timePicker.getMinute() == 0||timePicker.getMinute() == 1||timePicker.getMinute() == 2||
                                timePicker.getMinute() == 3||timePicker.getMinute() == 4||timePicker.getMinute() == 5||
                                timePicker.getMinute() == 6||timePicker.getMinute() == 7||timePicker.getMinute() == 8||
                                timePicker.getMinute() == 9)
                            minuta = "0" + Integer.toString(timePicker.getMinute());
                        else
                            minuta = Integer.toString(timePicker.getMinute());

                        if (timePicker.getHour() == 0||timePicker.getHour() == 1||timePicker.getHour() == 2||
                                timePicker.getHour() == 3||timePicker.getHour() == 4||timePicker.getHour() == 5||
                                timePicker.getHour() == 6||timePicker.getHour() == 7||timePicker.getHour() == 8||
                                timePicker.getHour() == 9)
                            godzina = "0" + Integer.toString(timePicker.getHour());
                        else
                            godzina = Integer.toString(timePicker.getHour());

                        time.set(i, godzina + ":" + minuta);
                    }

                }
                remindersAdapter = new RemindersAdapter(MakeRemind.this, date, time, amount, isChecked);
                remindersList = findViewById(R.id.insideList);
                remindersList.setAdapter(remindersAdapter);


                for (int i=0; i<isChecked.size(); i++){
                    isChecked.set(i, false);
                }
            }


        });

        setButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountCard.animate().translationY(-2000).setDuration(1000);
                amountCard.setVisibility(View.VISIBLE);
                enableBackground();
                String dawka = editText.getText().toString();

                if (tabletkiReminder.isChecked()){
                    if (editText.getText().toString().equals("1"))
                        dawka = editText.getText().toString() + " tabletka";
                    else if (editText.getText().toString().equals("2") || editText.getText().toString().equals("3") || editText.getText().toString().equals("4"))
                        dawka = editText.getText().toString() + " tabletki";
                    else
                        dawka = editText.getText().toString() + " tabletek";
                }

                if (mililitryReminder.isChecked()){
                    if (editText.getText().toString().equals("1"))
                        dawka = editText.getText().toString() + " mililitr";
                    else if (editText.getText().toString().equals("2") || editText.getText().toString().equals("3") || editText.getText().toString().equals("4"))
                        dawka = editText.getText().toString() + " mililitry";
                    else
                        dawka = editText.getText().toString() + " mililitrów";
                }

                if (dawkiReminder.isChecked()){
                    if (editText.getText().toString().equals("1"))
                        dawka = editText.getText().toString() + " dawka";
                    else if (editText.getText().toString().equals("2") || editText.getText().toString().equals("3") || editText.getText().toString().equals("4"))
                        dawka = editText.getText().toString() + " dawki";
                    else
                        dawka = editText.getText().toString() + " dawek";
                }

                for (int i=0; i<date.size(); i++){
                    if (isChecked.get(i))
                        amount.set(i, dawka);

                }
                remindersAdapter = new RemindersAdapter(MakeRemind.this, date, time, amount, isChecked);
                remindersList = findViewById(R.id.insideList);
                remindersList.setAdapter(remindersAdapter);

                for (int i=0; i<isChecked.size(); i++){
                    isChecked.set(i, false);
                }
            }

        });

        calendarReminder.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                calendarReminder.markDate(date);
            }
        });

        List<DateData> lista = calendarReminder.getMarkedDates().getAll();

        for (int i=0; i<lista.size(); i++){
            calendarReminder.unMarkDate(lista.get(i));
        }
    }
}
