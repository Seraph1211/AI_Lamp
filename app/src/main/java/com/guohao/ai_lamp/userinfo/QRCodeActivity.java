package com.coolweather.ai_lamp.userinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.coolweather.ai_lamp.R;
import com.coolweather.ai_lamp.utils.StatusBarUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class QRCodeActivity extends AppCompatActivity {

    private ImageView ivQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        StatusBarUtils.setStatusBarColor(QRCodeActivity.this, R.color.colorWhite);  //设置状态栏颜色
        StatusBarUtils.setLightStatusBar(QRCodeActivity.this, true, true);  //状态栏字体颜色-黑

        ZXingLibrary.initDisplayOpinion(this);

        ivQRCode = findViewById(R.id.imageQRCode);

        Bitmap bitmap = CodeUtils.createImage("这是二维码所包含的信息", 300, 300, null);
        ivQRCode.setImageBitmap(bitmap);

    }

    public void onClickBack(View view){
        finish();
    }
}
