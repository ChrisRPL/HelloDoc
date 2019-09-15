package com.hello.doc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import java.util.List;
import java.util.Locale;

public class Medicine extends AppCompatActivity {
    TextView textView, textDescription, textView1, textView2;
    ImageView imageView;
    Bundle extras;
    String imageUrl = "", medicineName = "", description = "", secondDescription = "", remindPomoc = "";
    TextToSpeech textToSpeech;
    FloatingActionButton floatingActionButton, floatingActionButton1;
    SharedPreferences sharedPreferences;
    CardView cardView;
    ScrollView scrollView;
    ConstraintLayout constraintLayout;
    Button button, anuluj, dodaj;
    EditText editText;
    Intent intent;
    RadioButton tabletki, mililitry, dawki;
    Boolean isSpeaking = false;
    String[] pomoc, reminPomoc;
    ListMedicines listMedicines;
    MedicinesAdapter medicinesAdapter;



    public void enableBackground()
    {
        imageView.setAlpha(255);
        textView.setAlpha(1);
        floatingActionButton.setClickable(true);
        floatingActionButton.setAlpha(new Float(255));
        floatingActionButton1.setClickable(true);
        floatingActionButton1.setAlpha(new Float(255));
        textView1.setAlpha(1);
        textView2.setAlpha(1);
        scrollView.setAlpha(1);
        constraintLayout.setAlpha(1);
        button.setClickable(true);
    }

    public void disableBackground()
    {
        imageView.setAlpha(128);
        textView.setAlpha(new Float(0.5));
        floatingActionButton.setClickable(false);
        floatingActionButton.setAlpha(new Float(0.5));
        floatingActionButton1.setClickable(false);
        floatingActionButton1.setAlpha(new Float(0.5));
        textView1.setAlpha(new Float(0.5));
        textView2.setAlpha(new Float(0.5));
        scrollView.setAlpha(new Float(0.5));
        constraintLayout.setAlpha(new Float(0.5));
        button.setClickable(false);
    }

    public MedicinesAdapter getMedicinesAdapter() {
        return medicinesAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        extras = getIntent().getExtras();
        textView = findViewById(R.id.medicine_name);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView4);
        imageView = findViewById(R.id.imageView);
        textDescription = findViewById(R.id.description);
        button = findViewById(R.id.addReminder);
        scrollView = findViewById(R.id.scrollView2);
        constraintLayout = findViewById(R.id.constraintLayout);
        floatingActionButton = findViewById(R.id.speakButton);
        anuluj = findViewById(R.id.anuluj);
        dodaj = findViewById(R.id.dodaj);
        editText = findViewById(R.id.editText);
        floatingActionButton1 = findViewById(R.id.floatingActionButton2);
        sharedPreferences = this.getSharedPreferences("com.hello.doc", Context.MODE_PRIVATE);
        cardView = findViewById(R.id.ilosc_card);
        intent = new Intent(this, MakeRemind.class);
        tabletki = findViewById(R.id.tabletki);
        mililitry = findViewById(R.id.mililitry);
        dawki = findViewById(R.id.dawki);

        cardView.setZ(99);
        cardView.setY(-1000);

        tabletki.setChecked(true);

        tabletki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mililitry.setChecked(false);
                dawki.setChecked(false);
            }
        });

        mililitry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabletki.setChecked(false);
                dawki.setChecked(false);
            }
        });

        dawki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mililitry.setChecked(false);
                tabletki.setChecked(false);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("urlImage", imageUrl);
                intent.putExtra("medicineName", textView.getText().toString());
                startActivity(intent);
            }
        });


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = textToSpeech.setLanguage(Locale.forLanguageTag("pl_PL"));


                    if (result == TextToSpeech.LANG_MISSING_DATA){
                        Toast.makeText(Medicine.this, "Brak wsparcia języka polskiego dla syntezatora mowy na Twoim urządzeniu, zainstaluj wchodząc w ustawienia syntezatora mowy.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSpeaking) {
                    textToSpeech.speak(description, TextToSpeech.QUEUE_FLUSH, null);
                    isSpeaking = true;
                }else {
                    textToSpeech.stop();
                    isSpeaking = false;
                }
            }
        });

        if (extras!=null) {
            medicineName = extras.getString("medicineName", "");
            imageUrl = extras.getString("imgUrl", "");
            description = extras.getString("description", "");
            secondDescription = extras.getString("secondDescription", "");
        }

        textView.setText(medicineName);
        textDescription.setText(description);
        Picasso.with(this).load(imageUrl).into(imageView);

        if (sharedPreferences.getString("lekiDane", "").contains(textView.getText().toString())) {
            floatingActionButton1.setImageDrawable(getDrawable(R.drawable.ic_check_black_24dp));
            textView2.setText("Lek dodany do listy");
        }
        else {
            floatingActionButton1.setImageDrawable(getDrawable(R.drawable.ic_add_black_24dp));
            textView2.setText("Dodaj lek do listy");
        }

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sharedPreferences.getString("lekiDane", "").contains(textView.getText().toString())){
                    cardView.setVisibility(View.VISIBLE);
                    disableBackground();
                    cardView.animate().translationY(0).setDuration(1000);

                }else {
                    floatingActionButton1.setImageResource(R.drawable.ic_add_black_24dp);
                    sharedPreferences.edit().putString("lekiDane", sharedPreferences.getString("lekiDane", "").replace(imageUrl + "  " + textView.getText().toString() + "  " + editText.getText() + "%!%", "")).apply();
                    String[] pomoc = sharedPreferences.getString("lekiReminder", "").split("%!%");
                    for (int i=0; i<pomoc.length; i++)
                    {
                        if (pomoc[i].contains(textView.getText().toString())) {
                            pomoc[i] = pomoc[i].replace(pomoc[i], "");
                        }else
                            remindPomoc += pomoc[i] + "%!%";
                    }
                    sharedPreferences.edit().putString("lekiReminder", remindPomoc).apply();
                    cardView.animate().translationY(-2000).setDuration(1000);
                    cardView.setVisibility(View.INVISIBLE);
                    enableBackground();
                }
            }
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.animate().translationY(-2000).setDuration(1000);
                cardView.setVisibility(View.INVISIBLE);
                enableBackground();
            }
        });

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().equals("")){
                    cardView.animate().translationY(-2000).setDuration(1000);
                    cardView.setVisibility(View.INVISIBLE);
                    String dawka = editText.getText().toString();
                    if (tabletki.isChecked()){
                        if (editText.getText().toString().equals("1"))
                            dawka = editText.getText().toString() + " tabletka";
                        else if (editText.getText().toString().equals("2") || editText.getText().toString().equals("3") || editText.getText().toString().equals("4"))
                            dawka = editText.getText().toString() + " tabletki";
                        else
                            dawka = editText.getText().toString() + " tabletek";
                    }

                    if (mililitry.isChecked()){
                        if (editText.getText().toString().equals("1"))
                            dawka = editText.getText().toString() + " mililitr";
                        else if (editText.getText().toString().equals("2") || editText.getText().toString().equals("3") || editText.getText().toString().equals("4"))
                            dawka = editText.getText().toString() + " mililitry";
                        else
                            dawka = editText.getText().toString() + " mililitrów";
                    }

                    if (dawki.isChecked()){
                        if (editText.getText().toString().equals("1"))
                            dawka = editText.getText().toString() + " dawka";
                        else if (editText.getText().toString().equals("2") || editText.getText().toString().equals("3") || editText.getText().toString().equals("4"))
                            dawka = editText.getText().toString() + " dawki";
                        else
                            dawka = editText.getText().toString() + " dawek";
                    }

                sharedPreferences.edit().putString("lekiDane", sharedPreferences.getString("lekiDane", "") + imageUrl + "  " + textView.getText().toString() + "  " + dawka + "%!%" ).apply();
                if (!sharedPreferences.getString("lekiReminder", "").contains(textView.getText().toString()))
                    sharedPreferences.edit().putString("lekiReminder", sharedPreferences.getString("lekiReminder", "") + textView.getText().toString() + "brak przypomnienia" + "%!%").apply();


                floatingActionButton1.setImageDrawable(getDrawable(R.drawable.ic_check_black_24dp));
                enableBackground();
                textView2.setText("Lek dodany do listy");

                sharedPreferences.edit().putBoolean("ifCreated", true).apply();


                }else
                    Toast.makeText(Medicine.this, "Najpierw wprowadź ilość leku", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
