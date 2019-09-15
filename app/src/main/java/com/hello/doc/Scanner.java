package com.hello.doc;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    String code = "";
    Intent intent;
    String medicineUrl = "", imgUrl = "", medicineName = "", description = "", cleanDescription = "", secondDescription = "",
    secondCleanDexcription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        intent = new Intent(this, Medicine.class);



    }

    @Override
    public void handleResult(Result result) {
        code = result.getText();
        Log.i("Siiiememema", code);
        new getMedicineData().execute();
        Log.i("SASASDASD", imgUrl);
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

    public class getMedicineData extends AsyncTask<Void, Void, Void>
    {


        int x = 1;

        @Override
        protected Void doInBackground(Void... voids) {

            startActivity(new Intent(Scanner.this, Loading.class));
            Log.i("Haaaallloo", code);
            Document doc = null;
            try {
                doc = Jsoup.connect("https://www.google.com/search?q=" + code + "&&as_sitesearch=aptekahit.pl").get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i("sadasd", "sadsadasd");

            Log.i("hghgg", "FFFFFFFFFFFFFFFFFFFFF");
            Log.i("HHUYGYJGJH", Integer.toString(x));

                medicineUrl = doc.select("div.r > a").attr("href");
                Log.i("dsadsadsada", "hghghghj");
                Document medicine = null;

                try {
                    medicine = Jsoup.connect(medicineUrl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                    finish();
                    Toast.makeText(Scanner.this, "wystąpił problem z załadowaniem danych lekarstwa", Toast.LENGTH_SHORT).show();
                }
                Log.i("TRTRTRTRTR", "FDSFSFDSFDF");
                imgUrl =  "https://aptekahit.pl/" + medicine.select("li").attr("data-src");
                medicineName = medicine.select("h1.section-header.product-content-header").text();
                Log.i("IMMAGGE", "gUrl");
                Log.i("Nammmeeee", medicineName);
                description = medicine.select("div.product-content-text.tinymce").outerHtml();
                cleanDescription = description.replace("<div class=\"product-content-text tinymce\">", "").replace("</div>", "").
                        replace("<p>", "").replace("<strong>", "").replace("</strong>", "").replace("<br>", "\n")
                        .replace("</p>", "\n").replace("&nbsp;", "").replace("<span style=\"color: inherit;\">", "").replace("</span>", "")
                .replace("<li>", "").replace("</li>", "\n").replace("<ul>", "").replace("</ul>", "").replaceAll("<.*?>", "").replaceAll("</.*?>", "")
                .replace(";", ";\n");

                secondDescription = medicine.select("div.product-content-text.tinymce").text();
                secondCleanDexcription = secondDescription.replace(".", "\n").replace(",", "\n").
                        replace(":", ":\n");

                intent.putExtra("imgUrl", imgUrl);
                intent.putExtra("medicineName", medicineName);
                intent.putExtra("description", cleanDescription);
                intent.putExtra("secondDescription", secondCleanDexcription);





            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            startActivity(intent);
        }

    }

}


