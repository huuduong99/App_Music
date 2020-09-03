package com.example.appmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmusic.Adapter.ViewPagerPlaylistNhac;
import com.example.appmusic.Fragment.Fragment_Dia_Nhac;
import com.example.appmusic.Fragment.Fragment_Play_Danh_Sach_Bai_Hat;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {
    Toolbar toolbarPlayNhac;
    TextView txtTimeSong,txtTotalTimeSong;
    SeekBar sktime;
    ImageButton ibtnPlay,ibtnRepeat,ibtnPre,ibtnNext,ibtnRandom;
    ViewPager viewPagerPlayNhac;
    public static ArrayList<BaiHat> mangBaiHat = new ArrayList<>();
    public static ViewPagerPlaylistNhac adapterNhac;
    Fragment_Dia_Nhac fragment_dia_nhac;
    Fragment_Play_Danh_Sach_Bai_Hat fragment_play_danh_sach_bai_hat;
    MediaPlayer mediaPlayer;
    int position = 0;
    boolean repeat = false;
    boolean checkrandom =false;
    boolean next = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GetDataFromIntent();
        Init();
        enventClick();


    }

    private void enventClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapterNhac.getItem(1) != null){
                    if(mangBaiHat.size() > 0){
                        fragment_dia_nhac.Playnhac(mangBaiHat.get(0).getHinhbaihat());
                        handler.removeCallbacks(this);
                    }else {
                        handler.postDelayed(this,300);
                    }
                }
            }
        },500);
        ibtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    ibtnPlay.setImageResource(R.drawable.iconplay);
                }else {
                    mediaPlayer.start();
                    ibtnPlay.setImageResource(R.drawable.iconpause);
                }

            }
        });
        ibtnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(repeat == false){
                    if(checkrandom == true){
                        checkrandom = false;
                        ibtnRepeat.setImageResource(R.drawable.iconsyned);
                        ibtnRandom.setImageResource(R.drawable.iconsuffle);
                    }
                    ibtnRepeat.setImageResource(R.drawable.iconsyned);
                    repeat = true;
                }else {
                    ibtnRepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
            }
        });
        ibtnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkrandom == false){
                    if(repeat == true){
                        repeat = false;
                        ibtnRandom.setImageResource(R.drawable.iconshuffled);
                        ibtnRepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    ibtnRandom.setImageResource(R.drawable.iconshuffled);
                    checkrandom = true;
                }else {
                    ibtnRandom.setImageResource(R.drawable.iconsuffle);
                    checkrandom = false;
                }
            }
        });
        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        ibtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mangBaiHat.size() > 0){
                    if(mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(position < mangBaiHat.size()){
                        ibtnPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if(repeat == true){
                            if(position == 0){
                                position=mangBaiHat.size();
                            }
                            position -=1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangBaiHat.size());
                            if(index == position){
                                position = index -1;
                            }
                            position = index;
                        }
                        if(position  > mangBaiHat.size()-1){
                            position = 0;
                        }
                        new PlayMp3().execute(mangBaiHat.get(position).getLinkbaihat());
                        fragment_dia_nhac.Playnhac(mangBaiHat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangBaiHat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                ibtnPre.setClickable(false);
                ibtnNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ibtnPre.setClickable(true);
                        ibtnNext.setClickable(true);
                    }
                },5000);
            }
        });
        ibtnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mangBaiHat.size() > 0){
                    if(mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(position < mangBaiHat.size()){
                        ibtnPlay.setImageResource(R.drawable.iconpause);
                        position--;
                        if(position < 0){
                            position = mangBaiHat.size() -1 ;
                        }
                        if(repeat == true){
                            position +=1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangBaiHat.size());
                            if(index == position){
                                position = index -1;
                            }
                            position = index;
                        }

                        new PlayMp3().execute(mangBaiHat.get(position).getLinkbaihat());
                        fragment_dia_nhac.Playnhac(mangBaiHat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangBaiHat.get(position).getTenbaihat());
                        UpdateTime();
                    }
                }
                ibtnPre.setClickable(false);
                ibtnNext.setClickable(false);
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ibtnPre.setClickable(true);
                        ibtnNext.setClickable(true);
                    }
                },5000);

            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangBaiHat.clear();
        if(intent != null){
            if(intent.hasExtra("cakhuc")){
                BaiHat baiHat = intent.getParcelableExtra("cakhuc");
                mangBaiHat.add(baiHat);
            }
            if(intent.hasExtra("cacbaihat")){
                ArrayList<BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
                mangBaiHat = baiHatArrayList;
            }
        }

    }

    private void Init() {
        toolbarPlayNhac = findViewById(R.id.toolbarPlayNhac);
        txtTimeSong = findViewById(R.id.textViewTimeSong);
        txtTotalTimeSong = findViewById(R.id.textViewTotalTimeSong);
        sktime = findViewById(R.id.seekBarSong);
        ibtnPlay = findViewById(R.id.imageButtonPlay);
        ibtnRepeat = findViewById(R.id.imageButtonRepeat);
        ibtnPre = findViewById(R.id.imageButtonPreview);
        ibtnRandom = findViewById(R.id.imageButtonShuffle);
        ibtnNext = findViewById(R.id.imageButtonNext);
        viewPagerPlayNhac = findViewById(R.id.viewpagerPlayNhac);

        setSupportActionBar(toolbarPlayNhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPlayNhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                mediaPlayer.stop();
                mangBaiHat.clear();
            }
        });
        fragment_dia_nhac = new Fragment_Dia_Nhac();
        fragment_play_danh_sach_bai_hat =new Fragment_Play_Danh_Sach_Bai_Hat();
        toolbarPlayNhac.setTitleTextColor(Color.WHITE);
        adapterNhac = new ViewPagerPlaylistNhac(getSupportFragmentManager());
        adapterNhac.AddFragment(fragment_play_danh_sach_bai_hat);
        adapterNhac.AddFragment(fragment_dia_nhac);
        viewPagerPlayNhac.setAdapter(adapterNhac);
        fragment_dia_nhac = (Fragment_Dia_Nhac) adapterNhac.getItem(1);

        if(mangBaiHat.size() > 0){
            getSupportActionBar().setTitle(mangBaiHat.get(0).getTenbaihat());
            new PlayMp3().execute(mangBaiHat.get(0).getLinkbaihat());
            ibtnPlay.setImageResource(R.drawable.iconpause);
        }
    }

    class PlayMp3 extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });

            mediaPlayer.setDataSource(baihat);
            mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTimeSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration());
    }

    private  void UpdateTime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    sktime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        },300);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(next == true){
                    if(position < mangBaiHat.size()){
                        ibtnPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if(repeat == true){
                            if(position == 0){
                                position=mangBaiHat.size();
                            }
                            position -=1;
                        }
                        if(checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangBaiHat.size());
                            if(index == position){
                                position = index -1;
                            }
                            position = index;
                        }
                        if(position  > mangBaiHat.size()-1){
                            position = 0;
                        }
                        new PlayMp3().execute(mangBaiHat.get(position).getLinkbaihat());
                        fragment_dia_nhac.Playnhac(mangBaiHat.get(position).getHinhbaihat());
                        getSupportActionBar().setTitle(mangBaiHat.get(position).getTenbaihat());
                    }
                    ibtnPre.setClickable(false);
                    ibtnNext.setClickable(false);
                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ibtnPre.setClickable(true);
                            ibtnNext.setClickable(true);
                        }
                    },5000);
                    next = false;
                    handler1.removeCallbacks(this);

                }else {
                    handler1.postDelayed(this,1000);
                }

            }
        },1000);
    }

}
