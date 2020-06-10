package com.coolweather.ai_lamp.music;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.ai_lamp.R;
import com.coolweather.ai_lamp.utils.StatusBarUtils;

import java.util.Timer;
import java.util.TimerTask;

public class MusicActivity extends AppCompatActivity {
    // 用来获取音乐的相关信息

    private static final String TAG = "MainActivity";
    private TextView textViewNowPro;
    private TextView textViewAllPro;
    private ImageView imageViewArtist;
    private TextView textViewArtist;
    private TextView textViewAlbum;
    private TextView textViewTitle;
    private ImageView imageViewReturn;
    private ImageView imgPlaySong;  //播放与暂停按钮
    private ImageView imgPreviousSong;  //切换上一首
    private ImageView imgNextSong;  //切换下一首

    SeekBar seekBar;
    private Song song;
    private boolean isChanging = false;
    ObjectAnimator animator;

    private int position;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);


        //隐藏actionBar
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        StatusBarUtils.setStatusBarColor(MusicActivity.this, R.color.colorWhite);
        StatusBarUtils.setLightStatusBar(MusicActivity.this, true, true);  //状态栏字体颜色-黑


        // 获取传来的歌曲
        song = (Song)getIntent().getSerializableExtra("song");
        position = getIntent().getIntExtra("index", -1);
        Log.d(TAG, "onCreate: index="+position+"----song="+song.toString());


        textViewNowPro = findViewById(R.id.textViewNowPro);
        textViewAllPro = findViewById(R.id.textViewAllPro);
        textViewAlbum = findViewById(R.id.tvAlbum);
        textViewArtist = findViewById(R.id.tvArtist);
        imageViewArtist = findViewById(R.id.imageViewArtist);
        textViewTitle = findViewById(R.id.tvTitle);
        imgPlaySong = findViewById(R.id.imgPlaySong);
        imgPreviousSong = findViewById(R.id.imgPreviousSong);
        imgNextSong = findViewById(R.id.imgNextSong);
        seekBar = findViewById(R.id.seekBar);
        imageViewReturn = findViewById(R.id.imageViewReturn);

        // 设置旋转动画
        animator = ObjectAnimator.ofFloat(imageViewArtist, "rotation", 0f, 360f);
        animator.setDuration(50000);// 持续时间 - 决定了播放的快慢
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);


        // 监听进度条更新事件
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewNowPro.setText(getTime(progress));
                if (isChanging) {// 如果在拖动
                    Player.moveTo(progress);
                    if (progress == seekBar.getMax()) {//  拖动到 播放完了
                        pause();
                        imgPlaySong.setImageResource(R.mipmap.play);
                    }
                    return;
                }
                if (progress == seekBar.getMax()) {//  播放完了
                    pause();
                    imgPlaySong.setImageResource(R.mipmap.play);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {// 点击进度条时 开始拖动前
                isChanging = true;
                pause();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {// 放开进度条后 开始拖动后
                isChanging = false;
                play();
            }
        });

        // 监听按钮play/pause
        imgPlaySong.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (!Player.isPlaying()){// 是否在播放
                    play();
                }
                else{
                    pause();
                }
            }
        });

        //上一曲
        imgPreviousSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=0){
                    song = SongList.songs.get(--position);
                    setSong(song);
                    Player.setPlayPath(MusicActivity.this, song.getFileName());
                    Player.play();
                    imgPlaySong.setImageResource(R.mipmap.pause);
                }else {
                    Toast.makeText(MusicActivity.this, "已是第1首歌曲！", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //下一曲
        imgNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!=SongList.songs.size()-1){
                    song = SongList.songs.get(++position);
                    setSong(song);
                    Player.setPlayPath(MusicActivity.this, song.getFileName());
                    Player.play();
                    imgPlaySong.setImageResource(R.mipmap.pause);
                }else {
                    Toast.makeText(MusicActivity.this, "已是最后一首歌曲！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        new Timer().schedule(new TimerTask() {// 不间断的更新播放进度
            @Override
            public void run() {
                if (!isChanging)
                    seekBar.setProgress(Player.getCurrentPosition());// 实时更新进度
            }
        }, 0, 10);

        //返回
        imageViewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回选择列表
                //startActivity(new Intent(MusicActivity.this, MusicListActivity.class));
                Intent intent = new Intent();
                intent.putExtra("song", song);
                setResult(RESULT_OK,intent);

                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();
        setSong(song);

        if(Player.isHasSource()){
            play();
        }


    }

    /**
     * 将毫秒转换成00:00
     * @param progress
     * @return
     */
    public String getTime(int progress) {
        int sec = progress / 1000; // 获取秒
        int min = sec / 60; // 获取分
        sec = sec % 60;// 获取剩余秒数 (分:秒)
        return String.format("%02d:%02d", min, sec);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pause() {// 暂停
        Player.pause();
        if (!isChanging)// 拖动的时候仍然播放动画
            animator.pause();
        imgPlaySong.setImageResource(R.mipmap.play);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void play() {// 播放
        if(Player.isHasSource()){  //有音源
            Player.play();
        }else {  //无音源
            Player.setPlayPath(MusicActivity.this,song.getFileName());
            Player.play();
        }


        if (!animator.isStarted()){
            animator.start();
        } else{
            animator.resume();
        }
        imgPlaySong.setImageResource(R.mipmap.pause);
    }

    /**
     * 设置音乐文件
     *
     * @param song
     */
    public void setSong(Song song) {
        seekBar.setMax(song.getDuration());
        seekBar.setProgress(0);
        textViewAllPro.setText(getTime(seekBar.getMax()));
        // 获取音乐相关信息
        textViewTitle.setText(song.getTitle());
        textViewAlbum.setText(song.getAlbum());
        textViewArtist.setText(song.getArtist());
        if (song.getBitmap() != null)
            imageViewArtist.setImageBitmap(song.getBitmap());
    }

    /**
     * 监听Back键按下事件,方法1:
     * super.onBackPressed()会自动调用finish()方法执行 onDestroy(),关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("song", song);
        setResult(RESULT_OK,intent);

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
