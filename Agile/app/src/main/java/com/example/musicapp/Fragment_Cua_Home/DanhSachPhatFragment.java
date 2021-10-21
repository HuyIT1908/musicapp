package com.example.musicapp.Fragment_Cua_Home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.AdapterDanhSachphat;
import com.example.musicapp.DAO.DanhSachPhatDAO;
import com.example.musicapp.Models.DanhSachPhat;
import com.example.musicapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class DanhSachPhatFragment extends Fragment {

    FloatingActionButton btnAdd;
    RecyclerView recyclerView;
    AdapterDanhSachphat adapterDanhSachphat;
    DanhSachPhatDAO danhSachPhatDAO;
    ArrayList<DanhSachPhat> listDanhSachPhat;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_danh_sach_phat, container, false);
        btnAdd = (FloatingActionButton) view.findViewById(R.id.btn_themdanhsachphat);
        recyclerView = (RecyclerView) view.findViewById(R.id.list_danhsachphat);
        danhSachPhatDAO = new DanhSachPhatDAO(getContext());
        listDanhSachPhat = (ArrayList<DanhSachPhat>) danhSachPhatDAO.getAll();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapterDanhSachphat = new AdapterDanhSachphat(getContext(), listDanhSachPhat);
        recyclerView.setAdapter(adapterDanhSachphat);


        adapterDanhSachphat.onClickItemListener(new AdapterDanhSachphat.onClickListener() {
            @Override
            public void onClick(int possion) {
                Toast.makeText(getContext(), "hhhhhhh", Toast.LENGTH_LONG).show();
                ;
            }

        });


        adapterDanhSachphat.onClickDeleteListener(new AdapterDanhSachphat.onClickListener() {
            @Override
            public void onClick(int possion) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn xoá không ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int resultDel = danhSachPhatDAO.delete(String.valueOf(listDanhSachPhat.get(possion).getIddanhsachPhat()));
                        if (resultDel > 0) {
                            reload();
                            adapterDanhSachphat.refresh((ArrayList) danhSachPhatDAO.getAll());
                        }
                        dialog.dismiss();
                    }

                });
                builder.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_themdanhsachphat, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(layout);
                AlertDialog alertDialog = builder.create();

                EditText edTenDanhSachPhat = (EditText) layout.findViewById(R.id.edTenDanhSachPhat);
                Button btn_them = (Button) layout.findViewById(R.id.btn_ThemDanhSach);
                Button btn_huy = (Button) layout.findViewById(R.id.btn_huyThemDanhSach);

//                listDanhSachPhat = (ArrayList<DanhSachPhat>) danhSachPhatDAO.getAll();
                Log.e("listdanhsachphat", String.valueOf(listDanhSachPhat.size()));


                btn_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (valiDate(edTenDanhSachPhat)) {
                            DanhSachPhat danhSachPhat = new DanhSachPhat();
                            danhSachPhat.setTendanhsachPhat(edTenDanhSachPhat.getText().toString());
                            long result = danhSachPhatDAO.insert(danhSachPhat);

                            if (result > 0) {
                                adapterDanhSachphat.refresh((ArrayList) danhSachPhatDAO.getAll());
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                reload();
                            }
                            alertDialog.dismiss();

                        } else {
                            Toast.makeText(getContext(), "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                alertDialog.show();

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });

        return view;
    }


    public void reload() {
        listDanhSachPhat.clear();
        listDanhSachPhat.addAll(danhSachPhatDAO.getAll());
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