package com.hello.doc;

import android.app.ActionBar;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class CalendarPage extends Fragment {
    DatePicker datePicker;
    ListView listView;
    RemindersListAdapter remindersListAdapter;
    ArrayList<String> timeClock, medicineText, amountText, dateRemind;
    String[] arrayListHelp;
    SharedPreferences sharedPreferences;
    TextView textView;
    MCalendarView mCalendarView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calendar, container, false);
    }

    public void setTimeClock(ArrayList<String> timeClock) {
        this.timeClock = timeClock;
    }

    public void setMedicineText(ArrayList<String> medicineText) {
        this.medicineText = medicineText;
    }

    public void setAmountText(ArrayList<String> amountText) {
        this.amountText = amountText;
    }

    public void setDateRemind(ArrayList<String> dateRemind) {
        this.dateRemind = dateRemind;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarView = getView().findViewById(R.id.MCalendarView);
        timeClock = new ArrayList<>();
        medicineText = new ArrayList<>();
        amountText = new ArrayList<>();
        dateRemind = new ArrayList<>();
        sharedPreferences = getContext().getSharedPreferences("com.hello.doc", Context.MODE_PRIVATE);
        arrayListHelp = sharedPreferences.getString("remindDane", "").split("%!%");
        listView = getView().findViewById(R.id.list);
        textView = getView().findViewById(R.id.textView3);

        String[] datesToMark = sharedPreferences.getString("datesToMark", "").split("%!%");
        List<DateData> lista = new ArrayList<>();



        if (!sharedPreferences.getString("datesToMark", "").equals("")) {
            for (int i = 0; i < datesToMark.length; i++) {
                String data = datesToMark[i].split(" ")[0];
                String dzien = data.split("/")[0], miesiac = data.split("/")[1], rok = data.split("/")[2];

                switch (dzien) {
                    case "1":
                        dzien = "0" + dzien;
                        break;
                    case "2":
                        dzien = "0" + dzien;
                        break;
                    case "3":
                        dzien = "0" + dzien;
                        break;
                    case "4":
                        dzien = "0" + dzien;
                        break;
                    case "5":
                        dzien = "0" + dzien;
                        break;
                    case "6":
                        dzien = "0" + dzien;
                        break;
                    case "7":
                        dzien = "0" + dzien;
                        break;
                    case "8":
                        dzien = "0" + dzien;
                        break;
                    case "9":
                        dzien = "0" + dzien;
                        break;
                    default:
                        dzien = dzien;
                        break;
                }

                switch (miesiac) {
                    case "1":
                        miesiac = "0" + miesiac;
                        break;
                    case "2":
                        miesiac = "0" + miesiac;
                        break;
                    case "3":
                        miesiac = "0" + miesiac;
                        break;
                    case "4":
                        miesiac = "0" + miesiac;
                        break;
                    case "5":
                        miesiac = "0" + miesiac;
                        break;
                    case "6":
                        miesiac = "0" + miesiac;
                        break;
                    case "7":
                        miesiac = "0" + miesiac;
                        break;
                    case "8":
                        miesiac = "0" + miesiac;
                        break;
                    case "9":
                        miesiac = "0" + miesiac;
                        break;
                    default:
                        miesiac = miesiac;
                        break;
                }
                mCalendarView.markDate(Integer.parseInt(rok), Integer.parseInt(miesiac), Integer.parseInt(dzien));
                lista.add(new DateData(Integer.parseInt(rok), Integer.parseInt(miesiac), Integer.parseInt(dzien)));

            }
        }

        List<DateData> zaznaczone = mCalendarView.getMarkedDates().getAll();

        for (int i=0; i<zaznaczone.size(); i++){
            mCalendarView.unMarkDate(zaznaczone.get(i));
        }

        for (int i=0; i<lista.size(); i++){
            mCalendarView.markDate(lista.get(i));
        }






        mCalendarView.setOnDateClickListener(new OnDateClickListener() {
            List<DateData> list = new ArrayList<>();
            @Override
            public void onDateClick(View view, DateData date) {

               list.add(date);

               date.setMarkStyle(new MarkStyle().setColor(Color.RED));
               if (list.size()>1) {
                   mCalendarView.unMarkDate(list.get(list.size() - 2));
                   mCalendarView.markDate(list.get(list.size() - 1));
               }else if (list.size() == 1)
                   mCalendarView.markDate(list.get(0));
               Log.i("listsize", Integer.toString(list.size()));






                int dayOfMonth = date.getDay(), year = date.getYear(), monthOfYear = date.getMonth();

                String dzien, miesiac, rok = year + "";
                timeClock.clear();
                medicineText.clear();
                dateRemind.clear();
                amountText.clear();

                Log.i("sdasdasdas", Integer.toString(monthOfYear));

                switch (dayOfMonth){
                    case 1:
                        dzien = "0" + dayOfMonth;
                        break;
                    case 2:
                        dzien = "0" + dayOfMonth;
                        break;
                    case 3:
                        dzien = "0" + dayOfMonth;
                        break;
                    case 4:
                        dzien = "0" + dayOfMonth;
                        break;
                    case 5:
                        dzien = "0" + dayOfMonth;
                        break;
                    case 6:
                        dzien = "0" + dayOfMonth;
                        break;
                    case 7:
                        dzien = "0" + dayOfMonth;
                        break;
                    case 8:
                        dzien = "0" + dayOfMonth;
                        break;
                    case 9:
                        dzien = "0" + dayOfMonth;
                        break;
                    default:
                        dzien = dayOfMonth + "";
                        break;
                }

                switch (monthOfYear ){
                    case 1:
                        miesiac = "0" + (monthOfYear );
                        break;
                    case 2:
                        miesiac = "0" + (monthOfYear );
                        break;
                    case 3:
                        miesiac = "0" + (monthOfYear );
                        break;
                    case 4:
                        miesiac = "0" + (monthOfYear );
                        break;
                    case 5:
                        miesiac = "0" + (monthOfYear );
                        break;
                    case 6:
                        miesiac = "0" + (monthOfYear );
                        break;
                    case 7:
                        miesiac = "0" + (monthOfYear );
                        break;
                    case 8:
                        miesiac = "0" + (monthOfYear );
                        break;
                    case 9:
                        miesiac = "0" + (monthOfYear );
                        break;
                    default:
                        miesiac = "" + (monthOfYear);
                        break;
                }

                String date1 = dzien + "/" + miesiac + "/" + rok;

                for (int i=0; i<arrayListHelp.length; i++){
                    if (arrayListHelp[i].contains(date1)){
                        String[] nameAndTime = arrayListHelp[i].replace(date1, "").split("  ")[1].split(" ");
                        String name = "";

                        for (int j=0; j<nameAndTime.length - 1; j++)
                            name += nameAndTime[j] + " ";

                        timeClock.add(arrayListHelp[i].replace(date1, "").split("  ")[1].split(" ")[nameAndTime.length - 1]);
                        medicineText.add(name);
                        dateRemind.add(date1);
                        amountText.add(arrayListHelp[i].replace(date1, "").split("  ")[2]);
                    }
                }

                remindersListAdapter = new RemindersListAdapter(getActivity(), timeClock, medicineText, amountText, dateRemind);
                listView.setAdapter(remindersListAdapter);

                if (timeClock.size()!=0){
                    textView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                }
                else {
                    listView.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }

            }
        });


    }
}
