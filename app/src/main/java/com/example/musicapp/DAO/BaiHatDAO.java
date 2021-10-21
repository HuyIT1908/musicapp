package com.example.musicapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.musicapp.Database.DBHelper;
import com.example.musicapp.Models.BaiHat;
import com.example.musicapp.Models.DanhSachPhat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaiHatDAO {

    private SQLiteDatabase db;
    static final String TABLE_NAME = "BaiHat";
    final String idbaihat = "idbaihat";
    final String tenBaiHat = "tenBaiHat";
    final String tencasi = "tencasi";
    final String trangthai = "trangthai";
    final String hinhanhbaihat = "hinhanhbaihat";

    public BaiHatDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public long insert_BAI_HAT(BaiHat obj) {
        ContentValues values = new ContentValues();

        values.put( "tenBaiHat" , obj.getTenBaiHat());
        values.put( "tencasi" , obj.getTencasi() );
        values.put( "trangthai" , obj.getTrangthai() );
        values.put( "hinhanhbaihat" , obj.getHinhanhbaihat() );

        return db.insert(TABLE_NAME , null, values);
    }

    public int delete_BAI_HAT(String id) {
        return db.delete(TABLE_NAME , "idbaihat=?", new String[]{id});
    }

    public List<BaiHat> getAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getData(sql);
    }


    public BaiHat getID(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + idbaihat + "=?";

        List<BaiHat> list = getData(sql, id);
        return list.get(0);
    }

//    public DanhSachPhat getHoaDonNew() {
//        String sql = "SELECT * FROM DanhSachPhat ORDER by iddanhsachPhat DESC";
//        List<DanhSachPhat> list = getData(sql);
//        return list.get(0);
//    }


    private List<BaiHat> getData(String sql, String... selectionArgs) {
        List<BaiHat> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        c.moveToFirst();

        while (c.moveToNext()) {

            BaiHat obj = new BaiHat();
            obj.setIdbaihat(c.getInt(c.getColumnIndex( idbaihat )));
            obj.setTenBaiHat(c.getString(c.getColumnIndex( tenBaiHat )));
            obj.setTencasi(c.getString(c.getColumnIndex( tencasi )));
            obj.setTrangthai(c.getString(c.getColumnIndex( trangthai )));

            obj.setHinhanhbaihat( c.getInt(c.getColumnIndex( hinhanhbaihat )));


            list.add(obj);
        }
        c.close();
        return list;
    }

    public int insert_Loai(BaiHat baiHat){
        int result = 0;
        List<BaiHat> list = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();

        while (c.moveToNext()) {

            BaiHat obj = new BaiHat();
            obj.setIdbaihat(c.getInt(c.getColumnIndex( idbaihat )));
            obj.setTenBaiHat(c.getString(c.getColumnIndex( tenBaiHat )));
            obj.setTencasi(c.getString(c.getColumnIndex( tencasi )));
            obj.setTrangthai(c.getString(c.getColumnIndex( trangthai )));

            obj.setHinhanhbaihat( c.getInt(c.getColumnIndex( hinhanhbaihat )));

            if ( baiHat.getTenBaiHat().toString().equals( obj.getTenBaiHat()) ){
                c.close();
                return 1;
            }
        }
        c.close();

        if (result == 0) {
            this.insert_BAI_HAT(baiHat);
            return -1; // không giống nhau
        }
        return 1; // giống nhau
    }

}
