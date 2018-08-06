package id.co.megadepo.fppb_sidoarjo.Riwayat_FPPB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by addin on 31/10/17.
 */

public class sqLite extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "riwayat.db";
        private static final int DATABASE_VERSION = 2;

        private  static String sql = "create table riwayatfppb (notemp text null ,tgl text null);";

    /*private  static String sql = "create table riwayatfppb (notemp text null, nama text null ,tgl text null);";*/

        public sqLite(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            Log.d("Data", "onCreate: " + sql);
            db.execSQL(sql);
//            sql = "INSERT INTO riwayatfppb (no, notemp, tgl) VALUES ('1001', 'Fathur', '1994-02-03', 'Laki-laki','Jakarta');";
//            db.execSQL(sql);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        /*    db.execSQL("drop table if exists riwayatfppb");
            onCreate(db);*/
            // TODO Auto-generated method stub

        }

    public void insertData (String notemp ,String tgl){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " insert into riwayatfppb(notemp, tgl) VALUES ('"+notemp+"' , '"+tgl+"') ";

        Log.e("inset sqlite", "" + query);
        db.execSQL(query);
        db.close();

    }

    public ArrayList<HashMap<String,String>> getAllData(String notemp){
        ArrayList<HashMap<String,String>> dataList;
        dataList = new ArrayList<HashMap<String, String>>();
        String query = " select * from riwayatfppb where notemp like '"+notemp+"%' order by notemp DESC LIMIT 20 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("notemp", cursor.getString(0));
//                map.put("nama", cursor.getString(1));
                map.put("tgl", cursor.getString(1));
                dataList.add(map);
            } while (cursor.moveToNext());
        }
        Log.e("select sqlite", "" +dataList);

        db.close();
        return dataList;
    }

    }
