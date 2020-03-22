package com.coolweather.ai_lamp.light;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.ai_lamp.R;

public class MyLeight extends AppCompatActivity {

    TextView tv_leight;
    ImageView img;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b_zidong;
    SeekBar sb;

    int leight_b=0;
    int leight_l=0;
    private String TAG="MyLeight: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_leight);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        init();
        initData();

    }


    private void init() {
        tv_leight = findViewById(R.id.tv_light_type);
        img = findViewById(R.id.iv_light_img);
        b1 = findViewById(R.id.bt_leight_mo1);
        b2 = findViewById(R.id.bt_leight_mo2);
        b3 = findViewById(R.id.bt_leight_mo3);
        b4 = findViewById(R.id.bt_leight_mo4);
        b_zidong = findViewById(R.id.bt_leight_zidong);
        sb = findViewById(R.id.sb_leight_len);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leight_b==1){
                    leight_b = 0;
                    img.setImageDrawable(getResources().getDrawable(R.drawable.leight_0, null));
                    tv_leight.setText("已关闭");
                    setLeight(0);
                }else {
                    leight_b = 1;
                    img.setImageDrawable(getResources().getDrawable(R.drawable.leight_1, null));
                    tv_leight.setText("已开启");
                    setLeight(1);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leight_b==1){
                    Toast.makeText(MyLeight.this, "灯光亮度：25%", Toast.LENGTH_SHORT).show();
                    setBrightness(25);
                }else {
                    Toast.makeText(MyLeight.this, "请先打开灯", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leight_b==1){
                    Toast.makeText(MyLeight.this, "灯光亮度：50%", Toast.LENGTH_SHORT).show();
                    setBrightness(50);
                }else {
                    Toast.makeText(MyLeight.this, "请先打开灯", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leight_b==1){
                    Toast.makeText(MyLeight.this, "灯光亮度：75%", Toast.LENGTH_SHORT).show();
                    setBrightness(75);
                }else {
                    Toast.makeText(MyLeight.this, "请先打开灯", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leight_b==1){
                    Toast.makeText(MyLeight.this, "灯光亮度：100%", Toast.LENGTH_SHORT).show();
                    setBrightness(100);
                }else {
                    Toast.makeText(MyLeight.this, "请先打开灯", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress/5!=leight_l){
                    leight_l = progress/5;
                    setBrightness(progress-progress%5);
                    seekBar.setProgress(progress-progress%5);
                }
                //主要是用于监听进度值的改变
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                //监听用户开始拖动进度条的时候
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                //监听用户结束拖动进度条的时候
                if(leight_b==1){
                    Toast.makeText(MyLeight.this, "灯光亮度："+(progress-progress%5)+"%", Toast.LENGTH_SHORT).show();
                }else {
                    seekBar.setProgress(0);
                    Toast.makeText(MyLeight.this, "请先打开灯", Toast.LENGTH_SHORT).show();

                }
            }
        });

        b_zidong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leight_b==1){
                    Toast.makeText(MyLeight.this, "已开启自动模式", Toast.LENGTH_SHORT).show();
                    zidng();
                }else{
                    Toast.makeText(MyLeight.this, "请先打开灯", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //初始化亮度
    private void initData() {

    }
    //灯开关
    private void setLeight(int i) {

    }
    //自动模式
    private void zidng() {

    }
    //设置亮度
    private void setBrightness(int x) {
        sb.setProgress(x);
        Log.d(TAG, ""+ x);
    }
}
