package com.coolweather.ai_lamp.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.coolweather.ai_lamp.MainActivity;
import com.coolweather.ai_lamp.R;
import com.coolweather.ai_lamp.utils.Base64Utils;
import com.coolweather.ai_lamp.utils.MySharedPreferencesUtils;
import com.coolweather.ai_lamp.utils.StudentInfo;


import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private CircleImageView imgUser;  //用户头像
    private EditText etAccount;  //用户账号
    private EditText etPassword;  //密码
    private AppCompatCheckBox cbRememberPassword;  //单选框，是否记住密码
    private Button btnLogin;  //登录按钮
    private TextView tvForgetPassword;  //忘记密码
    private TextView tvJumpToRegister;  //跳转至注册界面

    private String account;
    private String password;
    private String img;
    private boolean rememberPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initData();
        initView();
    }

    public void initView(){
        imgUser = findViewById(R.id.img_user);
        etAccount = findViewById(R.id.et_login_account);
        etPassword = findViewById(R.id.et_login_password);
        cbRememberPassword = findViewById(R.id.cb_remember_password);
        btnLogin = findViewById(R.id.btn_login);
        tvForgetPassword = findViewById(R.id.tv_forget_password);
        tvJumpToRegister = findViewById(R.id.tv_jump_to_register);

        btnLogin.setOnClickListener(this);
        tvJumpToRegister.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);


        //刷新信息
        if(!account.equals("empty")){  //如果本地有记录用户信息
            etAccount.setText(account);

            if(!img.equals("empty")){  //用户头像有做本地存储
                Base64Utils.loadBase64Image(img, imgUser);
            }
        }

        if(rememberPwd){
            cbRememberPassword.setChecked(true);
            etPassword.setText(password);
        }else {
            cbRememberPassword.setChecked(false);
        }
    }

    public void initData(){
        account = MySharedPreferencesUtils.getString(LoginActivity.this, "account");
        password = MySharedPreferencesUtils.getString(LoginActivity.this, "password");
        rememberPwd = MySharedPreferencesUtils.getBoolean(LoginActivity.this, "remember_pwd");
        img = MySharedPreferencesUtils.getString(LoginActivity.this, "img");
    }

    @Override
    protected void onDestroy(){
        MySharedPreferencesUtils.putString(LoginActivity.this, "account", etAccount.getText().toString().trim());
        MySharedPreferencesUtils.putString(LoginActivity.this, "password", etPassword.getText().toString().trim());
        MySharedPreferencesUtils.putBoolean(LoginActivity.this, "remember_pwd", cbRememberPassword.isChecked());

        account = MySharedPreferencesUtils.getString(LoginActivity.this, "account");
        password = MySharedPreferencesUtils.getString(LoginActivity.this, "password");
        rememberPwd = MySharedPreferencesUtils.getBoolean(LoginActivity.this, "remember_pwd");
        Log.d(TAG, "onDestroy: [account="+account+", password="+password +"]");

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:{
                if(etAccount.getText().toString().trim().equals("") || etPassword.getText().toString().trim().equals("")){
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                StudentInfo.account = etAccount.getText().toString().trim();
                StudentInfo.password = etPassword.getText().toString().trim();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
            }

            case R.id.tv_forget_password:{
                Toast.makeText(LoginActivity.this, "那咋办嘛，咱也没办法啊！", Toast.LENGTH_SHORT).show();

                account = etAccount.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                rememberPwd = cbRememberPassword.isChecked();
                MySharedPreferencesUtils.putString(LoginActivity.this, "account", account);
                MySharedPreferencesUtils.putString(LoginActivity.this, "password", password);
                MySharedPreferencesUtils.putBoolean(LoginActivity.this, "remember_pwd", rememberPwd);
                break;
            }

            case R.id.tv_jump_to_register:{
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            }
        }
    }

}
