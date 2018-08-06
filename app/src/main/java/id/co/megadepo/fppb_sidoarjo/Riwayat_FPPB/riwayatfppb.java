package id.co.megadepo.fppb_sidoarjo.Riwayat_FPPB;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import id.co.megadepo.fppb_sidoarjo.FPPB;
import id.co.megadepo.fppb_sidoarjo.FPPB2;
import id.co.megadepo.fppb_sidoarjo.KCal;
import id.co.megadepo.fppb_sidoarjo.R;
import id.co.megadepo.fppb_sidoarjo.Util.Config;
import id.co.megadepo.fppb_sidoarjo.Util.DataTemp;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class riwayatfppb extends AppCompatActivity {

    String[] daftar;
    RecyclerView listView;
    public Cursor cursor;
    sqLite dbcenter;
    public static riwayatfppb ma;

    private AdapterData adapterData;

    String notemp, gerai;
    private LinearLayoutManager layoutManager;

//    ArrayList<fppb_model> dataModels;
    private List<fppb_model> dataModels = new ArrayList<fppb_model>();
    private static fppb_adapter adapter;

    private sqLite sq = new sqLite(riwayatfppb.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayatfppb);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setTitle("Riwayat Permintaan Kirim");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(riwayatfppb.this, FPPB2.class);
                startActivity(i);
                finish();
            }
        });
        gerai = getIntent().getStringExtra("gerai");
        notemp = getIntent().getStringExtra("notemp");
        DataTemp.setGerai(gerai);

        ma = this;
        dbcenter = new sqLite(this);
        //RefreshList();
        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getData() throws JSONException {
        /*listView = (RecyclerView) findViewById(R.id.lv);
        listView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);*/
        sq = new sqLite(riwayatfppb.this);

        ArrayList<HashMap<String,String>> row = sq.getAllData(gerai);

        JSONArray jsonArray = new JSONArray();
        for (int i=0; i<row.size(); i++){
            String notemp = row.get(i).get("notemp");
//            String nama   = row.get(i).get("nama");
//            String tgl    = row.get(i).get("tgl");

/*            fppb_model mdl = new fppb_model();
            mdl.setNotemp(notemp);
//            mdl.setNama(nama);
            mdl.setTgl(tgl);
            dataModels.add(mdl);*/

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_fpb", notemp );
//            jsonObject.put("tgl", tgl);
            jsonArray.put(jsonObject);
        }

        /*adapterData = new AdapterData(riwayatfppb.this, dataModels);
        listView.setAdapter(adapterData);
        adapterData.notifyDataSetChanged();*/

        //((ArrayAdapter) listView.getAdapter()).notifyDataSetInvalidated();

        String datatemp = jsonArray.toString();
        try {
            runpost(datatemp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void runpost(String dataTemp_) throws IOException {

        String id_fpb=dataTemp_;
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("id_fpb", id_fpb)
                .build();

        Request request = new Request.Builder()
                .url(Config.getUrl()+"/androidapps/getnamehystoryfppb.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                Log.i("Cari.class", "onFailure: " + e.toString());

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.i("MYRESPONSE", "onResponse: " + myResponse);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (response.isSuccessful()) {
                            prepareRV();
                            try {
                                JSONArray jsonArray = new JSONArray(myResponse);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject users = jsonArray.getJSONObject(i);

                                    String notemp = users.getString("id_fpb");
                                    String nama = users.getString("nama");
                                    String tgl = users.getString("TimeStamp");

                                    KCal kcal = new KCal();
                                    String tgl2 = kcal.konvertdate(tgl,
                                            "yyyy-MM-dd" , "dd MMMM yyyy");

                                    fppb_model data = new fppb_model();
                                    data.setNotemp(notemp);
                                    data.setNama(nama);
                                    data.setTgl(tgl2);

                                    dataModels.add(data);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                });
            }
        });

    }

    private void prepareRV() {
        listView = (RecyclerView) findViewById(R.id.lv);
        listView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        adapterData = new AdapterData(riwayatfppb.this, dataModels);
        listView.setAdapter(adapterData);
        adapterData.notifyDataSetChanged();
    }


//    public void RefreshList() {
//        SQLiteDatabase db = dbcenter.getReadableDatabase();
//        cursor = db.rawQuery("SELECT * FROM riwayatfppb where notemp like '"+notemp+"%' order by notemp DESC LIMIT 20 ", null);
//        daftar = new String[cursor.getCount()];
//        cursor.moveToFirst();//liat pertama
//        dataModels= new ArrayList<>();
//        for (int cc = 0; cc < cursor.getCount(); cc++) {
//        /*for (int cc = cursor.getCount(); cc >=0 ; cc--) {*/
//            cursor.moveToPosition(cc);
//            dataModels.add(new fppb_model(cursor.getString(0),cursor.getString(1)));
//
//        }
///*        listView = (ListView) findViewById(R.id.lv);
//        adapter= new fppb_adapter(dataModels,getApplicationContext());
//
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent kedetail = new Intent(riwayatfppb.this, detail_fppb.class);
//                kedetail.putExtra("notemp", dataModels.get(i).getNotemp());
//                kedetail.putExtra("tgl", dataModels.get(i).getTgl());
//                startActivity(kedetail);
//                finish();
////                Toast.makeText(riwayatfppb.this, ""+dataModels.get(i).getNotemp(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        Collections.sort(dataModels, ketersediaankursi);
//        ((ArrayAdapter) listView.getAdapter()).notifyDataSetInvalidated();*/
//    }

    public Comparator<fppb_model> ketersediaankursi = new Comparator<fppb_model>() {

        public int compare(fppb_model app1, fppb_model app2) {

            String stringName1 = cursor.getString(0);
            String stringName2 = cursor.getString(1);

            return stringName2.compareToIgnoreCase(stringName1);
        }
    };

    @Override
    public void onBackPressed() {
        Intent i = new Intent(riwayatfppb.this, FPPB2.class);
        startActivity(i);
        finish();
        //super.onBackPressed();// optional depending on your needs
    }

}




