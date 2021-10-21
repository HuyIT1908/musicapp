package com.example.musicapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.DAO.DanhSachChiTietDAO;
import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PlayActivity extends AppCompatActivity {

    Button btnplay, btnnexxt, btnprev, btnff, btnfr;
    TextView txtsname, txtsstart, txtsstop;
    SeekBar seekmusic;
    //    BarVisualizer visualizer;
    ImageView imageView;

    String sname;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    List<File> mySongs;
    Thread updateseekbar;
    Boolean lap_lai = false;
    Boolean ngau_nhien = false;
    List<String> items;
    List<String> list_Ten_BH;
    DanhSachChiTietDAO danhSachChiTietDAO;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
//        if(visualizer != null){
//            visualizer.release();
//        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnplay = findViewById(R.id.playbtn);
        btnnexxt = findViewById(R.id.btnnext);
        btnprev = findViewById(R.id.btnprev);
        btnff = findViewById(R.id.btnff);
        btnfr = findViewById(R.id.btnfr);

        txtsname = findViewById(R.id.txtsn);
        txtsstart = findViewById(R.id.txtsstart);
        txtsstop = findViewById(R.id.txtsstop);
        seekmusic = findViewById(R.id.seekbar);
//        visualizer = findViewById(R.id.blast);
        imageView = findViewById(R.id.imgeview);
        items = new ArrayList<>();
        list_Ten_BH = new ArrayList<>();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent j = getIntent();
        Bundle bundle = j.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        list_Ten_BH = (ArrayList) bundle.getParcelableArrayList("ten_bh");
        String songName = j.getStringExtra("songname");

        List<File> fileList = new ArrayList<>();
        List<File> ten_moi = new ArrayList<>();
//        for (int i = 0; i < mySongs.size(); i++) {
//            String ten = mySongs.get(i).getName().toString().replace(".mp3", "");
//
//            Log.e("-------------------" , "......... " + ten + "\n" +
//            + fileList.size() + "\n" );
//
//        }
        int dem = 0;
//        Collections.reverse(list_Ten_BH);
        for (File i : mySongs) {
            String ten = i.getName().toString().replace(".mp3", "");
//            Log.e("-------------------" , "......... " + ten + "\n" +
//                    + dem + "\n" );
            dem++;
            for (String k : list_Ten_BH) {
                if (ten.equals( k ) ) {
                    fileList.add(i);
                }
            }
        }
//        dem = 0;
        Collections.reverse(fileList);
//        for (String i : list_Ten_BH) {
//            if ( i.equals(fileList.get(dem).getName()) ){
//                ten_moi.add(k);
//            }
//        }
        for (File i: fileList){
            Log.e("----------------", " ................. " + "\n" + i.getName()
                    + " --- " + fileList.size() + "\n" + list_Ten_BH.size());
        }
        mySongs.clear();
        mySongs.addAll(fileList);

        position = get_Index_BH(songName);

        txtsname.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        sname = mySongs.get(position).getName();

        txtsname.setText(sname);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        btnnexxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if ( position == (mySongs.size() - 1) ){
                    position = 0;
                } else {
                    position = ((position + 1) % mySongs.size());
                }

                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);

                sname = mySongs.get(position).getName();
                txtsname.setText(sname);

                mediaPlayer.start();
                btnplay.setBackgroundResource(R.drawable.icon_pause);
                startAnimation(imageView);
//                tg kt bh
                txtsstop.setText(convert_TIME(mediaPlayer.getDuration()));
                seekmusic.setMax(mediaPlayer.getDuration());
                seekmusic.setProgress(0);

                int audiosessionId = mediaPlayer.getAudioSessionId();

                if (audiosessionId != -1) {
//                    visualizer.setAudioSessionId(audiosessionId);
                }
            }
        });

        updateseekbar = new Thread() {
            @Override
            public void run() {
                int totaDuration = mediaPlayer.getDuration();
                int currentposition = 0;

                while (currentposition < totaDuration) {
                    try {
                        sleep(500);
                        currentposition = mediaPlayer.getCurrentPosition();
                        seekmusic.setProgress(currentposition);

                        su_kien();
//                        Log.e("---------------", "cap nhat seeber ...........");
                    } catch (InterruptedException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        seekmusic.setMax(mediaPlayer.getDuration());
        updateseekbar.start();
        seekmusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.design_default_color_primary), PorterDuff.Mode.MULTIPLY);
        seekmusic.getThumb().setColorFilter(getResources().getColor(R.color.design_default_color_primary), PorterDuff.Mode.SRC_IN);

        seekmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String endTime = createTime(mediaPlayer.getDuration());
//        thời gian kết thức music
        txtsstop.setText(convert_TIME(mediaPlayer.getDuration()));

        final Handler handler = new Handler();
        final int delay = 1;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = "0" + createTime(mediaPlayer.getCurrentPosition());
                String kt = convert_TIME(mediaPlayer.getDuration());
//                cập nhật thời gian phát bài hát
                txtsstart.setText(currentTime);
//                Log.e("------------------" , "thay doi ..........." + currentTime + " -- "
//                + kt + "................... " + convert_TIME(seekmusic.getProgress() ));

                handler.postDelayed(this, delay);
            }
        }, delay);


        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    btnplay.setBackgroundResource(R.drawable.icon_play);
                    mediaPlayer.pause();
                } else {
                    btnplay.setBackgroundResource(R.drawable.icon_pause);
                    mediaPlayer.start();
                }
            }
        });
//next list

        int audiosessionId = mediaPlayer.getAudioSessionId();

        if (audiosessionId != -1) {
//            visualizer.setAudioSessionId(audiosessionId);
        }

        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                mediaPlayer.stop();
                mediaPlayer.release();

                if (position == 0){
                    position = mySongs.size() - 1;
                } else {
                    position = ((position - 1) < 0) ? (mySongs.size() - 1) : (position - 1);
                }

                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                sname = mySongs.get(position).getName();
                txtsname.setText(sname);
                mediaPlayer.start();
                btnplay.setBackgroundResource(R.drawable.icon_pause);
                startAnimation(imageView);

//                tg kt bh
                txtsstop.setText(convert_TIME(mediaPlayer.getDuration()));
                seekmusic.setMax(mediaPlayer.getDuration());
                seekmusic.setProgress(0);

                int audiosessionId = mediaPlayer.getAudioSessionId();

                if (audiosessionId != -1) {
//                    visualizer.setAudioSessionId(audiosessionId);
                }
            }
        });

        btnff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ngau_nhien == true) {
                    Toast.makeText(getApplicationContext(), "Đã tắt chế độ ngẫu nhiên"
                            , Toast.LENGTH_SHORT).show();
                    ngau_nhien = false;
                } else if (ngau_nhien == false) {
                    ngau_nhien = true;
                    Toast.makeText(getApplicationContext(), "Đã bật chế độ ngẫu nhiên"
                            , Toast.LENGTH_SHORT).show();
                }
//                Random random = new Random();
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                position = ((position + 3) % mySongs.size());
//
//                Uri u = Uri.parse(mySongs.get(position).toString());
//                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
//                sname = mySongs.get(position).getName();
//                txtsname.setText(sname);
//                mediaPlayer.start();
//                btnplay.setBackgroundResource(R.drawable.icon_pause);
//                startAnimation(imageView);
//                int audiosessionId = mediaPlayer.getAudioSessionId();
//                if (audiosessionId != -1) {
////                    visualizer.setAudioSessionId(audiosessionId);
//                }
            }
        });

//        btnff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mediaPlayer.isPlaying()){
//                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
//                }
//            }
//        });
        btnfr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Random random = new Random();
//                mediaPlayer.stop();
//                mediaPlayer.release();
//                position = ((position) % mySongs.size());
//
//                Uri u = Uri.parse(mySongs.get(position).toString());
//                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
//                sname = mySongs.get(position).getName();
//                txtsname.setText(sname);
//                mediaPlayer.start();
//                btnplay.setBackgroundResource(R.drawable.icon_pause);
//                startAnimation(imageView);
//                int audiosessionId = mediaPlayer.getAudioSessionId();
//                if (audiosessionId != -1) {
////                    visualizer.setAudioSessionId(audiosessionId);
//                }
                if (lap_lai == true) {
                    Toast.makeText(getApplicationContext(), "Đã tắt chế độ lặp lại"
                            , Toast.LENGTH_SHORT).show();
                    lap_lai = false;
                } else if (lap_lai == false) {
                    lap_lai = true;
                    Toast.makeText(getApplicationContext(), "Đã bật chế độ lặp lại"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
//        btnfr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mediaPlayer.isPlaying()){
//                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
//                }
//            }
//        });
    } // end OnCreate

    public void startAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }

    public String createTime(int duration) {
        String time = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        time += min + ":";
        if (sec < 10) {
            time += "0";
        }
        time += sec;
        return time;
    }

    private int get_Index_BH(String bh) {
//        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());

        List<String> items = new ArrayList<>();
        for (int i = 0; i < mySongs.size(); i++) {
            String ten_BH = mySongs.get(i).getName().toString().replace(".mp3", "");

            if (ten_BH.equals(bh)) {
                return i;
            }
        }
        return 0;
    }

    private String convert_TIME(int time) {
        NumberFormat f = new DecimalFormat("00");
        long timeMiniutes = TimeUnit.MILLISECONDS.toMinutes(time);
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(timeMiniutes);
        String result = f.format(timeMiniutes) + ":" + f.format(timeSeconds);
        return result;
    }

//    private List<File> get_prorp_file(){
//        final List<File>[] phu = new List<File>[];
//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
//                phu[0] = mySongs;
//                return null;
//            }
//        };
//        asyncTask.execute();
//    }

    private void su_kien() {
        //        khi kết thúc bài hát
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                btnnexxt.performClick();
//                txtsstop.setText(convert_TIME(mediaPlayer.getDuration()));
//                seekmusic.setMax(mediaPlayer.getDuration());
//                seekmusic.setProgress(0);

                Log.e("-----------------", " ket thuc chuyen bai ..........." + lap_lai);

                // Chức năng lặp lại ( chưa hoàn thiện )
                if (lap_lai == true) {
                    Toast.makeText(getApplicationContext(), "Lặp lại bài hát hiện tại"
                            , Toast.LENGTH_SHORT).show();

                    mediaPlayer.stop();
                    mediaPlayer.release();

                    Uri u = Uri.parse(mySongs.get(position).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), u);

                    sname = mySongs.get(position).getName();
                    txtsname.setText(sname);

                    mediaPlayer.start();
                    btnplay.setBackgroundResource(R.drawable.icon_pause);
                    startAnimation(imageView);

                    //                tg kt bh
                    txtsstop.setText(convert_TIME(mediaPlayer.getDuration()));
                    seekmusic.setMax(mediaPlayer.getDuration());
                    seekmusic.setProgress(0);

                } else if (ngau_nhien == true) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    Random rand = new Random();
                    int pos = rand.nextInt(mySongs.size());
                    Log.e("----------------", "........... " + mySongs.size());

                    Uri u = Uri.parse(mySongs.get(pos).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), u);

                    sname = mySongs.get(pos).getName();
                    txtsname.setText(sname);

                    mediaPlayer.start();
                    btnplay.setBackgroundResource(R.drawable.icon_pause);
                    startAnimation(imageView);

                    //                tg kt bh
                    txtsstop.setText(convert_TIME(mediaPlayer.getDuration()));
                    seekmusic.setMax(mediaPlayer.getDuration());
                    seekmusic.setProgress(0);
                } else {
                    btnnexxt.performClick();
                    txtsstop.setText(convert_TIME(mediaPlayer.getDuration()));
                    seekmusic.setMax(mediaPlayer.getDuration());
                    seekmusic.setProgress(0);
                }

            }
        });
    }

}