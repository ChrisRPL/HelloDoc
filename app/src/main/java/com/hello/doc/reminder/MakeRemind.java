package com.hello.doc.reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hello.doc.R;
import com.hello.doc.alarm.AlertReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class MakeRemind extends AppCompatActivity {
    private TextView dateLabel, timeLabel,amountLabel, labelUp, labelInside;
    private CardView cardList, dateCard, timeCard, amountCard;
    private ImageButton dateButton, timeButton, amountButton;
    private Button addButton, selectDates, setRemindersTime, setMedicinesDosage, setButton4, setButton5, setButton6;
    private ListView remindersList;
    private RemindersAdapter remindersAdapter;
    private ArrayList<String> date, time, amount;
    private ArrayList<Boolean> isChecked;
    private MCalendarView calendarReminder;
    private TimePicker timePicker;
    private EditText editText;
    private Bundle bundle;
    private SharedPreferences sharedPreferences;
    private RadioButton dosagePills, dosageMillilitres, dosageDefault;


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

    public Boolean isNumber(String number) {
        for (int i=92; i<=122; i++) {
            if (number.toLowerCase().contains(Character.toString((char)i)) || number.contains(",") || number.contains("."))
                return false;
        }
        return true;
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
        selectDates = findViewById(R.id.selectDates);
        calendarReminder = findViewById(R.id.calendarReminder);
        timeCard = findViewById(R.id.timeCard);
        dateCard = findViewById(R.id.dateCard);
        amountCard = findViewById(R.id.amountCard);
        setRemindersTime = findViewById(R.id.setRemindersTime);
        setMedicinesDosage = findViewById(R.id.setMedicinesDosage);
        timePicker = findViewById(R.id.timePicker);
        labelInside = findViewById(R.id.textView14);
        editText = findViewById(R.id.editText2);
        bundle = getIntent().getExtras();
        sharedPreferences = this.getSharedPreferences("com.hello.doc", Context.MODE_PRIVATE);
        dosagePills = findViewById(R.id.dosagePills);
        dosageMillilitres = findViewById(R.id.dosageMillilitres);
        dosageDefault = findViewById(R.id.dosageDefault);
        setButton4 = findViewById(R.id.setButton4);
        setButton5 = findViewById(R.id.setButton5);
        setButton6 = findViewById(R.id.setButton6);

        calendarReminder.init(this);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date calendar = Calendar.getInstance().getTime();
        final String currentDateTime = df.format(calendar);

        timeButton.setEnabled(false);
        amountButton.setEnabled(false);

        timeButton.setAlpha(60);
        amountButton.setAlpha(60);


        dosagePills.setChecked(true);

        dosagePills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dosageMillilitres.setChecked(false);
                dosageDefault.setChecked(false);
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
                amountCard.animate().translationY(-2000).setDuration(1000);
                amountCard.setVisibility(View.VISIBLE);
                enableBackground();
            }
        });

        dosageMillilitres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dosagePills.setChecked(false);
                dosageDefault.setChecked(false);
            }
        });

        dosageDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dosagePills.setChecked(false);
                dosageMillilitres.setChecked(false);
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
                int size = 0;
                for (int i=0; i<isChecked.size(); i++) {
                    if (isChecked.get(i).equals(true))
                        size++;
                }
                if (size == 0)
                    Toast.makeText(MakeRemind.this, "Najpierw wybierz datę, aby wyznaczyć jej godzinę", Toast.LENGTH_SHORT).show();
                else {
                    disableBackground();
                    timeCard.setVisibility(View.VISIBLE);
                    timeCard.animate().translationY(0).setDuration(1000);
                }
            }
        });

        amountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = 0;
                for (int i=0; i<isChecked.size(); i++) {
                    if (isChecked.get(i).equals(true))
                        size++;
                }
                if (size == 0)
                    Toast.makeText(MakeRemind.this, "Najpierw wybierz datę, aby wyznaczyć dawkę", Toast.LENGTH_SHORT).show();
                else {
                    disableBackground();
                    amountCard.setVisibility(View.VISIBLE);
                    amountCard.animate().translationY(0).setDuration(1000);
                }
            }
        });

        final ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date.size()!=0 && !time.contains("") && !amount.contains("")) {
                    for (int i = 0; i < date.size(); i++) {
                        Calendar c = Calendar.getInstance();

                        c.set(Calendar.MONTH, Integer.parseInt(date.get(i).split("/")[1]) - 1);
                        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.get(i).split("/")[0]));
                        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.get(i).split(":")[0]));
                        c.set(Calendar.MINUTE, Integer.parseInt(time.get(i).split(":")[1]));
                        c.set(Calendar.SECOND, 0);


                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), sharedPreferences.getInt("requestCode", 0), intent, 0);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                        intentArray.add(pendingIntent);


                        sharedPreferences.edit().putInt("requestCode", sharedPreferences.getInt("requestCode", 0)).apply();
                        sharedPreferences.edit().putString("lekiReminder", sharedPreferences.getString("lekiReminder", "") + bundle.getString("medicineName") + date.get(i) + " " + time.get(i) + ";" + "%!%").apply();
                        sharedPreferences.edit().putString("reminderData", sharedPreferences.getString("reminderData", "") + bundle.getString("urlImage") + "  " + bundle.getString("medicineName") + date.get(i) + " " + time.get(i) + "  " + amount.get(i) + "  " + sharedPreferences.getInt("requestCode", 0) + "%!%").apply();
                        sharedPreferences.edit().putString("datesToMark", sharedPreferences.getString("datesToMark", "") + date.get(i) + " " + time.get(i) + "%!%").apply();
                        sharedPreferences.edit().putInt("requestCode", sharedPreferences.getInt("requestCode", 0) + 1).apply();
                        sharedPreferences.edit().putBoolean("ifCreated", true).apply();

                    }


                String[] reminders = sharedPreferences.getString("lekiReminder", "").split("%!%");
                String newReminders = "";

                for (int i = 0; i < reminders.length; i++) {
                    if (reminders[i].contains(bundle.getString("medicineName", "") + "brak przypomnienia")) {
                        reminders[i] = "";
                    }
                    newReminders += reminders[i] + "%!%";
                }
                sharedPreferences.edit().putString("lekiReminder", newReminders).apply();
                Toast.makeText(MakeRemind.this, "Przypomnienie ustawione pomyślnie!", Toast.LENGTH_SHORT).show();
                finish();
            }
                else
                    Toast.makeText(MakeRemind.this, "Wypełnij godzinę oraz dawkę dla każdej daty", Toast.LENGTH_SHORT).show();

            }
        });

        selectDates.setOnClickListener(new View.OnClickListener() {
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
                amountButton.setAlpha(255);


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

        setRemindersTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCard.animate().translationY(-2000).setDuration(1000);
                timeCard.setVisibility(View.VISIBLE);
                enableBackground();


                for (int i=0; i<date.size(); i++){
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

        setMedicinesDosage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("") || isNumber(editText.getText().toString())) {
                    amountCard.animate().translationY(-2000).setDuration(1000);
                    amountCard.setVisibility(View.VISIBLE);
                    enableBackground();
                    String dosage = editText.getText().toString();

                    if (dosagePills.isChecked()) {
                        if (editText.getText().toString().equals("1"))
                            dosage = editText.getText().toString() + " tabletka";
                        else if (editText.getText().toString().equals("2") || editText.getText().toString().equals("3") || editText.getText().toString().equals("4"))
                            dosage = editText.getText().toString() + " tabletki";
                        else
                            dosage = editText.getText().toString() + " tabletek";
                    }

                    if (dosageMillilitres.isChecked()) {
                        if (editText.getText().toString().equals("1"))
                            dosage = editText.getText().toString() + " mililitr";
                        else if (editText.getText().toString().equals("2") || editText.getText().toString().equals("3") || editText.getText().toString().equals("4"))
                            dosage = editText.getText().toString() + " mililitry";
                        else
                            dosage = editText.getText().toString() + " mililitrów";
                    }

                    if (dosageDefault.isChecked()) {
                        if (editText.getText().toString().equals("1"))
                            dosage = editText.getText().toString() + " dosage";
                        else if (editText.getText().toString().equals("2") || editText.getText().toString().equals("3") || editText.getText().toString().equals("4"))
                            dosage = editText.getText().toString() + " dawki";
                        else
                            dosage = editText.getText().toString() + " dawek";
                    }

                    for (int i = 0; i < date.size(); i++) {
                        if (isChecked.get(i))
                            amount.set(i, dosage);

                    }
                    remindersAdapter = new RemindersAdapter(MakeRemind.this, date, time, amount, isChecked);
                    remindersList = findViewById(R.id.insideList);
                    remindersList.setAdapter(remindersAdapter);

                    for (int i = 0; i < isChecked.size(); i++) {
                        isChecked.set(i, false);
                    }
                }else
                    Toast.makeText(MakeRemind.this, "Wprowadź prawidłową ilość leku", Toast.LENGTH_SHORT).show();
            }

        });

        calendarReminder.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                if ((date.getDay() < Integer.parseInt(currentDateTime.split(" ")[0].split("/")[0]) && date.getMonth() == Integer.parseInt(currentDateTime.split(" ")[0].split("/")[1]) ) || (date.getMonth() < Integer.parseInt(currentDateTime.split(" ")[0].split("/")[1])) && date.getDay() > Integer.parseInt(currentDateTime.split(" ")[0].split("/")[0] ) || (date.getDay() < Integer.parseInt(currentDateTime.split(" ")[0].split("/")[0]) && date.getMonth() < Integer.parseInt(currentDateTime.split(" ")[0].split("/")[1]) ))
                    Toast.makeText(MakeRemind.this, "Nie można ustawić przypomnienia na dzień w przeszłości", Toast.LENGTH_SHORT).show();
               else if (calendarReminder.getMarkedDates().getAll().contains(date))
                    calendarReminder.unMarkDate(date);
                else
                    calendarReminder.markDate(date);
            }
        });

        List<DateData> datesToUnmark = calendarReminder.getMarkedDates().getAll();

        for (int i=0; i<datesToUnmark.size(); i++){
            calendarReminder.unMarkDate(datesToUnmark.get(i));
        }
    }
}
