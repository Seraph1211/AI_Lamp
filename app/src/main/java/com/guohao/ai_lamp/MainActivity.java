package com.coolweather.ai_lamp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.coolweather.ai_lamp.dynamic.DynamicActivity;
import com.coolweather.ai_lamp.lamp.LampActivity;
import com.coolweather.ai_lamp.music.MusicListActivity;
import com.coolweather.ai_lamp.plans.PlanListActivity;
import com.coolweather.ai_lamp.report.LearningReportActivity;
import com.coolweather.ai_lamp.tomato.TomatoClockActivity;
import com.coolweather.ai_lamp.utils.Base64Utils;
import com.coolweather.ai_lamp.utils.MySharedPreferencesUtils;
import com.coolweather.ai_lamp.utils.StatusBarUtils;
import com.coolweather.ai_lamp.utils.StudentInfo;
import com.coolweather.ai_lamp.userinfo.QRCodeActivity;
import com.coolweather.ai_lamp.userinfo.UserDataActivity;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View headerLayout;  //drawer的head

    private TextView tvNickname;  //昵称
    private TextView tvId;  //账户

    private CircleImageView ivHeadPortrait1;  //drawer中用户头像
    private CircleImageView ivHeadPortrait2;  //MainActivity中的用户头像


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBarUtils.transparencyBar(MainActivity.this);
        StatusBarUtils.setLightStatusBar(MainActivity.this, true, true);  //状态栏字体颜色-黑

        if (getSupportActionBar() != null) getSupportActionBar().hide();
        NavigationView navigationView =  findViewById(R.id.nav_view);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        initNavigation();

        initData();
        initView();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
        initView();
    }

    private void initData(){
        StudentInfo.id = MySharedPreferencesUtils.getString(MainActivity.this, "id");
        StudentInfo.nickname = MySharedPreferencesUtils.getString(MainActivity.this, "nickname");
        StudentInfo.img = MySharedPreferencesUtils.getString(MainActivity.this, "img");
    }

    private void initView() {

        findViewById(R.id.cl_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "音乐", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MusicListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.cl_lamp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "台灯", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LampActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.cl_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanListActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "便签", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.cl_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "报告", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LearningReportActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.cl_clock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TomatoClockActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "计时", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.cl_community).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "社区", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, DynamicActivity.class));
            }
        });

        tvNickname = headerLayout.findViewById(R.id.myName);
        tvId = headerLayout.findViewById(R.id.myId);

        ivHeadPortrait1 = findViewById(R.id.iv_nav);
        ivHeadPortrait2 = headerLayout.findViewById(R.id.iv_head_portrait2);

        //更新UI
        if(!StudentInfo.nickname.equals("empty")){
            tvNickname.setText(StudentInfo.nickname);
        }

        if(!StudentInfo.id.equals("empty")){
            tvId.setText(StudentInfo.id);
        }

        if(!StudentInfo.img.equals("empty")){
            Log.d(TAG, "initView: img="+ StudentInfo.img);
            Base64Utils.loadBase64Image(StudentInfo.img, ivHeadPortrait1);
            Base64Utils.loadBase64Image(StudentInfo.img, ivHeadPortrait2);
        }

    }

    public void openDrawer(View view){
        drawer.openDrawer(navigationView);
    }

    private void initNavigation() {
        drawer = findViewById(R.id.drawer_layout);
        drawer.getBackground().setAlpha(20);

        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        headerLayout.findViewById(R.id.iv_qr_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QRCodeActivity.class));
            }
        });
    }

    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            Toast.makeText(MainActivity.this, "Drawer Open", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Drawer Close", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.myData:
                startActivity(new Intent(MainActivity.this, UserDataActivity.class));
                //Toast.makeText(MainActivity.this, "还没写呢！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myCard:
                Toast.makeText(MainActivity.this, "还没写呢！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myLamp:
                startActivity(new Intent(MainActivity.this, LampActivity.class));
                //Toast.makeText(MainActivity.this, "还没写呢！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mySetting:
                Toast.makeText(MainActivity.this, "还没写呢！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myHelp:
                Toast.makeText(MainActivity.this, "还没写呢！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myOurs:
                Toast.makeText(MainActivity.this, "还没写呢！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myOut:
                Toast.makeText(MainActivity.this, "已经注销当前用户", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
