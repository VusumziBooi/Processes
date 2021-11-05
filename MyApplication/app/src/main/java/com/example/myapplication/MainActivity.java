package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private String material = "" , add = " Material chosen : ";
    private TextView chosen;
    private EditText ts , ys , elong , res , acc , util , fin;
    private String TS , YS , ELONG , RES , ACC , UTIL , FIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chosen = findViewById(R.id.print);
        ts = findViewById(R.id.ts);
        ys = findViewById(R.id.ys);
        elong = findViewById(R.id.elong);
        res = findViewById(R.id.res);
        acc = findViewById(R.id.acc);
        util = findViewById(R.id.util);
        fin  = findViewById(R.id.fin);

    }

    public void doNickel(View v){
        material = "Nickel";
        chosen.setText(add + material);
    }
    public void doMagnesium(View v){
        material =  "Magnesium";
        chosen.setText(add + material);
    }
    public void doNiobium(View v){
        material =  "Niobium";
        chosen.setText(add + material);
    }
    public void doTitanium(View v){
        material = "Titanium";
        chosen.setText(add + material);
    }
    public void doTungten(View v){
        material = "Tungsten";
        chosen.setText(add + material);
    }
    public void doAluminium(View v){
        material = "Aluminium";
        chosen.setText(add + material);
    }
    public void doStainless_steel(View v){
        material = "Stainless Steel";
        chosen.setText(add + material);
    }
    public void doCobalt_chrome(View v){
        material =  "Cobalt-Chrome";
        chosen.setText(add + material);
    }
    public void doRequest(View v){
        TS = ts.getText().toString();
        YS = ys.getText().toString();
        ELONG = elong.getText().toString();
        RES = res.getText().toString();
        ACC = acc.getText().toString();
        UTIL = util.getText().toString();
        FIN = fin.getText().toString();
        if(material.isEmpty() || ts.getText().toString().isEmpty() || ys.getText().toString().isEmpty() || elong.getText().toString().isEmpty() ||
            res.getText().toString().isEmpty() || acc.getText().toString().isEmpty() || util.getText().toString().isEmpty() || fin.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this , "Make sure a material is selected and  all fields are not empty" , Toast.LENGTH_LONG).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("metal", material)
                .add("ts", TS)
                .add("ys", YS)
                .add("elong", ELONG)
                .add("res", RES)
                .add("util", UTIL)
                .add("acc", ACC)
                .add("fin", FIN)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2109688/processes.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String responseData = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            doMost(responseData);
                        }
                    });
                }
            }
        });
    }

    private void doMost(String json) {
        ArrayList<Processors>mylist = new ArrayList<>();
        try {
            JSONArray ja = new JSONArray(json);
            for(int i = 0 ; i < ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                String technique = jo.getString("TECHNIQUE");
                String metal = jo.getString("METAL");
                int tmin = Integer.parseInt(jo.getString("TENSILE_MIN"));
                int tmax = Integer.parseInt(jo.getString("TENSILE_MAX"));
                int emin = Integer.parseInt(jo.getString("ELONGATION_MIN"));
                int emax = Integer.parseInt(jo.getString("ELONGATION_MAX"));
                int ymin = Integer.parseInt(jo.getString("YIELD_MIN"));
                int ymax = Integer.parseInt(jo.getString("YIELD_MAX"));
                int rmin = Integer.parseInt(jo.getString("RESOLUTION_MIN"));
                int rmax = Integer.parseInt(jo.getString("RESOLUTION_MAX"));
                int acc = Integer.parseInt(jo.getString("ACCURACY"));
                int util = Integer.parseInt(jo.getString("UTILIZATION"));
                int fin = Integer.parseInt(jo.getString("FINISH"));
                mylist.add(new Processors(technique , metal , tmin ,tmax , emin , emax, ymin , ymax , rmin , rmax , acc , util , fin));
            }

            for(int i = 0 ; i < mylist.size() ; i++){
                Processors curr = mylist.get(i);
                curr.checkThem(Integer.parseInt(TS), Integer.parseInt(ELONG), Integer.parseInt(YS), Integer.parseInt(RES), Integer.parseInt(ACC), Integer.parseInt(UTIL), Integer.parseInt(FIN));
            }

            String process = Recommended(mylist);
            Toast.makeText(MainActivity.this , "Recommended process is " + process, Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String Recommended(ArrayList<Processors>mylist){
        Processors rec = mylist.get(0);
        for(Processors curr : mylist){
            if(curr.counter > rec.counter){
                rec = curr;
            }
        }
        return rec.technique;
    }



}