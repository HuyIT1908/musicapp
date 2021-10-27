package com.example.musicapp.Fragment_Cua_Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.musicapp.PlayActivity;
import com.example.musicapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimKiemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimKiemFragment extends Fragment {

    SearchView searchView;
    ListView listView;
    List<String> items;
    List<String> tk_list;
    CustomAdapter adapter;

    ArrayAdapter<String> arrayAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimKiemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimKiemFragment newInstance(String param1, String param2) {
        TimKiemFragment fragment = new TimKiemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        searchView = view.findViewById(R.id.search_bar);
        listView = view.findViewById(R.id.listViewSong);
        tk_list = new ArrayList<>();

        hien_nhac();

//        TimKiemFragment();
        return view;
    }

    public void TimKiemFragment() {
        // Required empty public constructor

        final ArrayList<File> mySongs = (ArrayList<File>) findSong(Environment.getExternalStorageDirectory());

//        items = new String[mySongs.size()];
//        List<String> ten_bai_hat = new ArrayList<>();
//        for (int i = 0; i < mySongs.size(); i++) {
//            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "");
//            ten_bai_hat.add( mySongs.get(i).getName().toString().replace(".mp3", "") );
//        }

//        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, ten_bai_hat);
//        listView.setAdapter(arrayAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                TimKiemFragment.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                TimKiemFragment.this.arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
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

    void hien_nhac() {
        final ArrayList<File> mySongs = (ArrayList<File>) findSong(Environment.getExternalStorageDirectory());

        items = new ArrayList<>();
        List<String> ten_bai_hat = new ArrayList<>();

        for (int i = 0; i < mySongs.size(); i++) {
//            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "");
            items.add(mySongs.get(i).getName().toString().replace(".mp3", "") );
            ten_bai_hat.add( mySongs.get(i).getName().toString().replace(".mp3", "") );
        }
        tk_list = ten_bai_hat;

        TimKiemFragment.CustomAdapter customAdapter = new TimKiemFragment.CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName = items.get(position);

                final ArrayList<String> ten_bh;
                ten_bh = (ArrayList<String>) items;

                startActivity(new Intent(getContext(), PlayActivity.class)
                        .putExtra("songs", mySongs )
                        .putExtra("ten_bh", ten_bh )
                        .putExtra("songname" , songName )
                        .putExtra("vi_tri" , position) );
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if ( valiDate(query) ){
                    items.clear();
                    items.addAll(tim_kiem(query , tk_list) );
                    hien_danh_sach();
                }else {
                    items.clear();
                    items.addAll(tk_list);
                    hien_danh_sach();
                    Toast.makeText(getContext(), "Không được để trống nha", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if ( valiDate(newText) ){
                    items.clear();
                    items.addAll(tim_kiem(newText , tk_list) );
                    hien_danh_sach();
                }else {
                    items.clear();
                    items.addAll(tk_list);
                    hien_danh_sach();
                    Toast.makeText(getContext(), "Không được để trống nha", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
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
            View view = getLayoutInflater().inflate(R.layout.item_bh_home, null);
            TextView txtSong = view.findViewById(R.id.txtSong);
            txtSong.setSelected(true);
            txtSong.setText(items.get(position));
            return view;
        }
    }

    private void hien_danh_sach(){
        adapter = new CustomAdapter();
        listView.setAdapter(adapter);
    }

    private List<String> tim_kiem(String bh , List<String> list){
        List<String> save_list = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if ( list.get(i).toString().toLowerCase().contains( bh.toLowerCase().toString() ) ){
                   save_list.add( list.get(i).toString() );
            }
        }

        return save_list;
    }

    public boolean valiDate(String... editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            if (editTexts[i].toString().isEmpty()) {
                return false;
            }
            ;
        }
        return true;
    }

}