 package com.coolweather.ai_lamp.loding;

 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;

 import com.coolweather.ai_lamp.R;


 public class ZhuCe extends AppCompatActivity {

     EditText et_name;
     EditText et_pass1;
     EditText et_pass2;
     EditText et_yanzheng;
     Button bt_get;
     Button bt_zhuce;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_zhu_ce);
         if (getSupportActionBar() != null) getSupportActionBar().hide();
         init();
     }

     private void init() {
         et_name = findViewById(R.id.et_zhuce_username);
         et_pass1 = findViewById(R.id.et_zhuce_pass1);
         et_pass2 = findViewById(R.id.et_zhuce_pass2);
         et_yanzheng = findViewById(R.id.et_zhuce_yanzheng);
         bt_get = findViewById(R.id.bt_zhuce_get);
         bt_zhuce = findViewById(R.id.bt_zhuce_zhuce);
         bt_get.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(ZhuCe.this, "验证码已发送", Toast.LENGTH_SHORT).show();
             }
         });
         bt_zhuce.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String name = et_name.getText().toString();
                 String pass1 = et_pass1.getText().toString();
                 String pass2 = et_pass2.getText().toString();
                 String yanzheng = et_yanzheng.getText().toString();
                 if("".equals(name)||"".equals(pass1)||"".equals(pass2)){
                     Toast.makeText(ZhuCe.this, "以上不能为空！", Toast.LENGTH_SHORT).show();
                 }else if(!pass1.equals(pass2)){
                     Toast.makeText(ZhuCe.this, "两次密码不一样！", Toast.LENGTH_SHORT).show();
                 }else if(!"".equals(yanzheng)){
                     Toast.makeText(ZhuCe.this, "验证码不正确！", Toast.LENGTH_SHORT).show();
                 }else{
                     add();
                 }



             }
         });

     }

     private void add() {
         Toast.makeText(ZhuCe.this, "注册成功,请再次登录使用", Toast.LENGTH_SHORT).show();
         finish();
     }
 }
