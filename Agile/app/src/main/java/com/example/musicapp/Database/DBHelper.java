package com.example.musicapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DataBaseName = "musicdb";
    public Context context;
    private static final int version = 1;


    public DBHelper(Context context) {
        super(context, DataBaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableBaiHat = "create table BaiHat("+
                "idbaihat INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tencasi TEXT NOT NULL," +
                "trangthai TEXT NOT NULL," +
                "hinhanhbaihat TEXT NOT NULL)";
        db.execSQL(createTableBaiHat);



        String createTableDanhSach = "create table DanhSachPhat("+
                "iddanhsachPhat INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tendanhsachPhat TEXT NOT NULL)";
        db.execSQL(createTableDanhSach);


        String createTableDanhSachChiTiet = "create table DanhSachChiTiet("+
                "iddanhsachChiTiet INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idbaihat INTEGER NOT NULL REFERENCES BaiHat(idbaihat)," +
                "iddanhsachPhat INTEGER NOT NULL REFERENCES DanhSachPhat(iddanhsachPhat))";
        db.execSQL(createTableDanhSachChiTiet);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
