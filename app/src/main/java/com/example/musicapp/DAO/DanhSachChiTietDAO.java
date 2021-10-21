package com.example.musicapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.musicapp.Database.DBHelper;
import com.example.musicapp.Models.BaiHat;
import com.example.musicapp.Models.DanhSachChiTiet;

import java.util.ArrayList;
import java.util.List;

public class DanhSachChiTietDAO {

    private SQLiteDatabase db;
    static final String TABLE_NAME = "DanhSachChiTiet";
    final String iddanhsachChiTiet = "iddanhsachChiTiet";
    final String idbaihat = "idbaihat";
    final String iddanhsachPhat = "iddanhsachPhat";

    public DanhSachChiTietDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public long insert_Danh_Sach_CT(DanhSachChiTiet obj) {
        ContentValues values = new ContentValues();

        values.put( idbaihat , obj.getIdbaihat() );
        values.put( iddanhsachPhat , obj.getIddanhsachPhat() );


        return db.insert(TABLE_NAME , null, values);
    }

    public int delete_Danh_Sach_CT(String id) {
        return db.delete(TABLE_NAME ,  iddanhsachChiTiet +"=?", new String[]{id});
    }

    public long delete_DSP_BH(String id_danh_sach_Phat) {
        String sql = "DELETE FROM DanhSachChiTiet WHERE iddanhsachPhat = " + id_danh_sach_Phat;
        Cursor c = db.rawQuery(sql ,  null);
        if (c == null){
            Log.e("delete BH in DSP" , "---------------");
            return 1;
        } else {
            c.moveToFirst();

            while (c.moveToNext()) {

                Log.e( c.getString( c.getColumnIndex( "idbaihat" )) , "-----------------idbaihat" );

            }
            c.close();
        }
        return -1;
    }

    public long delete_BH_DSP(int id_DSP , int id_BH){
        String sql = "DELETE FROM DanhSachChiTiet WHERE idbaihat = " + id_BH + " AND iddanhsachPhat = " + id_DSP;
        Cursor c = db.rawQuery(sql ,  null);
        if (c == null){
            Log.e("delete BH in DSP" , "---------------");
            return 1;
        } else {
            c.moveToFirst();

            while (c.moveToNext()) {

                Log.e( c.getString( c.getColumnIndex( "idbaihat" )) , "-----------------idbaihat" );

            }
            c.close();
        }
        return -1;
    }

    public List<DanhSachChiTiet> getAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getData(sql);
    }


    public DanhSachChiTiet getID(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + iddanhsachChiTiet + "=?";

        List<DanhSachChiTiet> list = getData(sql, id);
        return list.get(0);
    }

    public List<String> get_ID_DSP(int id_DSP) {
        String sql = "SELECT * FROM DanhSachChiTiet" +
                " INNER JOIN BaiHat ON BaiHat.idbaihat = DanhSachChiTiet.idbaihat" +
                " WHERE DanhSachChiTiet.iddanhsachPhat = " + id_DSP;

        List<String> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql ,  null);
        c.moveToFirst();

        while (c.moveToNext()) {

            list.add( c.getString( c.getColumnIndex( "tenBaiHat" )) );

        }
        c.close();
        return list;
    }


    private List<DanhSachChiTiet> getData(String sql, String... selectionArgs) {
        List<DanhSachChiTiet> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        c.moveToFirst();

        while (c.moveToNext()) {

            DanhSachChiTiet obj = new DanhSachChiTiet();
            obj.setIddanhsachChiTiet(c.getInt(c.getColumnIndex( iddanhsachChiTiet )));
            obj.setIdbaihat( c.getInt(c.getColumnIndex( idbaihat )));
            obj.setIddanhsachPhat( c.getInt(c.getColumnIndex( iddanhsachPhat )));

            list.add(obj);
        }
        c.close();
        return list;
    }

    public int insert_Loai(DanhSachChiTiet danhSachChiTiet){
        int result = 0;
        List<DanhSachChiTiet> list = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();

        while (c.moveToNext()) {

            DanhSachChiTiet obj = new DanhSachChiTiet();

            obj.setIddanhsachChiTiet(c.getInt(c.getColumnIndex( iddanhsachChiTiet )));

            obj.setIdbaihat(c.getInt(c.getColumnIndex( idbaihat )));

            obj.setIddanhsachPhat(c.getInt(c.getColumnIndex( iddanhsachPhat )));

            if ( danhSachChiTiet.getIddanhsachPhat() == obj.getIddanhsachPhat()
                    && danhSachChiTiet.getIdbaihat() == obj.getIdbaihat() ){
                continue;
            } else {
                this.insert_Danh_Sach_CT(danhSachChiTiet);
                return 1;
            }
        }
        c.close();

        if (result == 0) {

            return -1;
        }
        return 1;
    }

}
