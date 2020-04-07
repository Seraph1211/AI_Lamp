package com.coolweather.ai_lamp.lamp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.coolweather.ai_lamp.R;
import com.coolweather.ai_lamp.tools.StatusBarUtils;

import java.util.logging.LoggingMXBean;

public class LampActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btnBack;  //返回键
    private TextView tvBrightness;  //灯光亮度
    private SeekBar sbLight;  //灯光亮度控制器
    private ImageView imgPower;  //台灯开关按钮
    private ImageView imgColdLight;  //冷光模式按钮
    private ImageView imgSubduedLight; //柔光模式按钮
    private ImageView imgLamp;  //img台灯
    private TextView tvPower; //text电源
    private TextView tvColdLight;  //text冷光
    private TextView tvSubduedLight;  //text柔光

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);

        StatusBarUtils.setStatusBarColor(LampActivity.this, R.color.colorWhite);
        StatusBarUtils.setLightStatusBar(LampActivity.this, true, true);  //状态栏字体颜色-黑

        initView();
        initLamp();
    }

    public void initView(){
        btnBack = findViewById(R.id.btnLampBack);
        tvBrightness = findViewById(R.id.tvLight);
        sbLight = findViewById(R.id.sbLight);
        imgPower = findViewById(R.id.imgPower);
        imgColdLight = findViewById(R.id.imgColdLight);
        imgSubduedLight = findViewById(R.id.imgSubduedLight);
        imgLamp = findViewById(R.id.imgLamp);
        tvPower = findViewById(R.id.tvPower);
        tvColdLight = findViewById(R.id.tvColdLight);
        tvSubduedLight = findViewById(R.id.tvSubduedLight);

        btnBack.setOnClickListener(this);
        imgColdLight.setOnClickListener(this);
        imgPower.setOnClickListener(this);
        imgSubduedLight.setOnClickListener(this);

        sbLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvBrightness.setText(progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbLight.setProgress(50);
    }

    /**
     * 初始化台灯的状态
     */
    public void initLamp(){
        if(MyLamp.isTurnOn){  //台灯是打开的
            lampTurnOn();
        }else {
            lampTurnOff();
        }
    }

    /**
     * 开灯
     */
    public void lampTurnOn(){
        MyLamp.isTurnOn = true;
        imgLamp.setImageResource(R.drawable.lamp_on);
        imgPower.setBackground(ContextCompat.getDrawable(LampActivity.this, R.drawable.bg_circle_fad9a7));
        imgPower.setImageResource(R.mipmap.power_on);
        tvPower.setTextColor(Color.parseColor("#000000"));
        if(MyLamp.pattern==1){  //冷光模式
            imgColdLight.setBackground(ContextCompat.getDrawable(LampActivity.this, R.drawable.bg_circle_fad9a7));
            imgColdLight.setImageResource(R.mipmap.pattern_cold_light_on);
            tvColdLight.setTextColor(Color.parseColor("#000000"));
        }
        if(MyLamp.pattern==0){  //柔光模式
            imgSubduedLight.setBackground(ContextCompat.getDrawable(LampActivity.this, R.drawable.bg_circle_fad9a7));
            imgSubduedLight.setImageResource(R.mipmap.pattern_subdued_light_on);
            tvSubduedLight.setTextColor(Color.parseColor("#000000"));
        }
        tvBrightness.setText(MyLamp.brightness+"%");
        sbLight.setProgress(MyLamp.brightness);

        Toast.makeText(LampActivity.this, "台灯已打开！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 关灯
     */
    public void lampTurnOff(){
        MyLamp.isTurnOn = false;
        imgLamp.setImageResource(R.drawable.lamp_off);
        imgPower.setBackground(ContextCompat.getDrawable(LampActivity.this, R.drawable.bg_circle_gray));
        imgPower.setImageResource(R.mipmap.power_off);
        tvPower.setTextColor(Color.parseColor("#cccccc"));

        imgSubduedLight.setBackground(ContextCompat.getDrawable(LampActivity.this, R.drawable.bg_circle_gray));
        imgSubduedLight.setImageResource(R.mipmap.pattern_subdued_light_off);
        tvSubduedLight.setTextColor(Color.parseColor("#cccccc"));

        imgColdLight.setBackground(ContextCompat.getDrawable(LampActivity.this, R.drawable.bg_circle_gray));
        imgColdLight.setImageResource(R.mipmap.pattern_cold_light_off);
        tvColdLight.setTextColor(Color.parseColor("#cccccc"));

        MyLamp.brightness = 0;
        tvBrightness.setText("0%");
        sbLight.setProgress(0);

        Toast.makeText(LampActivity.this, "台灯已关闭！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 冷光模式
     */
    public void useColdLight(){
        if(!MyLamp.isTurnOn){
            Toast.makeText(LampActivity.this, "请先打开台灯", Toast.LENGTH_SHORT).show();
            return;
        }
        if(MyLamp.pattern==0){
            imgSubduedLight.setBackground(ContextCompat.getDrawable(LampActivity.this, R.drawable.bg_circle_gray));
            imgSubduedLight.setImageResource(R.mipmap.pattern_subdued_light_off);
            tvSubduedLight.setTextColor(Color.parseColor("#cccccc"));
        }
        MyLamp.pattern = 1;
        imgColdLight.setBackground(ContextCompat.getDrawable(LampActivity.this, R.drawable.bg_circle_fad9a7));
        imgColdLight.setImageResource(R.mipmap.pattern_cold_light_on);
        tvColdLight.setTextColor(Color.parseColor("#000000"));

        Toast.makeText(LampActivity.this, "您正在使用冷光模式！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 柔光模式
     */
    public void useSubduedLight(){
        if(!MyLamp.isTurnOn){
            Toast.makeText(LampActivity.this, "请先打开台灯", Toast.LENGTH_SHORT).show();
            return;
        }

        if(MyLamp.pattern==1){
            imgColdLight.setBackground(ContextCompat.getDrawable(LampActivity.this, R.drawable.bg_circle_gray));
            imgColdLight.setImageResource(R.mipmap.pattern_cold_light_off);
            tvColdLight.setTextColor(Color.parseColor("#cccccc"));
        }
        MyLamp.pattern = 0;
        imgSubduedLight.setBackground(ContextCompat.getDrawable(LampActivity.this, R.drawable.bg_circle_fad9a7));
        imgSubduedLight.setImageResource(R.mipmap.pattern_subdued_light_on);
        tvSubduedLight.setTextColor(Color.parseColor("#000000"));

        Toast.makeText(LampActivity.this, "您正在使用柔光模式！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLampBack:{
                finish();
                break;
            }
            case R.id.imgPower:{
                if(MyLamp.isTurnOn){
                    lampTurnOff();
                }else {
                    lampTurnOn();
                }
                break;
            }
            case R.id.imgColdLight:{
                if(MyLamp.pattern==0){  //如果是柔光，则切换成冷光模式
                    useColdLight();
                }
                break;
            }
            case R.id.imgSubduedLight:{
                if(MyLamp.pattern==1){
                    useSubduedLight();
                }
                break;
            }
            default:
                break;
        }
    }
}
