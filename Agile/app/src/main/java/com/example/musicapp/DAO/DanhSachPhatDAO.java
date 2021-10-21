package com.example.musicapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.musicapp.Database.DBHelper;
import com.example.musicapp.Database.DBHelper;
import com.example.musicapp.Models.DanhSachPhat;

import java.util.ArrayList;
import java.util.List;

public class DanhSachPhatDAO {
    private SQLiteDatabase db;

    public DanhSachPhatDAO(Context context) {
        DBHelper dbHelper =new DBHelper(context);
        this.db =dbHelper.getWritableDatabase();
    }

    public long insert(DanhSachPhat obj){
        ContentValues values =new ContentValues();

        values.put(Name.tendanhsachPhat, obj.getTendanhsachPhat());
        return db.insert("DanhSachPhat", null, values);
    }

    public int delete(String id){
        return db.delete("DanhSachPhat","iddanhsachPhat=?", new String[]{id});
    }

    public List<DanhSachPhat> getAll() {
        String sql = "SELECT * FROM DanhSachPhat";
        return getData(sql);
    }


    public DanhSachPhat getID(String id) {
        String sql = "SELECT * FROM DanhSachPhat WHERE iddanhsachPhat=?";
        List<DanhSachPhat> list = getData(sql, id);
        return list.get(0);
    }

    public DanhSachPhat getHoaDonNew() {
        String sql = "SELECT * FROM DanhSachPhat ORDER by iddanhsachPhat DESC";
        List<DanhSachPhat> list = getData(sql);
        return list.get(0);
    }


    private List<DanhSachPhat> getData(String sql, String... selectionArgs) {
        List<DanhSachPhat> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            DanhSachPhat obj = new DanhSachPhat();
            obj.setIddanhsachPhat(c.getInt(c.getColumnIndex(Name.iddanhsachPhat)));
            obj.setTendanhsachPhat(c.getString(c.getColumnIndex(Name.tendanhsachPhat)));
            list.add(obj);
        }
        return list;
    }

    public static class Name {
        public static String iddanhsachPhat = "iddanhsachPhat";
        public static String tendanhsachPhat = "tendanhsachPhat";

    }


}
