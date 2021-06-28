package com.hello.doc;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hello.doc.reminder.RemindersListAdapter;

import java.util.ArrayList;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class CalendarPage extends Fragment {
    private ListView listView;
    private RemindersListAdapter remindersListAdapter;
    private ArrayList<String> timeClock, medicineText, amountText, dateRemind;
    private String[] remindersOfDate;
    private SharedPreferences sharedPreferences;
    private TextView textView;
    private MCalendarView mCalendarView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calendar, container, false);
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
        listView = getView().findViewById(R.id.list);
        textView = getView().findViewById(R.id.textView3);
        mCalendarView.init(getActivity());

        final String[] datesToMark = sharedPreferences.getString("datesToMark", "").split("%!%");
        List<DateData> datesDataSet = new ArrayList<>();

        mCalendarView.getMarkedDates().getAll().clear();


        //IF THERE ARE REMINDERS IN SHARED PREFERENCES, WE HAVE TO MARK THEM ON CALENDAR
        if (!sharedPreferences.getString("datesToMark", "").equals("")) {
            for (int i = 0; i < datesToMark.length; i++) {
                String date = datesToMark[i].split(" ")[0];
                String day = date.split("/")[0], month = date.split("/")[1], year = date.split("/")[2];

                switch (day) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                        day = "0" + day;
                        break;
                }

                switch (month) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                        month = "0" + month;
                        break;
                }
                mCalendarView.markDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
                datesDataSet.add(new DateData(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));

            }
        }

        mCalendarView.setOnDateClickListener(new OnDateClickListener() {

            List<DateData> dates = new ArrayList<>();

            @Override
            public void onDateClick(View view, DateData date) {

                //FIRST WE IMPORT ALL REMINDERS THAT WE HAVE IN SHARED PREFERENCES
                remindersOfDate = sharedPreferences.getString("reminderData", "").split("%!%");

                //THEN WE IMPORT AGAIN DATES TO CHECK, IF WE CLICKED A DATE THA IS NOT MARKED ON CALENDAR
                String[] datesToMarkAfterClick = sharedPreferences.getString("datesToMark", "").split("%!%");

                if (datesToMarkAfterClick.length != datesToMark.length) {

                    //WE CLEAR ALL MARKS TO MARK NEW DATES
                    mCalendarView.getMarkedDates().getAll().clear();

                    if (!sharedPreferences.getString("datesToMark", "").equals("")) {
                        for (int i = 0; i < datesToMarkAfterClick.length; i++) {
                            String newMarkedDate = datesToMarkAfterClick[i].split(" ")[0];
                            String dayToMark = newMarkedDate.split("/")[0], monthToMark = newMarkedDate.split("/")[1], yearToMark = newMarkedDate.split("/")[2];

                            switch (dayToMark) {
                                case "1":
                                case "2":
                                case "3":
                                case "4":
                                case "5":
                                case "6":
                                case "7":
                                case "8":
                                case "9":
                                    dayToMark = "0" + dayToMark;
                                    break;
                            }

                            switch (monthToMark) {
                                case "1":
                                case "2":
                                case "3":
                                case "4":
                                case "5":
                                case "6":
                                case "7":
                                case "8":
                                case "9":
                                    monthToMark = "0" + monthToMark;
                                    break;
                            }
                            mCalendarView.markDate(Integer.parseInt(yearToMark), Integer.parseInt(monthToMark), Integer.parseInt(dayToMark));

                        }
                    }
                }

                dates.add(date);

                date.setMarkStyle(new MarkStyle().setColor(Color.RED));

                //WE SHOW ONLY ONE MARKED DATE, SO WE HAVE TO UNMARK PREVIOUS MARKED DATE
                if (dates.size() > 1) {
                    mCalendarView.unMarkDate(dates.get(dates.size() - 2));
                    mCalendarView.markDate(dates.get(dates.size() - 1));
                } else if (dates.size() == 1)
                    mCalendarView.markDate(dates.get(0));

                int dayOfMonth = date.getDay(), year = date.getYear(), monthOfYear = date.getMonth();

                //NOW WE CHECK, IF THERE ARE MEDICINES WITH REMINDERS UNDER CLICKED DATE
                String remindersDay, remindersMonth, remindersYear = year + "";
                timeClock.clear();
                medicineText.clear();
                dateRemind.clear();
                amountText.clear();

                switch (dayOfMonth) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        remindersDay = "0" + dayOfMonth;
                        break;
                    default:
                        remindersDay = dayOfMonth + "";
                        break;
                }

                switch (monthOfYear) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        remindersMonth = "0" + (monthOfYear);
                        break;
                    default:
                        remindersMonth = "" + (monthOfYear);
                        break;
                }

                String remindersDate = remindersDay + "/" + remindersMonth + "/" + remindersYear;

                //IF THERE ARE REMINDERS UNDER CLICKED DATE, WE SHOW THEM UNDER CALENDAR
                for (String s : remindersOfDate) {
                    if (s.contains(remindersDate)) {
                        String[] nameAndTime = s.replace(remindersDate, "").split("  ")[1].split(" ");
                        String medicineLabel = "";

                        for (int j = 0; j < nameAndTime.length - 1; j++)
                            medicineLabel += nameAndTime[j] + " ";

                        timeClock.add(s.replace(remindersDate, "").split("  ")[1].split(" ")[nameAndTime.length - 1]);
                        medicineText.add(medicineLabel);
                        dateRemind.add(remindersDate);
                        amountText.add(s.replace(remindersDate, "").split("  ")[2]);
                    }
                }

                //AFTER THAT WE REFRESH THE LIST WITH MEDICINES AND REMINDERS
                remindersListAdapter = new RemindersListAdapter(getActivity(), timeClock, medicineText, amountText, dateRemind);
                listView.setAdapter(remindersListAdapter);

                //IF THERE ARE NO REMINDERS, WE SHOW TEXT VIEW WITH INFO
                if (timeClock.size() != 0) {
                    textView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }

            }
        });


    }
}
