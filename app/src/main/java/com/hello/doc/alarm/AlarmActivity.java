package com.hello.doc.alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hello.doc.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private TextView nameAlarm, amountAlarm;
    private ImageView urlAlarm;
    private SharedPreferences sharedPreferences;
    private Button rejectAlarm;
    private Boolean isRunning = true;
    private Date calendar;
    private String currentDateTime;
    private SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        long[] waves = {500, 500 ,500};

        df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        calendar = Calendar.getInstance().getTime();
        currentDateTime = df.format(calendar);
        sharedPreferences = this.getSharedPreferences("com.hello.doc", Context.MODE_PRIVATE);
        rejectAlarm = findViewById(R.id.rejectAlarm);


        //REMINDER'S DATA SETS STORED IN SHARED PREFERENCES ARE SPLIT WITH '%!%' SEPARATOR
        String[] reminderData = sharedPreferences.getString("reminderData", "").split("%!%");
        nameAlarm = findViewById(R.id.medicineAlarm);
        amountAlarm = findViewById(R.id.amountAlarm);
        urlAlarm = findViewById(R.id.imageAlarm);

        for (int i=0; i<reminderData.length; i++)
        {
            if (reminderData[i].contains(currentDateTime))
            {
                //REMINDER'S DATA ARE SPLIT WITH SPACES, EACH DATA SET CONTAINS NAME, IMAGE URL AND AMOUNT OF MEDICINE
                // FIRST CELL CONTAINS NAME, SECOND IMAGE URL AND THIRD AMOUNT

                String name = reminderData[i].replace(currentDateTime, "").split("  ")[1];
                String imageUrl = reminderData[i].replace(currentDateTime, "").split("  ")[0];
                String amount = reminderData[i].replace(currentDateTime, "").split("  ")[2];

                nameAlarm.setText(name);
                amountAlarm.setText("Dawka: " + amount);
                Picasso.with(this).load(imageUrl).into(urlAlarm);

                sharedPreferences.edit().putString("reminderData", sharedPreferences.getString("reminderData", "").replace(reminderData[i], "")).apply();
            }
        }

        String[] reminders = sharedPreferences.getString("lekiReminder", "").split("%!%");
        String newReminders = "";

        //WE HAVE TO DELETE CURRENT REMINDER'S DATE AND REMOVE IT FROME MEDECINE'S REMINDERS DATA SET
        for (int i=0; i<reminders.length; i++){
            if (reminders[i].contains(currentDateTime)){
                reminders[i] = "";
                continue;
            }
            newReminders+=reminders[i] + "%!%";
        }

        //WE PUT NEW REMINDERS WITHOUT CURRENT ONE
        sharedPreferences.edit().putString("lekiReminder", newReminders).apply();

        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createWaveform(waves, 0));

        rejectAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                finish();
                mediaPlayer.stop();
                vibrator.cancel();
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.analog_watch_alarm_daniel_simion);
        mediaPlayer.start();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }


}
