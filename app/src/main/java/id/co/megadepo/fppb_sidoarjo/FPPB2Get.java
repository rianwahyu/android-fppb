package id.co.megadepo.fppb_sidoarjo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.co.megadepo.fppb_sidoarjo.Riwayat_FPPB.riwayatfppb;
import id.co.megadepo.fppb_sidoarjo.Riwayat_FPPB.sqLite;
import id.co.megadepo.fppb_sidoarjo.Util.Config;
import id.co.megadepo.fppb_sidoarjo.database.DatabaseHelper;
import id.co.megadepo.fppb_sidoarjo.database.ModelKabupaten;
import id.co.megadepo.fppb_sidoarjo.database.ModelKecamatan;
import id.co.megadepo.fppb_sidoarjo.database.ModelKelurahan;
import id.co.megadepo.fppb_sidoarjo.database.ModelProvinsi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class FPPB2Get extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, AdapterView.OnItemSelectedListener {

    AutoCompleteTextView act_kelurahan, act_kecamatan, act_kota, act_prov;
    TextView tvfppblokasi,tvnm;
    EditText etnomemb, etnama, etalamat, ettelepon, ethandphone, etlatitude,
            etlongitude, etlokasi,etpenerima,ettgldikirim,etjamdikirim,
    etrt,etrw ;
    Checkable cbtruck, cbpickup,cbtronton, cbada, cbadab ,cbtdkada,cbbandobel;
    String kendaraan1, kendaraan2, portal, gerai;
    private float latitude, longitude;

    float latsidoarjo = (float) -7.3870732;
    float lonsidoarjo = (float) 112.7269496;

    float latmalang = (float) -7.9124853;
    float lonmalang = (float) 112.6545007;

    float latbali = (float) -8.674295;
    float lonbali = (float) 115.1836986;

    String depo = "DEPO BANGUNAN", GeraiMalang="202", GeraiSidoarjo="201",GeraiBali="301",Lokasi="";

    List<String> lsid = new ArrayList<String>();
    List<String> lskab = new ArrayList<String>();
    List<String> lskec = new ArrayList<String>();
    List<String> lskel = new ArrayList<String>();
    String part1;
    String part2;
    String part3;
    String part4, part5, part6, part7, part8;
    String urlkab = Config.getUrl()+"/androidapps/kabupaten.php";
    String urlkec = Config.getUrl()+"/androidapps/kecamatan.php";
    String urlkel = Config.getUrl()+"/androidapps/kelurahan.php";

    String urlkab2 = Config.getUrl()+"/androidapps/kabupaten2.php";
    String urlkec2 = Config.getUrl()+"/androidapps/kecamatan2.php";
    String urlkel2 = Config.getUrl()+"/androidapps/kelurahan2.php";
    int actpos;
    LinearLayout llprov, llkot, llkec, llkel, lllokasi, llplace,llpro,llko,llkecamatan,llkelurahan;
    MapView map;
    ImageView transparent_image;
    GoogleMap mMap;
    private MarkerOptions markerOptions, markerOptions1, markerOptions2;
    private ScrollView mScrollView;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 11;
    Button btcarilok, okkirim;
    private String urlkirim = Config.getUrl()+"/androidapps/delivery.php";
    Boolean notelp, checked, cbkendaraan, cbportal;
    protected Cursor cursor;
    sqLite sqlite;
    private AlertDialog dialog;
    private GoogleApiClient mGoogleApiClient;
    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    private boolean mLocationPermissionGranted;
    String urlprofil = Config.getUrl()+"/androidapps/profile.php";
    String nomemb;
    Spinner sp_prov;
    MaterialBetterSpinner spinner1,spinner2,spinner3,spinner4;
//    ArrayList<ModelID> listprov = new ArrayList<>();
//    ArrayList<ModelID> listkota = new ArrayList<>();
//    ArrayList<ModelID> listkecamatan = new ArrayList<>();
//    ArrayList<ModelID> listkelurahan = new ArrayList<>();
//    BetterSpinner /*spinner1,*/ ;
    String provinselect,kotaselect,kecselect,kelselect ;
    int detectspinner = 0;
    KCal kc = new KCal();
    private String formattedDate;

    LinearLayout mainLayout;
    DatabaseHelper databaseHelper;

    private String namax,alamatx,kelx,kecx,kabx,provx,telpx,hpx,portalx,rtx,rwx,penerimax,tglx;

    String idProv2,idKab2,idKec2,idKel2;

    Button reset;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fppb);

        tvnm = (TextView) findViewById(R.id.tvnm);
        tvnm.setVisibility(View.GONE);
        act_kelurahan = (AutoCompleteTextView) findViewById(R.id.act_kelurahan);
        act_kecamatan = (AutoCompleteTextView) findViewById(R.id.act_kecamatan);
        act_kota = (AutoCompleteTextView) findViewById(R.id.act_kota);
        act_prov = (AutoCompleteTextView) findViewById(R.id.act_prov);
        tvfppblokasi = (TextView) findViewById(R.id.tvfppblokasi);
        etnomemb = (EditText) findViewById(R.id.etnomemb);
        etnomemb.setVisibility(View.GONE);
        etnama = (EditText) findViewById(R.id.etnama);
        etalamat = (EditText) findViewById(R.id.etalamat);
        ettelepon = (EditText) findViewById(R.id.ettelepon);
        ethandphone = (EditText) findViewById(R.id.ethandphone);
        cbtruck = (CheckBox) findViewById(R.id.cbtruck);
        cbpickup = (CheckBox) findViewById(R.id.cbpickup);
        cbtronton = (CheckBox) findViewById(R.id.cbtronton);
        cbbandobel = (CheckBox) findViewById(R.id.cbbandobel);
        cbada = (CheckBox) findViewById(R.id.cbada);
        cbadab = (CheckBox) findViewById(R.id.cbadab);
        cbtdkada = (CheckBox) findViewById(R.id.cbtdkada);
        llprov = (LinearLayout) findViewById(R.id.llprov);
        llkot = (LinearLayout) findViewById(R.id.llkot);
        llkec = (LinearLayout) findViewById(R.id.llkec);
        llkel = (LinearLayout) findViewById(R.id.llkel);
        lllokasi = (LinearLayout) findViewById(R.id.lllokasi);
        llplace = (LinearLayout) findViewById(R.id.llplace);
        llpro = (LinearLayout) findViewById(R.id.llpro);
        llko = (LinearLayout) findViewById(R.id.llko);
        llkecamatan = (LinearLayout) findViewById(R.id.llkecamatan);
        llkelurahan = (LinearLayout) findViewById(R.id.llkelurahan);
        transparent_image = (ImageView) findViewById(R.id.transparent_image);
        etlatitude = (EditText) findViewById(R.id.etlatitude);
        etlongitude = (EditText) findViewById(R.id.etlongitude);
        etlokasi = (EditText) findViewById(R.id.etlokasi);
        btcarilok = (Button) findViewById(R.id.btcarilok);
        okkirim = (Button) findViewById(R.id.okkirim);
        sp_prov = (Spinner) findViewById(R.id.sp_prov);
        sp_prov.setOnItemSelectedListener(this);
        spinner1 = (MaterialBetterSpinner) findViewById(R.id.spinner1);
        spinner2 = (MaterialBetterSpinner) findViewById(R.id.spinner2);
        spinner3 = (MaterialBetterSpinner) findViewById(R.id.spinner3);
        spinner4 = (MaterialBetterSpinner) findViewById(R.id.spin4);
        etpenerima =(EditText)findViewById(R.id.etpenerima);
        ettgldikirim =(EditText)findViewById(R.id.ettgldikirim);
//        etjamdikirim =(EditText)findViewById(R.id.etjamdikirim);
        etrt =(EditText)findViewById(R.id.etrt);
        etrw =(EditText)findViewById(R.id.etrW);

        namax = getIntent().getStringExtra("namax");
        alamatx = getIntent().getStringExtra("alamatx");
        kelx = getIntent().getStringExtra("kelx");
        kecx = getIntent().getStringExtra("kecx");
        kabx = getIntent().getStringExtra("kabx");
        provx = getIntent().getStringExtra("provx");
        telpx = getIntent().getStringExtra("telpx");
        hpx = getIntent().getStringExtra("hpx");
        portalx = getIntent().getStringExtra("portalx");
        rtx = getIntent().getStringExtra("rtx");
        rwx = getIntent().getStringExtra("rwx");
        penerimax = getIntent().getStringExtra("penerimax");

        if (namax.isEmpty()){
            llko.setVisibility(View.GONE);
            llkecamatan.setVisibility(View.GONE);
            llkelurahan.setVisibility(View.GONE);
        }else {
            llko.setVisibility(View.VISIBLE);
            llkecamatan.setVisibility(View.VISIBLE);
            llkelurahan.setVisibility(View.VISIBLE);

            etnama.setText(namax.toUpperCase());
            etalamat.setText(alamatx.toUpperCase());
            ettelepon.setText(telpx);
            ethandphone.setText(hpx);

            spinner1.setText(provx);
            spinner2.setText(kabx);
            spinner3.setText(kecx);
            spinner4.setText(kelx);

            etrt.setText(rtx);
            etrw.setText(rwx);

            etpenerima.setText(penerimax.toUpperCase());
        }

//        llko.setVisibility(View.GONE);
//        llkecamatan.setVisibility(View.GONE);
//        llkelurahan.setVisibility(View.GONE);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);

/*        toolbar.setTitle(Lokasi);*/
        setSupportActionBar(toolbar);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_menu);
        toolbar.setOverflowIcon(drawable);
        toolbar.setNavigationIcon(R.drawable.depotruck);

        mainLayout = (LinearLayout)findViewById(R.id.linearlayout);


        Log.wtf("WTF", "onCreate: " + portal);

        mGeoDataClient = Places.getGeoDataClient(this, null);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        formattedDate = df.format(c.getTime());

        ettgldikirim.setText(formattedDate);

        ettgldikirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hari = c.get(Calendar.DAY_OF_WEEK);
                c.add(Calendar.DAY_OF_MONTH,0);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FPPB2Get.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String dateset = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

//                                ettgldikirim.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                ettgldikirim.setText(kc.konvertdate(dateset,"dd-MM-yyyy","EEEE, dd MMMM yyyy"));
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        databaseHelper = DatabaseHelper.getInstance(this);

        loadProvinsi();

        etlatitude.setKeyListener(null);
        etlongitude.setKeyListener(null);
        etlokasi.setKeyListener(null);

        mScrollView = (ScrollView) findViewById(R.id.mScrollView);

        Log.wtf("CHECKEDCBADA", "onCheckboxClicked: " + kendaraan1);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        okkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (provId == 0 || KabId == 0 || KecId == 0 || KelId == 0 ){
                    idProv2 = databaseHelper.getIdProv(provx);
                    idKab2 = databaseHelper.getIdKabupaten(kabx);
                    idKec2 = databaseHelper.getIdKecamatan(kecx);
                    idKel2 = databaseHelper.getIdKelurahan(kelx);

                    fixkirim2();
                }else {
                    fixkirim();
                }


                InputMethodManager enm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                enm.hideSoftInputFromWindow(etnama.getWindowToken(), 0);

                InputMethodManager eal = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                eal.hideSoftInputFromWindow(etalamat.getWindowToken(), 0);

                InputMethodManager ehp = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                ehp.hideSoftInputFromWindow(ethandphone.getWindowToken(), 0);

                InputMethodManager ert = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                ert.hideSoftInputFromWindow(etrt.getWindowToken(), 0);

                InputMethodManager erw = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                erw.hideSoftInputFromWindow(etrw.getWindowToken(), 0);

                InputMethodManager epen = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                epen.hideSoftInputFromWindow(etpenerima.getWindowToken(), 0);

                InputMethodManager etlp = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                etlp.hideSoftInputFromWindow(ettelepon.getWindowToken(), 0);

            }
        });

        llkel.setVisibility(View.GONE);
        llkec.setVisibility(View.GONE);
        llkot.setVisibility(View.GONE);

        etnomemb.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                etnama.setText("");
                    etnama.setFocusable(true);
                    etnama.setFocusableInTouchMode(true);
                    etnama.setClickable(true);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() >= 8)
                    tampprofil();

            }
        });

/*        pilihprovinsi();
        try {
//            postprovinsi();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        if (jikainternettersedia(this) == false) {

        }

/*
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        */


        //below make scrollview modar
        ImageView transparentImageView = (ImageView) findViewById(R.id.transparent_image);

        transparentImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        mScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;
                    default:
                        return true;
                }
            }
        });
        getLocationPermission();
        btcarilok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPlace();
            }
        });

        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etnama.setText("");
                etnomemb.setText("");
                etalamat.setText("");
//                    act_kelurahan.setText("");
//                    act_kecamatan.setText("");
//                    act_kota.setText("");
//                    act_prov.setText("");
                spinner1.setText("");
                spinner2.setText("");
                spinner3.setText("");
                spinner4.setText("");
                ettelepon.setText("");
                ethandphone.setText("");
                etlokasi.setText("");
                etlatitude.setText("");
                etlongitude.setText("");
                etpenerima.setText("");
                //disable llspinner
                llko.setVisibility(View.GONE);
                llkecamatan.setVisibility(View.GONE);
                llkelurahan.setVisibility(View.GONE);

                mMap.clear();
                cbada.setChecked(false);
                cbadab.setChecked(false);
                cbtdkada.setChecked(false);
                cbpickup.setChecked(false);
                cbtruck.setChecked(false);
                cbtronton.setChecked(false);
                cbbandobel.setChecked(false);
                etnomemb.requestFocus();
                kendaraan1 = null;
//                    kendaraan2 = null;
                portal = null;
                etrt.setText("");
                etrw.setText("");
                ettgldikirim.setText(formattedDate);
            }
        });


    }

    int provId;

    private void loadProvinsi() {

        ArrayList spinnerProvList = databaseHelper.getProvinsi();

        ModelProvinsi modelProvinsi = new ModelProvinsi();
        modelProvinsi.getName();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(FPPB2Get.this,android.R.layout.simple_spinner_item,spinnerProvList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(spinnerAdapter);
        spinner1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String label = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "You selected: " + label,
                        Toast.LENGTH_LONG).show();

                ModelProvinsi item = (ModelProvinsi) adapterView.getItemAtPosition(i);
                String databaseValue = item.getName();
                provId = item.getId();

                loadKabupaten(provId);
            }
        });

    }

    int KabId;
    private void loadKabupaten(int provId) {

        int ProvID = provId;
        llko.setVisibility(View.VISIBLE);

        ArrayList spinnerKabList = databaseHelper.getKabupaten(ProvID);

        ModelProvinsi modelProvinsi = new ModelProvinsi();
        modelProvinsi.getName();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(FPPB2Get.this,android.R.layout.simple_spinner_item,spinnerKabList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(spinnerAdapter);
        spinner2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String label = adapterView.getItemAtPosition(i).toString();

                ModelKabupaten item = (ModelKabupaten) adapterView.getItemAtPosition(i);
                String databaseValue = item.getName();
                KabId = item.getId();

                loadKecamatan(KabId);
            }
        });
    }

    int KecId;
    private void loadKecamatan(int kabId) {
        int KabID = kabId;
        llkecamatan.setVisibility(View.VISIBLE);

        ArrayList spinnerKecList = databaseHelper.getKecamatan(KabID);

        ModelKecamatan modelProvinsi = new ModelKecamatan();
        modelProvinsi.getName();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(FPPB2Get.this,android.R.layout.simple_spinner_item,spinnerKecList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(spinnerAdapter);
        spinner3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String label = adapterView.getItemAtPosition(i).toString();

                ModelKecamatan item = (ModelKecamatan) adapterView.getItemAtPosition(i);
                String databaseValue = item.getName();
                KecId = item.getId();

                loadKelurahan(KecId);
            }
        });

    }

    int KelId;
    private void loadKelurahan(int kecId) {
        int KecID = kecId;
        llkelurahan.setVisibility(View.VISIBLE);

        ArrayList spinnerKelList = databaseHelper.getKelurahan(KecID);

        ModelKelurahan modelProvinsi = new ModelKelurahan();
        modelProvinsi.getName();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(FPPB2Get.this,android.R.layout.simple_spinner_item,spinnerKelList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(spinnerAdapter);
        spinner4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String label = adapterView.getItemAtPosition(i).toString();

                ModelKelurahan item = (ModelKelurahan) adapterView.getItemAtPosition(i);
                String databaseValue = item.getName();
                KelId = item.getId();

                /*loadKelurahan(KecId);*/
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
    }


    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        mGoogleApiClient.connect();
    }


    private boolean jikainternettersedia(Context context) {
        ConnectivityManager aturkoneksi = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return aturkoneksi.getActiveNetworkInfo() != null && aturkoneksi.getActiveNetworkInfo().isConnected();
    }

    private void fixkirim() {

        String ttelp = ettelepon.getText().toString().trim();
//        Log.i("TTELP", "fixkirim:"+ttelp);
        String thandp = ethandphone.getText().toString().trim();

//        etnama.onEditorAction(EditorInfo.IME_ACTION_DONE);
        if (ttelp.equals("") && thandp.equals("")) {
            notelp = false;
        }
        if (!ttelp.equals("") || !thandp.equals("")) {
            notelp = true;
        }
        Log.i("NOTELP", "fixkirim: " + notelp);


        if (etnama.getText().toString().trim().equals("") ||
                        etalamat.getText().toString().trim().equals("") ||
                        spinner4.getText().toString().trim().equals("") ||
                        spinner3.getText().toString().trim().equals("") ||
                        spinner2.getText().toString().trim().equals("") ||
                        spinner1.getText().toString().trim().equals("") ||
                        notelp == false || kendaraan1 == null || portal == null
//                        portal.isEmpty()

                ) {
            tampilsnack("Mohon Lengkapi Data Anda");


        } else {
            runkirim();
        }

    }

    private void runkirim() {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("no_member", etnomemb.getText().toString())
                .add("nama", etnama.getText().toString())
                .add("alamat", etalamat.getText().toString())
                .add("rt", etrt.getText().toString().trim())
                .add("rw",etrw.getText().toString().trim())
                .add("kelurahan", String.valueOf(KelId))
                .add("kecamatan", String.valueOf(KecId))
                .add("kabupaten", String.valueOf(KabId))
                .add("provinsi", String.valueOf(provId))
                .add("telp", ettelepon.getText().toString())
                .add("hp", ethandphone.getText().toString())
                .add("kendaraan", kendaraan1)
                .add("portal", portal)
                .add("warehouse", gerai)
                .add("latit", etlatitude.getText().toString())
                .add("longit", etlongitude.getText().toString())
                .add("penerima", etpenerima.getText().toString())
                .add("tanggal_kirim", kc.konvertdate(ettgldikirim.getText().toString().trim(),
                            "EEEE, dd MMMM yyyy", "yyyyMMdd"))
                .build();

        Request request = new Request.Builder()
                .url(urlkirim)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                Log.i("Cari.class", "onFailure: " + e.toString());

                FPPB2Get.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.i("MYRESPONSE", "onResponse: " + myResponse);

                FPPB2Get.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String resp = myResponse;
                        Log.i("resp", "run: " + resp + " ");

                        if (response.isSuccessful()) {
        tampilsnack("" + resp);
    }
}
                });
                        }
                        });
                        }

    private void tampprofil() {
        Log.i("ceket", "tampprofil: " + etnomemb.getText().toString());

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
        .add("nomember", etnomemb.getText().toString().trim())
        .build();

        Request request = new Request.Builder()
        .url(urlprofil)
        .post(formBody)
        .build();

        client.newCall(request).enqueue(new Callback() {
@Override
public void onFailure(Call call, IOException e) {
        call.cancel();
        Log.i("Cari.class", "onFailure: " + e.toString());

        FPPB2Get.this.runOnUiThread(new Runnable() {
@Override
public void run() {
        }
        });
        }

@Override
public void onResponse(Call call, final Response response) throws IOException {
final String myResponse = response.body().string();
        Log.i("MYRESPONSE", "onResponse: " + myResponse);
        nomemb = myResponse;
        Log.i("NOMEMB", "onResponse: " + nomemb);
        FPPB2Get.this.runOnUiThread(new Runnable() {
@Override
public void run() {

        String resp = myResponse;
        Log.i("resp", "run: " + resp + " ");

        if (response.isSuccessful()) {
        JSONArray jr = null;
        try {
        jr = new JSONArray(myResponse);
        JSONObject jo = new JSONObject(jr.getString(0));
        etnama.setText(jo.getString("nama"));
        etnama.setFocusable(false);
        etnama.setFocusableInTouchMode(false);
        etnama.setClickable(false);
        } catch (JSONException e) {
        e.printStackTrace();
        }

        }
        }
        });
        }
        });
        }

    public String tampilsnack(final String s) {

        Snackbar mySnackbar = Snackbar
                .make(findViewById(R.id.colayout), s, Snackbar.LENGTH_INDEFINITE);
        mySnackbar.setActionTextColor(Color.RED);
//        mySnackbar.setDuration(10000);
        View sbView = mySnackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        textView.setMaxLines(4);
        mySnackbar.show();

        mySnackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.equals("Mohon Lengkapi Data Anda")) {

                    if (ethandphone.getText().toString().equals("")) {
                        ethandphone.requestFocus();
                        ethandphone.setHint("Tidak boleh kosong");
                    }
//                    if (act_prov.getText().toString().equals("")) {
//                        act_prov.requestFocus();
//                        act_prov.setHint("Tidak boleh kosong");
//                    }
//                    if (act_kota.getText().toString().equals("")) {
//                        act_kota.requestFocus();
//                        act_kota.setHint("Tidak boleh kosong");
//                    }
//                    if (act_kecamatan.getText().toString().equals("")) {
//                        act_kecamatan.requestFocus();
//                        act_kecamatan.setHint("Tidak boleh kosong");
//                    }
//                    if (act_kelurahan.getText().toString().equals("")) {
//                        act_kelurahan.requestFocus();
//                        act_kelurahan.setHint("Tidak boleh kosong");
//                    }
                    if (etalamat.getText().toString().equals("")) {
                        etalamat.requestFocus();
                        etalamat.setHint("Tidak boleh kosong");
                    }
                    if (etnama.getText().toString().equals("")) {
                        etnama.requestFocus();
                        etnama.setHint("Tidak boleh kosong");
                    }


                }
                if (s.contains("Mohon maaf, silahkan periksa kembali No member anda")) {
                    etnomemb.requestFocus();
                }
                if (s.contains("Permintaan anda akan segera diproses dengan FPB")) {
                    final String Str = s.substring(48, 57);
//                    Toast.makeText(FPPB.this, ""+Str, Toast.LENGTH_SHORT).show();
                    etnama.setText("");
                    etnomemb.setText("");
                    etalamat.setText("");
//                    act_kelurahan.setText("");
//                    act_kecamatan.setText("");
//                    act_kota.setText("");
//                    act_prov.setText("");
                    /*spinner1.setText("");*/
                    spinner2.setText("");
                    spinner3.setText("");
                    spinner4.setText("");
                    ettelepon.setText("");
                    ethandphone.setText("");
                    etlokasi.setText("");
                    etlatitude.setText("");
                    etlongitude.setText("");
                    etpenerima.setText("");
                    //disable llspinner
                    llko.setVisibility(View.GONE);
                    llkecamatan.setVisibility(View.GONE);
                    llkelurahan.setVisibility(View.GONE);
                    mMap.clear();
                    cbada.setChecked(false);
                    cbadab.setChecked(false);
                    cbtdkada.setChecked(false);
                    cbpickup.setChecked(false);
                    cbtruck.setChecked(false);
                    cbtronton.setChecked(false);
                    cbbandobel.setChecked(false);
                    etnomemb.requestFocus();
                    kendaraan1 = null;
//                    kendaraan2 = null;
                    portal = null;
                    etrt.setText("");
                    etrw.setText("");
                    ettgldikirim.setText(formattedDate);

                    final LinearLayout llnotemp = (LinearLayout) findViewById(R.id.llnotemp);
                    TextView tvnotemp = (TextView) findViewById(R.id.tvnotemp);
                    Button btnotemp = (Button) findViewById(R.id.btnotemp);

                    llnotemp.setVisibility(View.VISIBLE);
                    tvnotemp.setText("Nomor temp : " + Str);

                    btnotemp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
                            String tgl = df.format(Calendar.getInstance().getTime());
                            llnotemp.setVisibility(View.GONE);
                            sqlite = new sqLite(FPPB2Get.this);
                            SQLiteDatabase db = sqlite.getWritableDatabase();
                            db.execSQL("insert into riwayatfppb(notemp, tgl) values('" +
                                    Str + "', '" + tgl + "')");
//                            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
//                            riwayatfppb.ma.RefreshList();
//                            finish();
                        }
                    });


                }
            }
        });

        return s;
    }

    private void findPlace() {

        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e1) {
            e1.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                EditText et = (EditText) findViewById(R.id.et);
                et.setKeyListener(null);
                et.requestFocus();
                mMap.clear();
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                LatLng queriedLocation = place.getLatLng();
                Log.v("Latitude is", "" + queriedLocation.latitude);
                Log.v("Longitude is", "" + queriedLocation.longitude);

                //nampilkan marker
                markerOptions = new MarkerOptions();
                markerOptions.position(queriedLocation);
                markerOptions.title((String) place.getName());

                mMap.addMarker(markerOptions);

                // Locate the first location
                mMap.animateCamera(CameraUpdateFactory.newLatLng(queriedLocation));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(queriedLocation.latitude, queriedLocation.longitude), 18));

                etlokasi.setText(place.getAddress());
                etlatitude.setText("" + queriedLocation.latitude);
                etlongitude.setText("" + queriedLocation.longitude);
//                        ((EditText) findViewById(R.id.input_longitude)).setText("\n" + queriedLocation.longitude);
//                        //((EditText) findViewById(R.id.input_lokasiprospek)).setText("");


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                com.google.android.gms.common.api.Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void onCheckboxClicked(View view) {
        checked = ((CheckBox) view).isChecked();
//        Log.wtf("CHECKED", "onCheckboxClicked: "+checked);
//        Log.i("TAGCHECKED", "onCheckboxClicked: "+cektruk);
        switch (view.getId()) {
            case R.id.cbtruck:
                if (checked) {
                    cbpickup.setChecked(false);
                    cbtronton.setChecked(false);
                    cbbandobel.setChecked(false);
                    kendaraan1 = ((CheckBox) view).getText().toString();


//                    Toast.makeText(this, "" + kendaraan1, Toast.LENGTH_SHORT).show();
                    // Remove the meat
                    break;
                } else {
                    kendaraan1 = null;
//                    Toast.makeText(this, "Optional", Toast.LENGTH_SHORT).show();
                }
                Log.wtf("CHECKEDCBADA", "onCheckboxClicked: " + kendaraan1);
            case R.id.cbpickup:
                if (checked) {
                    cbtruck.setChecked(false);
                    cbtronton.setChecked(false);
                    cbbandobel.setChecked(false);
                    kendaraan1 = ((CheckBox) view).getText().toString();
//                    Toast.makeText(this, "" + kendaraan2, Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    kendaraan1 = null;
//                    Toast.makeText(this, "Optional", Toast.LENGTH_SHORT).show();
                }
            case R.id.cbtronton:
                if (checked) {
                    cbtruck.setChecked(false);
                    cbpickup.setChecked(false);
                    cbbandobel.setChecked(false);
                    kendaraan1 = ((CheckBox) view).getText().toString();
//                    Toast.makeText(this, "" + kendaraan2, Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    kendaraan1 = null;
//                    Toast.makeText(this, "Optional", Toast.LENGTH_SHORT).show();
                }
            case R.id.cbbandobel:
                if (checked) {
                    cbtruck.setChecked(false);
                    cbpickup.setChecked(false);
                    cbtronton.setChecked(false);
                    kendaraan1 = ((CheckBox) view).getText().toString();
//                    Toast.makeText(this, "" + kendaraan2, Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    kendaraan1 = null;
//                    Toast.makeText(this, "Optional", Toast.LENGTH_SHORT).show();
                }
            case R.id.cbada:
                if (checked) {
                    cbtdkada.setChecked(false);
                    cbadab.setChecked(false);
                    portal = ((CheckBox) view).getText().toString();
                    if (portal.equals("Ada (FREE)")) {
                        portal = "1";
                    }
//                    Toast.makeText(this, "" + portal, Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    portal = null;
                }

            case R.id.cbadab:
                if (checked){
                    cbtdkada.setChecked(false);
                    cbada.setChecked(false);
                    portal = ((CheckBox) view).getText().toString();
                    if (portal.equals("Ada (Bayar)")){
                        portal = "1";
                    }

                    break;
                }else {
                    portal = null;
                }

            case R.id.cbtdkada:
                if (checked) {
                    cbada.setChecked(false);
                    cbadab.setChecked(false);
                    portal = ((CheckBox) view).getText().toString();
                    if (portal.equals("Tidak Ada")) {
                        portal = "0";
                    }
//                    Toast.makeText(this, "" + portal, Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    portal = null;
                }
                // TODO: Veggie sandwich
        }
    }

    @Override
    public void onBackPressed() {

        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(FPPB2Get.this)
                .setBody("Apakah anda yakin akan keluar")
                .setNegativeColor(R.color.orange)
                .setNegativeButtonText("TIDAK")
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButtonText("YA")
                .setPositiveColor(R.color.red)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {

                        FPPB2Get.this.finish();

                    }
                })
                .build();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opme, menu);
        return true;
    }

    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.riwa:
                Intent i = new Intent(FPPB2Get.this, riwayatfppb.class);
                startActivity(i);
                finish();
                return true;
/*
            case R.id.hps:
                etnama.setText("");
                etnomemb.setText("");
                etalamat.setText("");
//                    act_kelurahan.setText("");
//                    act_kecamatan.setText("");
//                    act_kota.setText("");
//                    act_prov.setText("");
                *//*spinner1.setText("");*//*
                spinner2.setText("");
                spinner3.setText("");
                spinner4.setText("");
                ettelepon.setText("");
                ethandphone.setText("");
                etlokasi.setText("");
                etlatitude.setText("");
                etlongitude.setText("");
                etpenerima.setText("");
                //disable llspinner
                llko.setVisibility(View.GONE);
                llkecamatan.setVisibility(View.GONE);
                llkelurahan.setVisibility(View.GONE);

                mMap.clear();
                cbada.setChecked(false);
                cbadab.setChecked(false);
                cbtdkada.setChecked(false);
                cbpickup.setChecked(false);
                cbtruck.setChecked(false);
                cbtronton.setChecked(false);
                cbbandobel.setChecked(false);
                etnomemb.requestFocus();
                kendaraan1 = null;
//                    kendaraan2 = null;
                portal = null;
                etrt.setText("");
                etrw.setText("");
                ettgldikirim.setText(formattedDate);

                return true;*/

            case R.id.cari:
                Intent a = new Intent(FPPB2Get.this, Cari.class);
                startActivity(a);
                /*finish();
                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style_json);
        googleMap.setMapStyle(style);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }

        gerai = "202"; //201 = SIDOARJO, 202 = MALANG , 301 = BALI

        if (gerai.equals(GeraiSidoarjo)){
            Lokasi="SIDOARJO";
            Config.setLokasi("SIDOARJO");
            Config.setTextDepo("SIDOARJO");
            Config.setTitle("SIDOARJO");
            Config.setLattitude(latsidoarjo);
            Config.setLontitude(lonsidoarjo);
        }else if(gerai.equals(GeraiBali)){
            Config.setLokasi("BALI");
            Config.setTextDepo("BALI");
            Config.setTitle("BALI");
            Config.setLattitude(latbali);
            Config.setLontitude(lonbali);

        }else if (gerai.equals(GeraiMalang)){
            Config.setLokasi("MALANG");
            Config.setTextDepo("MALANG");
            Config.setTitle("MALANG");
            Config.setLattitude(latmalang);
            Config.setLontitude(lonmalang);
        }

        tvfppblokasi.setText(depo + " " + Config.getTextDepo());
        Marker marker = mMap.addMarker(new MarkerOptions()
                .title(depo + " " + Config.getTitle())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.home_map))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(Config.getLattitude(), Config.getLontitude())));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(Config.getLattitude(), Config.getLontitude()), 18));
        marker.showInfoWindow();

//        toolbar.setTitle(Config.getLokasi());
        TextView txtTitle = (TextView) findViewById(R.id.textTitleMarque);
        txtTitle.setText("FPPB " + Config.getLokasi() + " ( Untuk melihat semua riwayat klik menu Cari riwayat dan inputkan nomor HP untuk pencarian data riwayat FPB )");
        txtTitle.setSelected(true);
        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FPPB2Get.this,Cari.class);
                startActivity(intent);
                finish();
            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                mMap.clear();

                // Getting the Latitude and Longitude of the touched location
                latLng = latLng;

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Creating a marker
                markerOptions = new MarkerOptions();
                markerOptions1 = new MarkerOptions();
                markerOptions2 = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);
                markerOptions1.position(latLng);
                markerOptions2.position(latLng);

                markerOptions1.title(latLng.latitude + "");
                markerOptions2.title(latLng.longitude + "");
                ((EditText) findViewById(R.id.etlatitude)).setText(markerOptions1.getTitle());
                ((EditText) findViewById(R.id.etlongitude)).setText(markerOptions2.getTitle());

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

                // Adding Marker on the touched location with address
                new ReverseGeocodingTask(getBaseContext()).execute(latLng);
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String provinselect = listprov.get(i).getNamep();
        Toast.makeText(this, ""+provinselect, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //njopok addres dari map picker
    public class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String> {
        Context mContext;

        public ReverseGeocodingTask(Context context) {
            super();
            mContext = context;
        }

        // Finding address using reverse geocoding
        @Override
        protected String doInBackground(LatLng... params) {
            Geocoder geocoder = new Geocoder(mContext);
            double latitude = params[0].latitude;
            double longitude = params[0].longitude;

            List<Address> addresses = null;
            String addressText = "";

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

//                    addressText = String.format("%s, %s, %s",
//                            address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
//                    addressText = address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : addressText;
                addressText = address.getAddressLine(0);
                //                    address.getLocality(),
//                            address.getCountryName());
            }

            return addressText;

        }

        @Override
        protected void onPostExecute(String addressText) {
            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(addressText);
            // Placing a marker on the touched position
            mMap.addMarker(markerOptions);
            ((EditText) findViewById(R.id.etlokasi)).setText(markerOptions.getTitle());

        }

    }

    private void fixkirim2() {

        String ttelp = ettelepon.getText().toString().trim();
//        Log.i("TTELP", "fixkirim:"+ttelp);
        String thandp = ethandphone.getText().toString().trim();

//        etnama.onEditorAction(EditorInfo.IME_ACTION_DONE);
        if (ttelp.equals("") && thandp.equals("")) {
            notelp = false;
        }
        if (!ttelp.equals("") || !thandp.equals("")) {
            notelp = true;
        }
        Log.i("NOTELP", "fixkirim: " + notelp);


        if (etnama.getText().toString().trim().equals("") ||
                etalamat.getText().toString().trim().equals("") ||
                spinner4.getText().toString().trim().equals("") ||
                spinner3.getText().toString().trim().equals("") ||
                spinner2.getText().toString().trim().equals("") ||
                spinner1.getText().toString().trim().equals("") ||
                notelp == false || kendaraan1 == null || portal == null
//                        portal.isEmpty()

                ) {
            tampilsnack("Mohon Lengkapi Data Anda");


        } else {
            runkirim2();
        }

    }

    private void runkirim2() {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("no_member", etnomemb.getText().toString())
                .add("nama", etnama.getText().toString())
                .add("alamat", etalamat.getText().toString())
                .add("rt", etrt.getText().toString().trim())
                .add("rw",etrw.getText().toString().trim())
                .add("kelurahan", idKel2)
                .add("kecamatan", idKec2)
                .add("kabupaten", idKab2)
                .add("provinsi", idProv2)
                .add("telp", ettelepon.getText().toString())
                .add("hp", ethandphone.getText().toString())
                .add("kendaraan", kendaraan1)
                .add("portal", portal)
                .add("warehouse", gerai)
                .add("latit", etlatitude.getText().toString())
                .add("longit", etlongitude.getText().toString())
                .add("penerima", etpenerima.getText().toString())
                .add("tanggal_kirim", kc.konvertdate(ettgldikirim.getText().toString().trim(),
                        "EEEE, dd MMMM yyyy", "yyyyMMdd"))
                .build();

        Request request = new Request.Builder()
                .url(urlkirim)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                Log.i("Cari.class", "onFailure: " + e.toString());

                FPPB2Get.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.i("MYRESPONSE", "onResponse: " + myResponse);

                FPPB2Get.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String resp = myResponse;
                        Log.i("resp", "run: " + resp + " ");

                        if (response.isSuccessful()) {
                            tampilsnack("" + resp);
                        }
                    }
                });

            }
        });
    }
}