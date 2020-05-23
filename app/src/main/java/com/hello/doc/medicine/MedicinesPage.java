package com.hello.doc.medicine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import com.hello.doc.Loading;
import com.hello.doc.R;
import com.hello.doc.RecyclerItemClickListener;
import com.hello.doc.RecyclerItemTouchHelperListener;
import com.hello.doc.RecyclerViewTouchHelper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class MedicinesPage extends Fragment implements RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private List<ListMedicines> list , backupList;
    private MedicinesAdapter medicinesAdapter;
    private CoordinatorLayout coordinatorLayout;
    private String medicineName = "", reminPomocStr = "", reminPomocStrCzyste = "", reminPomocStrSwipe = "", code = "";
    private SharedPreferences sharedPreferences;
    private String[] pomoc, wewPomoc, reminPomoc, reminPomocSwipe;
    private ListMedicines listMedicines;
    private SearchView searchView;
    private TextView mainText;
    private Boolean connected;
    private ImageView ludzik;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        return inflater.inflate(R.layout.medicines, container, false);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.recycler_view);
        coordinatorLayout = getView().findViewById(R.id.coordinator);
        list = new ArrayList<>();
        sharedPreferences = getContext().getSharedPreferences("com.hello.doc", Context.MODE_PRIVATE);
        searchView = getView().findViewById(R.id.searchView);
        searchView.setQueryHint("szukaj...");
        mainText = getView().findViewById(R.id.textView2);
        ludzik = getView().findViewById(R.id.ludzik);



        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            connected = true;
        }
        else{
            connected = false;

            new AlertDialog.Builder(getContext()).setCancelable(false)
                    .setTitle("BRAK POŁĄCZENIA Z INTERNETEM")
                    .setMessage("Sprawdź swoje połączenie z internetem")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }



        backupList = new ArrayList<>();


        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    searchView.setIconified(false);

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<ListMedicines> newList = new ArrayList<>();

                for (int i=0; i<list.size(); i++){
                    if (list.get(i).getMedicineName().toLowerCase().contains(newText.toLowerCase()))
                        newList.add(list.get(i));
                }

                medicinesAdapter = new MedicinesAdapter(getActivity(), newList);
                recyclerView.setAdapter(medicinesAdapter);
                medicinesAdapter.notifyDataSetChanged();

                return false;
            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                list = backupList;
                medicinesAdapter.notifyDataSetChanged();

                return false;
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                code = list.get(position).getMedicineName();
                medicineName = code;
                new getMedicinieOnClick().execute();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        pomoc = sharedPreferences.getString("medicinesData", "").split("%!%");
        reminPomoc = sharedPreferences.getString("lekiReminder", "").split("%!%");


        if (!sharedPreferences.getString("medicinesData", "").equals("")) {
            for (int i = 0; i < pomoc.length; i++) {
                wewPomoc = pomoc[i].split("  ");
                listMedicines = new ListMedicines();
                listMedicines.setThumbnail(wewPomoc[0]);
                listMedicines.setMedicineName(wewPomoc[1]);
                listMedicines.setPills(wewPomoc[2]);

                for (int j=0; j<reminPomoc.length; j++){
                    if (reminPomoc[j].contains(wewPomoc[1])){
                        reminPomocStr += "\n" + reminPomoc[j];
                        reminPomocStrCzyste += reminPomocStr.replaceAll(wewPomoc[1], "") + " ";
                        reminPomocStr = "";
                    }
                }

                if (reminPomocStrCzyste.equals(""))
                    listMedicines.setReminder("\n" + "brak przypomnień;");
                else
                    listMedicines.setReminder(reminPomocStrCzyste);
                reminPomocStrCzyste = "";

                list.add(listMedicines);
            }

            for (int i=0; i<list.size(); i++){
                backupList.add(list.get(i));
            }

        }



        medicinesAdapter = new MedicinesAdapter(getContext(), list);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(medicinesAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());




        ItemTouchHelper.SimpleCallback item = new RecyclerViewTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(item).attachToRecyclerView(recyclerView);

        String[] medicinesData = sharedPreferences.getString("medicinesData", "").split(" ");

        for (int i=0; i<medicinesData.length; i++){
            if (medicinesData[i].contains("brak")){

                new AlertDialog.Builder(getContext())
                        .setTitle("Puste lekarstwo")
                        .setMessage("Zawartość jednego z Twoich lekarstw jest pusta. Czy wyświetlić apteki w pobliżu, w których zakupisz brakujący lek?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Uri gmmIntentUri = Uri.parse("geo:0,0?q=apteka");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);

                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_local_hospital_black_24dp)
                        .show();

            }
        }

        if (!sharedPreferences.getString("medicinesData", "").equals("")){
            recyclerView.setVisibility(View.VISIBLE);
            mainText.setVisibility(View.INVISIBLE);
            ludzik.setVisibility(View.INVISIBLE);
        }
        else{
            recyclerView.setVisibility(View.INVISIBLE);
            mainText.setVisibility(View.VISIBLE);
            ludzik.setVisibility(View.VISIBLE);
        }
        mainText.setZ(100);
        ludzik.setZ(100);
        ludzik.setAlpha(125);


    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof  MedicinesAdapter.MyViewHolder){
            String name = list.get(viewHolder.getAdapterPosition()).getMedicineName();
            final ListMedicines deletedItem = list.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            medicinesAdapter.removeItem(deleteIndex);
            sharedPreferences.edit().putString("medicinesDataBackup", sharedPreferences.getString("medicinesData",  "")).apply();
            sharedPreferences.edit().putString("medicinesData", sharedPreferences.getString("medicinesData", "").replace(deletedItem.getThumbnail() + "  " + deletedItem.getMedicineName() + "  " + deletedItem.getPills() + "%!%", "")).apply();
            reminPomocSwipe = deletedItem.getReminder().split("; ");

            for (int i=0; i<reminPomocSwipe.length; i++)
            {
                reminPomocStrSwipe += deletedItem.getMedicineName() + reminPomocSwipe[i] + "%!%";
            }

            sharedPreferences.edit().putString("lekiReminder", sharedPreferences.getString("lekiReminder", "").replace(reminPomocStrSwipe, "")).apply();

            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Usunięto lek: " + name, Snackbar.LENGTH_LONG);
            snackbar.setAction("COFNIJ", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    medicinesAdapter.restoreItem(deletedItem, deleteIndex);
                    sharedPreferences.edit().putString("medicinesData", sharedPreferences.getString("medicinesDataBackup", "")).apply();
                    sharedPreferences.edit().putString("lekiReminder", sharedPreferences.getString("lekiReminder", "")).apply();

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }

    }

    public class getMedicinieOnClick extends AsyncTask<Void, Void, Void>
    {
        Document doc;
        String medicineUrl = "", imgUrl = "", medicineName2 = "", description = "", cleanDescription = "";
        Intent intent;

        @Override
        protected Void doInBackground(Void... voids) {
            intent = new Intent(getContext(), Medicine.class);
            startActivity(new Intent(getContext(), Loading.class));

            try {
                doc = Jsoup.connect("https://www.google.com/search?q=" + code + "&&as_sitesearch=aptekahit.pl").get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            medicineUrl = doc.select("div.r > a").attr("href");

            Document medicine = null;

            try {
                medicine = Jsoup.connect(medicineUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            imgUrl =  "https://aptekahit.pl/" + medicine.select("li").attr("data-src");
            medicineName2 = medicine.select("h1.section-header.product-content-header").text();

            description = medicine.select("div.product-content-text.tinymce").outerHtml();
            cleanDescription = description.replace("<div class=\"product-content-text tinymce\">", "").replace("</div>", "").
                    replace("<p>", "").replace("<strong>", "").replace("</strong>", "").replace("<br>", "\n")
                    .replace("</p>", "\n").replace("&nbsp;", "").replace("<span style=\"color: inherit;\">", "").replace("</span>", "")
                    .replace("<li>", "").replace("</li>", "\n").replace("<ul>", "").replace("</ul>", "").replaceAll("<.*?>", "").replaceAll("</.*?>", "")
                    .replace(";", ";\n");

            intent.putExtra("imgUrl", imgUrl);
            intent.putExtra("medicineName", medicineName);
            intent.putExtra("description", cleanDescription);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean("ifCreated", true)){
            sharedPreferences.edit().putBoolean("ifCreated", false).apply();
            this.onViewCreated(getView(), null);
        }
    }
}
