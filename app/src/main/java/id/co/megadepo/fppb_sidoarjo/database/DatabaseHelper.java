package id.co.megadepo.fppb_sidoarjo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteAssetHelper {

    public static final String COL_ITEM_NUMBER ="itemNumber";
//    public static final String COL_NAMA_BARANG = "NamaBarang";
    public static final String COL_DESCRPITON = "Description";
//    public static final String COL_INVNUM = "InvNum";

    public static final String COL_UOM = "UOM";
    private static final String DB_NAME = "DB_Wilayah3";
    private static final int DB_VER =1;
    private static final String TB_DATA = "inventory2";
    private static SQLiteDatabase db;
    private static DatabaseHelper dbInstance;

    String Description;
//    String INUMM3;
    String UOM;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, context.getExternalFilesDir(null).getAbsolutePath()+"/"+DB_NAME,null,DB_VER );
    }

    public static DatabaseHelper getInstance(Context context){
        if (dbInstance == null){
            dbInstance = new DatabaseHelper(context);
            db = dbInstance.getWritableDatabase();
        }
        return  dbInstance;
    }

    public synchronized void close(){
        super.close();
        if (dbInstance != null){
            dbInstance.close();
        }
    }

    public String getIdProv(String Name){
        String idp = "";
        Cursor cursor = db.rawQuery("select id from provinsi where Name ='"+Name+"' ", null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (cursor.getCount() < 1){
            return "not found";
        }
        idp = cursor.getString(0);
        return idp;
    }

    public String getIdKabupaten (String name){
        String idkab = "";
        Cursor cursor = db.rawQuery("select id from kabupaten where name ='"+name+"' ", null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (cursor.getCount() < 1){
            return "not found";
        }
        idkab = cursor.getString(0);
        return idkab;

    }


    public String getIdKecamatan (String name){
        String idkec = "";
        Cursor cursor = db.rawQuery("select id from kecamatan where name ='"+name+"' ", null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (cursor.getCount() < 1){
            return "not found";
        }
        idkec = cursor.getString(0);
        return idkec;
    }


    public String getIdKelurahan (String name){
        String idkec = "";
        Cursor cursor = db.rawQuery("select id from kelurahan where name ='"+name+"' ", null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (cursor.getCount() < 1){
            return "not found";
        }
        idkec = cursor.getString(0);
        return idkec;
    }

    /*
    public String[] getProvinsi() {

        String query = "Select * from provinsi";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> spinnerContent = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                spinnerContent.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();

        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);

        return allSpinner;
    }
    */

    public ArrayList<ModelProvinsi> getProvinsi() {

        String query = "Select * from provinsi";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<ModelProvinsi> prov = new ArrayList<ModelProvinsi>();
        if (cursor.moveToFirst()) {
            do {
                ModelProvinsi mProv = new ModelProvinsi();
                mProv.setId(cursor.getInt(cursor.getColumnIndex("id")));
                mProv.setName(cursor.getString(cursor.getColumnIndex("Name")));
                prov.add(mProv);

            } while (cursor.moveToNext());
        }
        cursor.close();

/*        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);*/

        return prov;
    }


    public ArrayList<ModelKabupaten> getKabupaten(Integer ProvId) {

        String query = "Select * from kabupaten where province_id ='"+ProvId+"' order by name ASC";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<ModelKabupaten> kab = new ArrayList<ModelKabupaten>();
        if (cursor.moveToFirst()) {
            do {
                ModelKabupaten mProv = new ModelKabupaten();
                mProv.setId(cursor.getInt(cursor.getColumnIndex("id")));
                mProv.setName(cursor.getString(cursor.getColumnIndex("name")));
                kab.add(mProv);

            } while (cursor.moveToNext());
        }
        cursor.close();

/*        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);*/

        return kab;
    }

    public ArrayList<ModelKecamatan> getKecamatan(Integer RegId) {

        String query = "Select * from kecamatan where regency_id ='"+RegId+"' order by name ASC";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<ModelKecamatan> kec = new ArrayList<ModelKecamatan>();
        if (cursor.moveToFirst()) {
            do {
                ModelKecamatan mProv = new ModelKecamatan();
                mProv.setId(cursor.getInt(cursor.getColumnIndex("id")));
                mProv.setName(cursor.getString(cursor.getColumnIndex("name")));
                kec.add(mProv);

            } while (cursor.moveToNext());
        }
        cursor.close();

/*        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);*/

        return kec;
    }

    public ArrayList<ModelKelurahan> getKelurahan(Integer DistId) {

        String query = "Select * from kelurahan where district_id ='"+DistId+"' order by name ASC";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<ModelKelurahan> kel = new ArrayList<ModelKelurahan>();
        if (cursor.moveToFirst()) {
            do {
                ModelKelurahan mProv = new ModelKelurahan();
                mProv.setId(cursor.getInt(cursor.getColumnIndex("id")));
                mProv.setName(cursor.getString(cursor.getColumnIndex("name")));
                kel.add(mProv);

            } while (cursor.moveToNext());
        }
        cursor.close();

/*        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);*/

        return kel;
    }
}
