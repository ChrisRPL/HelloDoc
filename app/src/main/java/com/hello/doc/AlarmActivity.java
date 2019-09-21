package com.hello.doc;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    TextView nameAlarm, amountAlarm;
    ImageView urlAlarm;
    SharedPreferences sharedPreferences;
    Button odrzucButton;
    Boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        long[] waves = {500, 500 ,500};

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date calendar = Calendar.getInstance().getTime();
        String currentDateTime = df.format(calendar);
        Log.i("data", currentDateTime);
        sharedPreferences = this.getSharedPreferences("com.hello.doc", Context.MODE_PRIVATE);
        odrzucButton = findViewById(R.id.odrzucButton);



        String[] daneReminder = sharedPreferences.getString("remindDane", "").split("%!%");
        nameAlarm = findViewById(R.id.medicineAlarm);
        amountAlarm = findViewById(R.id.amountAlarm);
        urlAlarm = findViewById(R.id.imageAlarm);

        for (int i=0; i<daneReminder.length; i++)
        {
            if (daneReminder[i].contains(currentDateTime))
            {
                String name = daneReminder[i].replace(currentDateTime, "").split("  ")[1];
                String imageUrl = daneReminder[i].replace(currentDateTime, "").split("  ")[0];
                String amount = daneReminder[i].replace(currentDateTime, "").split("  ")[2];

                nameAlarm.setText(name);
                amountAlarm.setText("Dawka: " + amount);
                Picasso.with(this).load(imageUrl).into(urlAlarm);

                sharedPreferences.edit().putString("remindDane", sharedPreferences.getString("remindDane", "").replace(daneReminder[i], "")).apply();
            }
        }

        String[] przypomnienia = sharedPreferences.getString("lekiReminder", "").split("%!%");
        String nowePrzypomnienia = "";

        for (int i=0; i<przypomnienia.length; i++){
            if (przypomnienia[i].contains(currentDateTime)){
                przypomnienia[i] = "";
                continue;
            }
            nowePrzypomnienia+=przypomnienia[i] + "%!%";
        }
        sharedPreferences.edit().putString("lekiReminder", nowePrzypomnienia).apply();










        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        vibrator.vibrate(VibrationEffect.createWaveform(waves, 0));

        odrzucButton.setOnClickListener(new View.OnClickListener() {
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
