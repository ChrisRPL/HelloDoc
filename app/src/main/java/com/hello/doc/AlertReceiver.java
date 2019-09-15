package com.hello.doc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Window;

import androidx.core.app.NotificationCompat;

import java.nio.channels.Channel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AlertReceiver extends BroadcastReceiver {
    String name, imageUrl, amount;
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent();
        intent1.setClassName("com.hello.doc", "com.hello.doc.AlarmActivity");
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.hello.doc", Context.MODE_PRIVATE);
        String[] daneLekarstw = sharedPreferences.getString("lekiDane", "").split("%!%");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date calendar = Calendar.getInstance().getTime();
        String currentDateTime = df.format(calendar);
        String[] danePrzypomnien = sharedPreferences.getString("remindDane", "").split("%!%");
        String dawka = "", nazwa = "", zawartosc = "", nowyLekiDane = "", nowaZawartosc = "", rodzajDawki = "";

        for (int i=0; i<danePrzypomnien.length; i++){
            if (danePrzypomnien[i].contains(currentDateTime)){
                dawka = danePrzypomnien[i].replace(currentDateTime, "").split("  ")[2];
                nazwa = danePrzypomnien[i].replace(currentDateTime, "").split("  ")[1];
            }
        }

        for (int i=0; i<daneLekarstw.length; i++){
            if (daneLekarstw[i].contains(nazwa)){
                zawartosc = daneLekarstw[i].split("  ")[2];
                Log.i("INFO", zawartosc);
                rodzajDawki = zawartosc.split(" ")[1];
                nowaZawartosc = Integer.toString(Integer.parseInt(zawartosc.split(" ")[0]) - Integer.parseInt(dawka.split(" ")[0])) + " " + rodzajDawki;
                if (Integer.parseInt(nowaZawartosc.replace(" " + rodzajDawki, "")) <= 0)
                    nowaZawartosc = "brak";
                daneLekarstw[i] = daneLekarstw[i].replace(zawartosc, nowaZawartosc);
            }
            nowyLekiDane+=daneLekarstw[i] + "%!%";
        }
        sharedPreferences.edit().putString("lekiDane", nowyLekiDane).apply();

        String[] markedDates = sharedPreferences.getString("datesToMark", "").split("%!%");
        String pomoc = "";

        for (int i=0; i<markedDates.length; i++){
            if (markedDates[i].contains(currentDateTime)){
                markedDates[i] = "";
                continue;
            }
            pomoc += markedDates[i] + "%!%";
        }
        sharedPreferences.edit().putString("datesToMark", pomoc).apply();



        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent1);
    }
}
