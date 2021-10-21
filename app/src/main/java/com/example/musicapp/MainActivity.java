package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.musicapp.HomeActivity;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        xin quyền truy cập
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Xin quyền truy cập ảnh trong máy
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    999);
            System.exit(0);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){

            chuyen_Home();
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
////        Xin quyền lại
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            // xin quyen
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    999);
//            Toast.makeText(getApplicationContext(), "ban chua cap quyen",
//                    Toast.LENGTH_SHORT).show();
//        } else {
//            // goi lai ham lay danh ba
//            // moi nhan lan nua
//            // lay danh ba
//
//            Toast.makeText(getApplicationContext(), "ok hazz",
//                    Toast.LENGTH_SHORT).show();
//            chuyen_Home();
//        }
//    }

    private void chuyen_Home(){
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent intent = new Intent(MainActivity.this , HomeActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 1000);
        Intent intent = new Intent(MainActivity.this , HomeActivity.class);
        startActivity(intent);
        finish();
    }

}