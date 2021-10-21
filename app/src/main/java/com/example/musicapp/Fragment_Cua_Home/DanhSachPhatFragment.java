package com.example.musicapp.Fragment_Cua_Home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.musicapp.BaiHatInDSPActivity;
import com.example.musicapp.DAO.BaiHatDAO;
import com.example.musicapp.DAO.DanhSachChiTietDAO;
import com.example.musicapp.DAO.DanhSachPhatDAO;
import com.example.musicapp.Models.BaiHat;
import com.example.musicapp.Models.DanhSachPhat;
import com.example.musicapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DanhSachPhatFragment extends Fragment {

    FloatingActionButton btnAdd;
    RecyclerView recyclerView;
    AdapterDanhSachphat adapterDanhSachphat;
    DanhSachPhatDAO danhSachPhatDAO;
    List<DanhSachPhat> listDanhSachPhat;
    DanhSachChiTietDAO danhSachChiTietDAO;


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
        danhSachChiTietDAO = new DanhSachChiTietDAO(getContext() );


        adapterDanhSachphat.onClickItemListener(new AdapterDanhSachphat.onClickListener() {
            @Override
            public void onClick(int possion) {
                Intent intent = new Intent(getContext() , BaiHatInDSPActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameDSP" , listDanhSachPhat.get(possion).getTendanhsachPhat() );
                intent.putExtras(bundle);
                startActivity(intent);
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
                        long kq = danhSachChiTietDAO.delete_DSP_BH( String.valueOf( listDanhSachPhat.get(possion).getIddanhsachPhat() ) );
                        int resultDel = danhSachPhatDAO.delete(String.valueOf(listDanhSachPhat.get(possion).getIddanhsachPhat()));

                        Log.e("-----------------" , resultDel + "\n\n" + kq);
                        if (resultDel > 0 ) {
                            Toast.makeText(getContext(), "Đã xóa Danh Sách Phát !",
                                    Toast.LENGTH_SHORT).show();
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
                edTenDanhSachPhat.setText("");
                Button btn_them = (Button) layout.findViewById(R.id.btn_ThemDanhSach);
                Button btn_huy = (Button) layout.findViewById(R.id.btn_huyThemDanhSach);

//                listDanhSachPhat = (ArrayList<DanhSachPhat>) danhSachPhatDAO.getAll();
                Log.e("listdanhsachphat", String.valueOf(listDanhSachPhat.size()));


                btn_them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Pattern NotSpecialCharacter = Pattern.compile("[A-Za-z0-9AÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴĐaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵđ ]+");
                        Matcher IDMatcher = NotSpecialCharacter.matcher(edTenDanhSachPhat.getText().toString().trim());

                        if (valiDate(edTenDanhSachPhat)) {
                            if( !IDMatcher.matches() ){ //!IDMatcher.matches()
                                Toast.makeText(getContext(), "không được nhập kí tự đặc biệt !", Toast.LENGTH_SHORT).show();
                            } else {
                                DanhSachPhat danhSachPhat = new DanhSachPhat();
                                danhSachPhat.setTendanhsachPhat(edTenDanhSachPhat.getText().toString());
                                long result = danhSachPhatDAO.insert(danhSachPhat);

                                if (result > 0) {
                                    adapterDanhSachphat.refresh((ArrayList) danhSachPhatDAO.getAll());
                                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    reload();
                                }
                                alertDialog.dismiss();
                            }

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
        }
        return true;
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

}