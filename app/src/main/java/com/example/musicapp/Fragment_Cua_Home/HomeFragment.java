package com.example.musicapp.Fragment_Cua_Home;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.musicapp.DAO.BaiHatDAO;
import com.example.musicapp.Models.BaiHat;
import com.example.musicapp.PlayActivity;
import com.example.musicapp.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    ListView listView;
    String[] items;
//    huy
    BaiHatDAO baiHatDAO;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listViewSong);
//        huy
        baiHatDAO = new BaiHatDAO(getContext());
        runtimePermission();

        return view;
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

        items = new String[mySongs.size()];
        List<String> ten_bai_hat = new ArrayList<>();
        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "");
            ten_bai_hat.add( mySongs.get(i).getName().toString().replace(".mp3", "") );
        }
//        Collections.reverse(Arrays.asList(items));

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName = ten_bai_hat.get(position);
                final ArrayList<String> ten_bh = new ArrayList<>();
                Collections.addAll(ten_bh , items);

                startActivity(new Intent(getContext(), PlayActivity.class)
                        .putExtra("songs", mySongs )
                        .putExtra("ten_bh", ten_bh )
                        .putExtra("songname" , songName )
                        .putExtra("vi_tri" , position) );
            }
        });

        dong_bo_database();
    } //        end hien nhac

    private void dong_bo_database(){
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                // huy
                for (int i = 0; i < items.length; i++) {

                    BaiHat baiHat = new BaiHat(items[i] );
                    long result =  baiHatDAO.insert_Loai( baiHat );

                    Log.e("---------------- " , String.valueOf(result ) );
                    if (result == -1) {
//                baiHatDAO.insert_BAI_HAT(baiHat);
                        Log.e("---------------- " + i , "Insert Database ");
                    }

//            Log.e("---------------- " + i , items[i].toString() );
                }
                return null;
            }
        };
        asyncTask.execute();
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.length;
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
            View view= getLayoutInflater().inflate(R.layout.item_bh_home, null);
            TextView txtSong = view.findViewById(R.id.txtSong);
            txtSong.setSelected(true);
            txtSong.setText(items[position]);
            return view;
        }
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    public void runtimePermission(){
        Dexter.withContext(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        hien_nhac();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();

    }

}