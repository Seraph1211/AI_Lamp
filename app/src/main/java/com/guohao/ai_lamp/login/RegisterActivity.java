package com.coolweather.ai_lamp.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.ai_lamp.R;
import com.mob.MobSDK;
import com.mob.OperationCallback;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 注册
 * 用户通过手机验证码注册
 * 注册成功后将用户手机号、id、昵称（默认值，即"User Nickname"）、密码发送到服务器
 * 注册成功后跳转至登录界面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "RegisterActivity";

    private EditText etAccount;  //账号（手机号）
    private EditText etSecurityCode;  //短信验证码
    private EditText etPassword;  //密码
    private Button btnSendSecurityCode;  //按钮，发送验证码
    private Button btnRegister;  //注册按钮
    private TextView tvJumpToLogin;  //跳转到登录界面

    private int id;  //id
    private String phoneNum;  //手机号
    private String securityCode;  //验证码
    private String password;  //密码

    EventHandler handler = new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE){  //回调完成

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {  //提交验证码成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                   startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                   finish();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){  //获取验证码成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    Log.i("test","test");
                }
            }else{
                ((Throwable)data).printStackTrace();
                Throwable throwable = (Throwable) data;
                throwable.printStackTrace();
                Log.i("1234",throwable.toString());
                try {
                    JSONObject obj = new JSONObject(throwable.getMessage());
                    final String des = obj.optString("detail");
                    if (!TextUtils.isEmpty(des)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this,des,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        submitPrivacyGrantResult(true);  //个人信息需要获得用户授权

        MobSDK.init(this);  //初始化MobSDK

        SMSSDK.registerEventHandler(handler);  //注册短信回调

        initView();
    }

    public void initView(){
        etAccount = findViewById(R.id.et_register_account);
        etSecurityCode = findViewById(R.id.et_register_security_code);
        etPassword = findViewById(R.id.et_register_password);
        btnSendSecurityCode = findViewById(R.id.btn_register_send_security_code);
        btnRegister = findViewById(R.id.btn_register);
        tvJumpToLogin = findViewById(R.id.tv_jump_to_login);

        btnSendSecurityCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvJumpToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_register_send_security_code:{

                if(isPhoneNumValid()){  //如果用户的手机号合法，则向该手机号发送短信验证码
                    SMSSDK.getVerificationCode("86", phoneNum);
                }

                break;
            }

            case R.id.btn_register:{
                securityCode = etSecurityCode.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                if(TextUtils.isEmpty(phoneNum)){
                    Toast.makeText(RegisterActivity.this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(securityCode)){
                    Toast.makeText(RegisterActivity.this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length()<6 || password.length()>12){
                    Toast.makeText(RegisterActivity.this, "请输入长度为6-12位的密码！", Toast.LENGTH_SHORT).show();
                    return;
                }

                SMSSDK.submitVerificationCode("86", phoneNum, securityCode);
                break;
            }

            case R.id.tv_jump_to_login:{
                finish();
                //phoneNum = "13725469996";
                //password = "123456";
                //register();
                break;
            }
        }
    }

    /**
     * 判断用户输入的手机号是否合法
     * @return
     */
    private boolean isPhoneNumValid() {
        phoneNum = etAccount.getText().toString().trim();

        if(TextUtils.isEmpty(phoneNum)){  //输入的手机号为空
            Toast.makeText(RegisterActivity.this,"手机号码不能为空！",Toast.LENGTH_LONG).show();
            return false;
        } else if(phoneNum.length()!=11){
            Toast.makeText(RegisterActivity.this,"请输入11位的手机号码！",Toast.LENGTH_LONG).show();
            return false;
        } else{
            String num="[1][3578]\\d{9}";  //正则表达式
            if(phoneNum.matches(num)) {
                return true;
            } else{
                Toast.makeText(RegisterActivity.this,"请输入正确的手机号码!",Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(handler);
    }

    private void submitPrivacyGrantResult(boolean granted) {
        MobSDK.submitPolicyGrantResult(granted, new OperationCallback<Void>() {
            @Override
            public void onComplete(Void data) {
                Log.d(TAG, "隐私协议授权结果提交：成功");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "隐私协议授权结果提交：失败");
            }
        });
    }


}
