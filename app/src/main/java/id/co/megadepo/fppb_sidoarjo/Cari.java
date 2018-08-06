package id.co.megadepo.fppb_sidoarjo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.co.megadepo.fppb_sidoarjo.Riwayat_FPPB.riwayatfppb;
import id.co.megadepo.fppb_sidoarjo.Util.AppController;
import id.co.megadepo.fppb_sidoarjo.Util.Config;
import id.co.megadepo.fppb_sidoarjo.Util.ModelRiwayat;

public class Cari extends AppCompatActivity implements SearchView.OnQueryTextListener {
    EditText textHp;
    Button buttonCari;

    ProgressDialog pDialog;
    List<ModelRiwayat> listData = new ArrayList<ModelRiwayat>();
    AdapterRiwayat adapterRiwayat;
    SwipeRefreshLayout swipe;
    ListView list_view;
    String tag_json_obj = "json_obj_req";
    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";

    public static final String url_cari = Config.getUrl()+"/androidapps/caririwayat.php";
    public static final String url_data = Config.getUrl()+"/androidapps/showdelivery.php";

    private static final String TAG = Cari.class.getSimpleName();

    TextView txtnotfound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCari);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setTitle(" Cari Riwayat By Nomor Hp");

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cari.this, FPPB2.class);
                startActivity(i);
                finish();
            }
        });


        /*swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);*/
        list_view = (ListView) findViewById(R.id.listCari);

        adapterRiwayat = new AdapterRiwayat(Cari.this, listData);
        list_view.setAdapter(adapterRiwayat);

        txtnotfound = (TextView) findViewById(R.id.textnotfound);
        txtnotfound.setText("Cari Data Berdasarkan Nomor Hp dengan menekan tombol cari pada form diatas");
        txtnotfound.setVisibility(View.VISIBLE);

        SearchView searchView = (SearchView) findViewById(R.id.searchHP);

        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);

    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(Cari.this, FPPB2.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        cariData(query);
        return false;
    }



    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    private void cariData(final String hp) {
        pDialog = new ProgressDialog(Cari.this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading..");
//        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_cari, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response :", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int value = jsonObject.getInt(TAG_VALUE);

                    if (value == 1) {
                        listData.clear();
                        adapterRiwayat.notifyDataSetChanged();

                        String getobj = jsonObject.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getobj);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            ModelRiwayat data = new ModelRiwayat();
                            data.setIdFpb(obj.getString("id_fpb"));
                            data.setNama(obj.getString("nama"));
                            data.setAlamat(obj.getString("alamat"));
                            data.setKelurahan(obj.getString("kelurahan"));
                            data.setKecamatan(obj.getString("kecamatan"));
                            data.setKabupaten(obj.getString("kabupaten"));
                            data.setProvinsi(obj.getString("provinsi"));
                            data.setTelp(obj.getString("telp"));
                            data.setHp(obj.getString("hp"));
                            data.setKendaraan(obj.getString("kendaraan"));
                            data.setPortal(obj.getString("portal"));
                            data.setRt(obj.getString("rt"));
                            data.setRw(obj.getString("rw"));
                            data.setPenerima(obj.getString("penerima"));
                            data.setTanggal_kirim(obj.getString("tanggal_kirim"));

                            listData.add(data);
                            txtnotfound.setVisibility(View.GONE);
                            list_view.setVisibility(View.VISIBLE);
                        }
                    } else {
                        /*Toast.makeText(getApplicationContext(), jsonObject.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();*/
                        txtnotfound.setText(jsonObject.getString(TAG_MESSAGE));
                        txtnotfound.setVisibility(View.VISIBLE);
                        list_view.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapterRiwayat.notifyDataSetChanged();
//                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("hp", hp);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest,tag_json_obj);
    }

}
