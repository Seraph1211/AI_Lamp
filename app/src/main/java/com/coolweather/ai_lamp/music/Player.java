package com.coolweather.ai_lamp.music;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class Player {
    private static final MediaPlayer mediaPlayer = new MediaPlayer();
    private static boolean hasSource = false;  //是否有音源

    /**
     *  是否正在播放
     * @return
     */
    public static boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    /**
     *  暂停
     */
    public static void pause(){
        mediaPlayer.pause();
    }

    /**
     *  播放
     */
    public static void play(){
        mediaPlayer.start();
    }

    public static void prepare() throws IOException {
        mediaPlayer.prepare();
    }

    /**
     * 停止
     */
    public static void stop(){
        mediaPlayer.stop();
       //mediaPlayer.release();

    }

    /**
     *  重新播放
     */
    public static void replay(){
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }

    public static int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }
    /**
     *  移动到音乐的某处，然后播放
     * @param pos
     */
    public static void moveTo(int pos){
        mediaPlayer.pause();
        mediaPlayer.seekTo(pos);
        mediaPlayer.start();
    }

    /**
     *  设置播放文件
     * @param path 文件路径
     */
    public static void setPlayPath(String path){
        try {
            // 设置类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.reset();
            // 设置文件源
            mediaPlayer.setDataSource(path);

            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPlayPath(Context context, String fileName){
        try {
            // 设置类型
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.reset();
            // 设置文件源
            AssetFileDescriptor fd = context.getAssets().openFd(fileName);
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mediaPlayer.prepare();

            hasSource = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean isHasSource(){
        return hasSource;
    }


}
