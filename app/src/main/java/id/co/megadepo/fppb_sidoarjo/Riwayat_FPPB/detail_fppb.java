package id.co.megadepo.fppb_sidoarjo.Riwayat_FPPB;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.co.megadepo.fppb_sidoarjo.KCal;
import id.co.megadepo.fppb_sidoarjo.R;
import id.co.megadepo.fppb_sidoarjo.Util.Config;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class detail_fppb extends AppCompatActivity {

    TextView tvnotemp,tvtgl,tvnama,tvnomemb,tvalamat,tvkel,tvkec,tvkab,tvprov,tvtelp,tvhp,tvkend,tvportal,
    tvrtrw,tvnp,tvmintakirim;
    String notemp,tgl ;
    String portalstring;
    String gerai;

    String tgl2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fppb);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setTitle("Detail Permintaan Kirim");
        setSupportActionBar(toolbar);

        notemp=getIntent().getStringExtra("notemp");
        tgl=getIntent().getStringExtra("tgl");
        gerai = getIntent().getStringExtra("gerai");

       /* KCal kcal = new KCal();
        tgl2 = kcal.konvertdate(tgl,
                "yyyyMMdd" , "EEEE, dd MMMM yyyy");*/

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(detail_fppb.this, riwayatfppb.class);
                i.putExtra("gerai", gerai);
                startActivity(i);
                finish();
            }
        });

        tvnotemp=(TextView)findViewById(R.id.tvnotemp);
        tvtgl=(TextView)findViewById(R.id.tvtgl);
        tvnomemb=(TextView)findViewById(R.id.tvnomemb);
        tvnama=(TextView)findViewById(R.id.tvnama);
        tvalamat=(TextView)findViewById(R.id.tvalamat);
        tvkel=(TextView)findViewById(R.id.tvkel);
        tvkec=(TextView)findViewById(R.id.tvkec);
        tvkab=(TextView)findViewById(R.id.tvkota);
        tvprov=(TextView)findViewById(R.id.tvprov);
        tvtelp=(TextView)findViewById(R.id.tvtelepon);
        tvhp=(TextView)findViewById(R.id.tvhandphone);
        tvkend=(TextView)findViewById(R.id.tvkend);
        tvportal=(TextView)findViewById(R.id.tvportal);
        tvrtrw=(TextView)findViewById(R.id.tvrtrw);
        tvnp=(TextView)findViewById(R.id.tvnp);
        tvmintakirim=(TextView)findViewById(R.id.tvmintakirim);


//        Toast.makeText(this, ""+notemp, Toast.LENGTH_SHORT).show();
        try {
            runpost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(detail_fppb.this, riwayatfppb.class);
        i.putExtra("gerai", gerai);
        startActivity(i);
        finish();
        //super.onBackPressed();// optional depending on your needs
    }

    void runpost() throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("id_fpb", notemp)
                .build();

        Request request = new Request.Builder()
                .url(Config.getUrl()+"/androidapps/fpb.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                Log.i("Cari.class", "onFailure: " + e.toString());

                detail_fppb.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.i("MYRESPONSE", "onResponse: " + myResponse);

                detail_fppb.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (response.isSuccessful()) {

                            try {
                                JSONArray jsonArray = new JSONArray(myResponse);
//                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject users = jsonArray.getJSONObject(0);

                                    String no_member = users.getString("no_member");
                                    String nama = users.getString("nama");
                                    String alamat = users.getString("alamat");
                                    String kelurahan = users.getString("kelurahan");
                                    String kecamatan = users.getString("kecamatan");
                                    String kabupaten = users.getString("kabupaten");
                                    String provinsi = users.getString("provinsi");
                                    String telpon = users.getString("telp");
                                    String hp = users.getString("hp");
                                    String kendaraan = users.getString("kendaraan");
                                    String portal = users.getString("portal");
                                    String rt = users.getString("rt");
                                    String rw = users.getString("rw");
                                    String np = users.getString("penerima");
                                    String mintakirim = users.getString("tanggal_kirim");
                                    if (portal.equals("0")){
                                        portalstring="Tidak Ada";
                                    }
                                    if (portal.equals("1")){
                                        portalstring="Ada";
                                    }

                                    tvnotemp.setText(": "+notemp);
                                    tvtgl.setText(": "+tgl);
                                    tvnomemb.setText(": "+no_member);
                                    tvnama.setText(": "+nama);
                                    tvalamat.setText(": "+alamat);
                                    tvkel.setText(": "+kelurahan);
                                    tvkec.setText(": "+kecamatan);
                                    tvkab.setText(": "+kabupaten);
                                    tvprov.setText(": "+provinsi);
                                    tvtelp.setText(": "+telpon);
                                    tvhp.setText(": "+hp);
                                    tvkend.setText(": "+kendaraan);
                                    tvportal.setText(": "+portalstring);
                                    tvrtrw.setText(": "+rt+" / "+rw);
                                    tvnp.setText(": "+np);

                                    KCal kcal = new KCal();
                                    tvmintakirim.setText(": "+kcal.konvertdate(mintakirim,
                                        "yyyyMMdd" , "EEEE, dd MMMM yyyy"));
//                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }


                    }
                });
            }
        });

    }
}
