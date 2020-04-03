package com.coolweather.ai_lamp.music;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.coolweather.ai_lamp.R;
import com.coolweather.ai_lamp.plans.PlanInfoActivity;
import com.coolweather.ai_lamp.tools.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity {

    private ListView listView;  //歌曲列表控件
    private ImageView imageViewPlaying;  //底部播放按钮
    private ImageView imageViewSelected;  //底部歌曲封面图
    private TextView textViewSelectedTitle;  //底部歌曲名
    private TextView textViewSelectedArtist;  //底部歌手名
    private RelativeLayout relativeLayoutEndItem;  //底部-正在播放的歌曲信息
    private ImageButton btnBack; //顶部返回按钮

    private List<Song> songList = new ArrayList<>();  //歌曲集合


    private static Song selectSong;  //被选中的歌曲（正在播放或暂停）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        
        if(ContextCompat.checkSelfPermission(MusicListActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MusicListActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            Log.d(TAG, "onCreate: 无权限");
        }

        StatusBarUtils.setStatusBarColor(MusicListActivity.this, R.color.colorWhite);
        StatusBarUtils.setLightStatusBar(MusicListActivity.this, true, true);  //状态栏字体颜色-黑

        init();

        initMusic();
        //songList = MusicUtils.getAllMusics(this);

        Log.i(TAG, "onCreate: " + songList.size());
        Log.i(TAG, "onCreate: " + songList);

        listView.setAdapter(new SongAdapter(this, R.layout.music_list_item, songList));
        initSelectedSong();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = (Song) parent.getItemAtPosition(position);

                if(!song.equals(selectSong)){// 如果当前播放的不是这首歌
                    Player.setPlayPath(MusicListActivity.this, song.getFileName());
                    changeSelectSong(song);
                    playSelectSong();
                }else{
                    Log.d(TAG, "isPlaying this music");
                    if(!Player.isPlaying()){// 没有正在播放
                        playSelectSong();// 播放
                    }else{// 跳转到详细页面
                        // 跳转到主页面
                        jumpMain();
                    }
                }
            }
        });

        relativeLayoutEndItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectSong != null){
                    // 跳转到主页面
                    jumpMain();
                }
            }
        });

        imageViewPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectSong == null){
                    initSelectedSong();// 尝试初始化
                }
                if(Player.isPlaying()){
                    pauseSelectSong();
                }else{
                    playSelectSong();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:{
                Song song = (Song)data.getSerializableExtra("song");
                changeSelectSong(song);

                Log.d(TAG, "onActivityResult: "+song.toString());
                break;
            }
        }
    }

    /**
     * 跳转到歌曲详情界面：MusicActivity
     */
    private void jumpMain(){
        if(selectSong != null) {
            // 跳转到主页面
            Intent intent = new Intent(MusicListActivity.this, MusicActivity.class);

            intent.putExtra("song", selectSong);
            intent.putExtra("index", songList.indexOf(selectSong));
            startActivityForResult(intent, 1);
        }
    }

    /**
     * 初始化选中歌曲:selectedSong
     */
    public void initSelectedSong(){
        if(!songList.isEmpty()){
            changeSelectSong(songList.get(0));
        }
    }

    /**
     * 将选中歌曲改为参数song
     * @param song
     */
    public void changeSelectSong(Song song) {
        if (song != null) {
            selectSong = song;
            if(song.getBitmap() != null){  //切换底部歌曲封面图
                imageViewSelected.setImageBitmap(song.getBitmap());
            }
            textViewSelectedTitle.setText(song.getTitle());  //改歌名
            textViewSelectedArtist.setText(song.getArtist());  //改歌手

            //改变底部播放按钮状态
            if(Player.isPlaying()){
                imageViewPlaying.setImageResource(R.mipmap.pause);
            }else {
                imageViewPlaying.setImageResource(R.mipmap.play);
            }
        }
    }

    public void playSelectSong() {
        if (selectSong != null) {
            if(!Player.isHasSource()){
                Player.setPlayPath(MusicListActivity.this, selectSong.getFileName());
            }
            Player.play();
            imageViewPlaying.setImageResource(R.mipmap.pause);
        }
    }

    public void pauseSelectSong() {
        if (selectSong != null) {
            if(Player.isPlaying()){
                Log.d(TAG, "pauseSelectSong: isPlaying="+Player.isPlaying());
                Player.pause();

            }

            imageViewPlaying.setImageResource(R.mipmap.play);
        }
    }

    private static final String TAG = "MusicListActivity";

    private void init() {
        listView = findViewById(R.id.listViewMusicList);
        imageViewPlaying = findViewById(R.id.imageViewPlaying);
        imageViewSelected = findViewById(R.id.imageViewSelected);
        textViewSelectedTitle = findViewById(R.id.textViewSelectedTitle);
        textViewSelectedArtist = findViewById(R.id.textViewSelectedArist);
        relativeLayoutEndItem = findViewById(R.id.relativeLayoutEndItem);
        btnBack = findViewById(R.id.btnMusicListBack);

    }

    private void initMusic(){
        songList.add(MusicUtils.getSong(MusicListActivity.this, "music_1.mp3"));
        songList.add(MusicUtils.getSong(MusicListActivity.this, "music_2.mp3"));
        songList.add(MusicUtils.getSong(MusicListActivity.this, "music_3.mp3"));
        songList.add(MusicUtils.getSong(MusicListActivity.this, "music_4.mp3"));
        songList.add(MusicUtils.getSong(MusicListActivity.this, "music_5.mp3"));
        songList.add(MusicUtils.getSong(MusicListActivity.this, "music_6.mp3"));
        songList.add(MusicUtils.getSong(MusicListActivity.this, "music_7.mp3"));
        songList.add(MusicUtils.getSong(MusicListActivity.this, "music_8.mp3"));
        songList.add(MusicUtils.getSong(MusicListActivity.this, "music_9.mp3"));
        SongList.setSongs(songList);
    }


    /**
     * 歌曲的list适配器
     */
    class SongAdapter extends ArrayAdapter<Song> {
        private int resourceId;

        public SongAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
            super(context, resource, objects);
            this.resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(resourceId, null, false);
            Song song = getItem(position);
            TextView index = view.findViewById(R.id.tvItemIndex);
            index.setText(position + 1 + "");
            TextView title = view.findViewById(R.id.tvItemTitle);
            title.setText(song.getTitle());
            TextView artist = view.findViewById(R.id.tvItemArtist);
            artist.setText(song.getArtist());
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Player.pause();
    }
}
