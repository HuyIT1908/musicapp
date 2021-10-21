package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.DAO.BaiHatDAO;
import com.example.musicapp.DAO.DanhSachChiTietDAO;
import com.example.musicapp.DAO.DanhSachPhatDAO;
import com.example.musicapp.Fragment_Cua_Home.HomeFragment;
import com.example.musicapp.Models.BaiHat;
import com.example.musicapp.Models.DanhSachChiTiet;
import com.example.musicapp.Models.DanhSachPhat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaiHatInDSPActivity extends AppCompatActivity {

    BaiHatDAO baiHatDAO;
    DanhSachChiTietDAO danhSachChiTietDAO;
    DanhSachPhatDAO danhSachPhatDAO;
    FloatingActionButton fba_bh_add;
    TextView tv_Ten_DSP;
    ListView listView;


    List<String> items;
    CustomAdapter adapter;
    int index_TenBH;
    String name_DSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_hat_in_dspactivity);

        Bundle bundle = getIntent().getExtras();

        String name_DSP = bundle.getString("nameDSP" , "No data");
        this.name_DSP = name_DSP;

        tv_Ten_DSP = findViewById(R.id.tv_ten_DSP);
        tv_Ten_DSP.setText(name_DSP);

        baiHatDAO = new BaiHatDAO(getApplicationContext() );
        danhSachPhatDAO = new DanhSachPhatDAO(getApplicationContext() );
        danhSachChiTietDAO = new DanhSachChiTietDAO(getApplicationContext() );
        fba_bh_add = findViewById(R.id.fba_them_bai_hat_DSP);
        listView = findViewById(R.id.dsp_bh);
        items = new ArrayList<>();

        items.clear();
        items = danhSachChiTietDAO.get_ID_DSP( get_ID_DSP(name_DSP) );
        hien_bai_hat();

        fba_bh_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.add_bh_dsp_dialog, null);

                Spinner spinner_add = (Spinner) layout.findViewById(R.id.spn_bh);

                Button btn_them = (Button) layout.findViewById(R.id.btn_ThemBH);
                Button btn_huy = (Button) layout.findViewById(R.id.btn_huyThemBH);

                List<BaiHat> list = baiHatDAO.getAll();

                List<String> tenBH = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    tenBH.add(list.get(i).getTenBaiHat().toString() );
                }

                Collections.reverse(tenBH);
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_spinner_item,
                        tenBH
                );
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_add.setAdapter(arrayAdapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(BaiHatInDSPActivity.this);
                builder.setView(layout);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                spinner_add.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        index_TenBH = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btn_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        items = danhSachChiTietDAO.get_ID_DSP( get_ID_DSP(name_DSP) );
                        items.add( tenBH.get(index_TenBH) );

                        DanhSachChiTiet danhSachChiTiet = new DanhSachChiTiet(
                                get_ID_Bai_hat( tenBH.get(index_TenBH) ),
                                get_ID_DSP(name_DSP)
                        );
                        long kq = danhSachChiTietDAO.insert_Danh_Sach_CT(danhSachChiTiet);
                        if (kq > 0) {
                            Toast.makeText(getApplicationContext(),
                                    "Thêm thành công",
                                    Toast.LENGTH_SHORT).show();
                            if (danhSachChiTietDAO.get_ID_DSP( get_ID_DSP(name_DSP) ).size() == 0 ){
                                danhSachChiTietDAO.insert_Danh_Sach_CT(danhSachChiTiet);
                            }
                        }

                        hien_bai_hat();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ArrayList<File> mySongs = (ArrayList<File>) findSong(Environment.getExternalStorageDirectory());
                final ArrayList<String> ten_bh = (ArrayList<String>) items;

                startActivity(new Intent(getApplicationContext(), PlayActivity.class)
                        .putExtra("songs", mySongs )
                        .putExtra("ten_bh", ten_bh )
                        .putExtra("songname" , items.get(position) )
                        .putExtra("vi_tri" , position) );
            }
        });

    } // end onCreate , https://github.com/HuyIT1908/Sample_project

    private int get_ID_Bai_hat(String tenBH){
        List<BaiHat> baiHatList = baiHatDAO.getAll();

        for (int i = 0; i < baiHatList.size(); i++) {
            if ( tenBH.equals( baiHatList.get(i).getTenBaiHat().toString()) ){
                return baiHatList.get(i).getIdbaihat();
            }
        }
        return 0;
    }

    private int get_ID_DSP(String dsp_Name){
        List<DanhSachPhat> danhSachPhats = danhSachPhatDAO.getAll();

        for (int i = 0; i < danhSachPhats.size(); i++) {
            if ( dsp_Name.equals( danhSachPhats.get(i).getTendanhsachPhat().toString()) ){
                return danhSachPhats.get(i).getIddanhsachPhat();
            }
        }
        return 0;
    }

    public List<File> findSong(File file) {
        List<File> arrayList = new ArrayList<>();

        File[] files = file.listFiles();

        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                arrayList.addAll(findSong(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp3")) {
                    arrayList.add(singleFile);
                }
            }

        }
        return arrayList;
    }

//    private ArrayList<File>  hien_nhac(List<String>) {
//        final ArrayList<File> mySongs = (ArrayList<File>) findSong(Environment.getExternalStorageDirectory());
//
//        for (int i = 0; i < mySongs.size(); i++) {
//
//            nhap.add(mySongs.get(i).getName().toString().replace(".mp3", "") );
//        }
//        return nhap;
//    } //        end hien nhac

    private void hien_bai_hat(){
        adapter = new CustomAdapter();
        listView.setAdapter(adapter);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view= getLayoutInflater().inflate(R.layout.item_nhac_bh_dsp, null);

            TextView txtSong = view.findViewById(R.id.txtSong);
            ImageView delete_bh_dsp = view.findViewById(R.id.delete_bh_dsp);

            txtSong.setSelected(true);
            txtSong.setText(items.get(position));

            delete_bh_dsp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BaiHatInDSPActivity.this);
                    builder.setMessage("Bạn có muốn xóa bài hát không ?");

                    builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            danhSachChiTietDAO.delete_BH_DSP(get_ID_DSP(name_DSP) , get_ID_Bai_hat(items.get(position)) );
                            items.remove(position);
                            hien_bai_hat();
                            Toast.makeText(getApplicationContext(), "Đã xóa bài hát !", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            return view;
        }
    }

    public boolean valiDate(EditText... editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            if (editTexts[i].getText().toString().isEmpty()) {
                return false;
            }
            ;
        }
        return true;
    }
}