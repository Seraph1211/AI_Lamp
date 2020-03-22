package com.coolweather.ai_lamp.loding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.ai_lamp.MyData;
import com.coolweather.ai_lamp.R;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Loging extends AppCompatActivity {

    private static final String TAG = "Loging : ";
    EditText et_username;
    EditText et_userpass;
    Button bt_loging;
    Button bt_add;
    CheckBox cb_password;
    CheckBox cb_loging;
    private Boolean b1 = false;
    private Boolean b2 = false;

    private PrintWriter pw;
    private BufferedReader in;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    String s1 = (String) msg.obj;
                    if("1".equals(s1)){
                        MyData.Loding=1;
                        Toast.makeText(Loging.this, "登录成功，欢迎使用", Toast.LENGTH_SHORT).show();
                        MyData.isLoging=true;
                        finish();
                    }else{
                        MyData.Loding=0;
                        Toast.makeText(Loging.this, "账号或密码错误,请重新登录", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        init();
        initData();
    }


    private void init() {
        bt_add = findViewById(R.id.bt_add);
        bt_loging = findViewById(R.id.bt_loging);
        cb_loging = findViewById(R.id.cb_loging);
        cb_password = findViewById(R.id.cb_password);
        et_username = findViewById(R.id.et_username);
        et_userpass = findViewById(R.id.et_userpass);


        bt_loging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(et_username.getText().toString()) || "".equals(et_userpass.getText().toString())){
                    Toast.makeText(Loging.this, "账户或密码不能为空!", Toast.LENGTH_SHORT).show();
                }else{
                    loging();
                }
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Loging.this,ZhuCe.class);
                startActivity(intent);

            }
        });
        //CheckBox
        cb_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                b1 = isChecked;
                Log.i(TAG, b1+"：");
            }
        });
        cb_loging.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                b2 = isChecked;
                Log.i(TAG, b2+"：");
            }
        });

    }

    private void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        String userPassword = sharedPreferences.getString("userPassword", "");

        int cb1 = sharedPreferences.getInt("b1", 0);
        int cb2 = sharedPreferences.getInt("b2", 0);

        et_userpass.setText(userPassword);
        et_username.setText(userName);
        cb_password.setChecked(cb1==1);
        cb_loging.setChecked(cb2==1);
        Log.d(TAG, cb2+"<-------");
        if(cb2==1) loging();

    }


    private void loging() {
        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", et_username.getText().toString());
        if(b1){
            editor.putString("userPassword", et_userpass.getText().toString());
            editor.putInt("b1", 1);
        }else{
            editor.putString("userPassword", "");
            editor.putInt("b1", 0);
        }
        if(b2){
            editor.putInt("b2", 1);
        }else{
            editor.putInt("b2", 0);
        }
        editor.commit();

        String msg = "1"+":"+et_username.getText()+":"+et_userpass.getText();
        socket(msg,1);

    }

    private void socket(final String msg, final int t){
        Log.i(TAG, "开启线程"+t);
        Message message = new Message();
        message.what = t;
        message.obj = "1";
        mHandler.sendMessage(message);
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                Socket socket = null;      //步骤一
//                try {
//                    socket = new Socket(MyData.mySeverID, MyData.mySeverPoart);
//                    socket.setSoTimeout(10000);
//                    pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
//                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
//                    Log.i(TAG, "开始发送"+t);
//                    pw.println(msg);
//                    String s = "";
//                    Log.i(TAG, "开始接收"+t);
//                    while (true){
//                        if ((s = in.readLine()) != null) {
//                            Log.i(TAG, "消息是:"+t+":"+s);
//                            Message message = new Message();
//                            message.what = t;
//                            message.obj = s;
//                            mHandler.sendMessage(message);
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
    }





}
