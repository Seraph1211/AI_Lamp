package com.coolweather.ai_lamp.userinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;


import com.coolweather.ai_lamp.R;
import com.coolweather.ai_lamp.utils.MySharedPreferencesUtils;
import com.coolweather.ai_lamp.utils.ToastUtils;


public class ModifyUserDataActivity extends AppCompatActivity {
    private final String TAG = "ModifyActivity";
    private TextView tvTitle;
    private AppCompatEditText etContent;
    private Button btnModify;
    private int type;  //要修改的数据：0-nickname, 1-pwd
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_data);

        initData();
        initView();
    }


    private void initView(){
        tvTitle = findViewById(R.id.tv_modify_user_data);
        etContent = findViewById(R.id.et_modify_user_data);
        btnModify = findViewById(R.id.btn_modify_user_data);

        if(type == 0){
            tvTitle.setText("修改昵称");
        }else {
            tvTitle.setText("修改密码");
        }

        etContent.setHint(content);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = etContent.getText().toString().trim();
                if(type == 0){
                    modifyNickname();
                }else {
                    modifyPwd();
                }
            }
        });
    }

    private void initData(){
        type = getIntent().getIntExtra("type", -1);
        content = getIntent().getStringExtra("content");
    }

    public void back(View view){
        finish();
    }

    /**
     * 修改昵称
     */
    private void modifyNickname(){
        if(content.equals("")){
            ToastUtils.showToast(ModifyUserDataActivity.this, "昵称不能为空！");
            return;
        }else if(content.length() > 20){
            ToastUtils.showToast(ModifyUserDataActivity.this, "请输入20个字符以内的昵称！");
            return;
        }


        showLoadingDialog1();
    }

    /**
     * 修改密码
     */
    private void modifyPwd(){
        if(content.equals("")){
            ToastUtils.showToast(ModifyUserDataActivity.this, "密码不能为空！");
            return;
        }else if(content.length()<6 || content.length()>12){
            ToastUtils.showToast(ModifyUserDataActivity.this, "请输入6-12位的密码！");
            return;
        }

        showLoadingDialog2();
    }

    private void showLoadingDialog1(){
        final ProgressDialog dialog = ProgressDialog.show(ModifyUserDataActivity.this, "加载中...", ""
                ,false,false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
                ToastUtils.showToast(ModifyUserDataActivity.this, "昵称修改成功！");
                MySharedPreferencesUtils.putString(ModifyUserDataActivity.this, "nickname", content);
                Intent intent = new Intent();
                intent.putExtra("new_name", content);
                setResult(RESULT_OK, intent);
                finish();

            }
        }).start();
    }

    private void showLoadingDialog2(){
        final ProgressDialog dialog = ProgressDialog.show(ModifyUserDataActivity.this, "加载中...", ""
                ,false,false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
                ToastUtils.showToast(ModifyUserDataActivity.this, "密码修改成功！");
                MySharedPreferencesUtils.putString(ModifyUserDataActivity.this, "password", content);
                setResult(RESULT_OK);
                finish();

            }
        }).start();
    }
}
