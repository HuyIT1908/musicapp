package com.example.musicapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    Button btnplay, btnnexxt, btnprev, btnff, btnfr;
    TextView txtsname, txtsstart, txtsstop;
    SeekBar seekmusic;
    BarVisualizer visualizer;

    String sname;
    public static final  String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        btnplay= findViewById(R.id.playbtn);
        btnnexxt= findViewById(R.id.btnnext);
        btnprev= findViewById(R.id.btnprev);
        btnff= findViewById(R.id.btnff);
        btnfr= findViewById(R.id.btnfr);

        txtsname = findViewById(R.id.txtsn);
        txtsstart = findViewById(R.id.txtsstart);
        txtsstop = findViewById(R.id.txtsstop);
        seekmusic = findViewById(R.id.seekbar);
        visualizer = findViewById(R.id.blast);

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songName = i.getStringExtra("songname") ;
        position = bundle.getInt("pos",0);
        txtsname.setSelected(true);
        Uri uri =Uri.parse(mySongs.get(position).toString());
        sname= mySongs.get(position).getName();
        txtsname.setText(sname);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    btnplay.setBackgroundResource(R.drawable.icon_play);
                    mediaPlayer.pause();
                }
                else{
                    btnplay.setBackgroundResource(R.drawable.icon_pause);
                    mediaPlayer.start();
                }
            }
        });

    }
}