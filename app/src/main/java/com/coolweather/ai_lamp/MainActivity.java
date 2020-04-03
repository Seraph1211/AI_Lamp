package com.coolweather.ai_lamp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.coolweather.ai_lamp.dynamic.DynamicActivity;
import com.coolweather.ai_lamp.light.MyLeight;
import com.coolweather.ai_lamp.loding.Loging;
import com.coolweather.ai_lamp.music.MusicActivity;
import com.coolweather.ai_lamp.music.MusicListActivity;
import com.coolweather.ai_lamp.plans.PlanListActivity;
import com.coolweather.ai_lamp.report.LearningReportActivity;
import com.coolweather.ai_lamp.tomato.TomatoClockActivity;
import com.coolweather.ai_lamp.tools.StatusBarUtils;
import com.google.android.material.navigation.NavigationView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity:  ";
    View headerLayout;
    TextView myName;
    TextView myID;
    Button myCount;

    private Banner banner;
    private ArrayList<Integer> imageUrlList;
    private ArrayList<String> titleList;

    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBarUtils.transparencyBar(MainActivity.this);
        StatusBarUtils.setLightStatusBar(MainActivity.this, true, true);  //状态栏字体颜色-黑

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        int cb2 = sharedPreferences.getInt("b2", 0);
        if(cb2 == 1)MyData.isLoging=true;
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        NavigationView navigationView =  findViewById(R.id.nav_view);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        initNavigation();
        initData();
        shuXin();
        //initBanner();
    }


    public void initBanner(){
        imageUrlList = new ArrayList<>();
        titleList = new ArrayList<>();

        imageUrlList.add(R.drawable.banner_5);
        imageUrlList.add(R.drawable.banner_6);
        imageUrlList.add(R.drawable.banner_7);
        imageUrlList.add(R.drawable.banner_8);

        titleList.add("We become what we dream about");
        titleList.add("挺不过军训，你怎么拼得赢学长？");
        titleList.add("再见少年");
        titleList.add("越努力，越幸运");

        banner = findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new MyLoader());
        banner.setImages(imageUrlList);
        banner.setBannerTitles(titleList);
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(2000);  //切换频率
        banner.isAutoPlay(true);  //自动启动
        banner.setIndicatorGravity(BannerConfig.CENTER);  //位置设置
        banner.start();
    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(MainActivity.this)
                    .load(path).
                    into(imageView);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        shuXin();
    }

    private void shuXin() {
        if(MyData.isLoging){
            SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            String name = "昵称：未设置";
            String id = "ID：" +sharedPreferences.getString("userName", "18674877242");
            myName.setText(name);
            myID.setText(id);
            myCount.setText("用户管理（有账户）");
        }else{
            myName.setText("昵称：未登录");
            myID.setText("ID：未登录");
            myCount.setText("用户管理（无账户）");
        }
    }

    private void initData() {

        /*
        findViewById(R.id.main_app_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "音乐", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Music.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.main_app_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "台灯", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MyLeight.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.main_app_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanListActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "计划", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.main_app_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "统计", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LearningReportActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.main_app_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TomatoClockActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "闹钟", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.main_app_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "社区", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, DynamicActivity.class));
            }
        });
         */

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
                Intent intent = new Intent(MainActivity.this, MyLeight.class);
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

        myName = headerLayout.findViewById(R.id.myName);
        myID = headerLayout.findViewById(R.id.myId);
        myCount = headerLayout.findViewById(R.id.main_count);
        myCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyData.isLoging){
                    Toast.makeText(MainActivity.this, "请退出当前用户再试", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, Loging.class);
                    startActivity(intent);
                }
            }
        });





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
            Toast.makeText(MainActivity.this, "点我干嘛！哼！", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "打开");
        } else {
            Toast.makeText(MainActivity.this, "点我干嘛！哼！", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "关闭");
            super.onBackPressed();
        }
    }

    //菜单栏
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.myData:
                Toast.makeText(MainActivity.this, "还没写呢！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myCard:
                Toast.makeText(MainActivity.this, "还没写呢！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myLamp:
                Toast.makeText(MainActivity.this, "还没写呢！", Toast.LENGTH_SHORT).show();
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
                SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", "");
                editor.putString("userPassword", "");
                editor.putInt("b1", 0);
                editor.putInt("b2", 0);
                editor.commit();
                Toast.makeText(MainActivity.this, "已经注销当前用户", Toast.LENGTH_SHORT).show();
                myName.setText("昵称：未登录");
                myID.setText("ID：未登录");
                myCount.setText("用户管理（无账户）");
                MyData.isLoging=false;
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
