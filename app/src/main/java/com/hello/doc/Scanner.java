package com.hello.doc;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.hello.doc.medicine.Medicine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    String code = "";
    Intent intent, intent2;
    String medicineUrl = "", imgUrl = "", medicineName = "", description = "", cleanDescription = "", secondDescription = "",
            secondCleanDescription = "";
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        intent = new Intent(this, Medicine.class);
        intent2 = new Intent(this, Loading.class);
        activity = new Loading();


    }

    @Override
    public void handleResult(Result result) {
        code = result.getText();

        startActivity(intent2);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        new getMedicineData().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();

        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    public class getMedicineData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            Document doc = null;
            try {
                doc = Jsoup.connect("https://www.google.com/search?q=" + code + "&&as_sitesearch=aptekahit.pl").get();
            } catch (Exception e) {
                Toast.makeText(Scanner.this, "wystąpił problem z załadowaniem danych lekarstwa", Toast.LENGTH_SHORT).show();
            }

            medicineUrl = doc.select("div#search").html();

            Pattern pattern = Pattern.compile("\"https://aptekahit.pl/product/[^\"]+\"");
            Matcher matcher = pattern.matcher(medicineUrl);

            if (matcher.find())
                medicineUrl = matcher.group().replace("\"", "");

            Document medicine = null;
            try {
                medicine = Jsoup.connect(medicineUrl).get();
            } catch (Exception e) {
                e.printStackTrace();

                finish();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Scanner.this, Scanner.class));
                        Toast.makeText(Scanner.this, "Wystąpił błąd podczas szczytywania kodu kreskowego", Toast.LENGTH_SHORT).show();

                    }
                });
                cancel(true);
            }


            imgUrl = "https://aptekahit.pl/" + medicine.select("li").attr("data-src");
            medicineName = medicine.select("h1.section-header.product-content-header").text();

            description = medicine.select("div.product-content-text.tinymce").outerHtml();
            cleanDescription = description.replace("<div class=\"product-content-text tinymce\">", "").replace("</div>", "").
                    replace("<p>", "").replace("<strong>", "").replace("</strong>", "").replace("<br>", "\n")
                    .replace("</p>", "\n").replace("&nbsp;", "").replace("<span style=\"color: inherit;\">", "").replace("</span>", "")
                    .replace("<li>", "").replace("</li>", "\n").replace("<ul>", "").replace("</ul>", "").replaceAll("<.*?>", "").replaceAll("</.*?>", "")
                    .replace(";", ";\n");

            secondDescription = medicine.select("div.product-content-text.tinymce").text();
            secondCleanDescription = secondDescription.replace(".", "\n").replace(",", "\n").
                    replace(":", ":\n");

            intent.putExtra("imgUrl", imgUrl);
            intent.putExtra("medicineName", medicineName);
            intent.putExtra("description", cleanDescription);
            intent.putExtra("secondDescription", secondCleanDescription);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            startActivity(intent);
        }

    }

}


