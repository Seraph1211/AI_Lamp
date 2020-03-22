package com.coolweather.ai_lamp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class QRCodeActivity extends AppCompatActivity {

    private ImageView ivQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        ZXingLibrary.initDisplayOpinion(this);

        ivQRCode = findViewById(R.id.imageQRCode);

        Bitmap bitmap = CodeUtils.createImage("这是二维码所包含的信息", 300, 300, null);
        ivQRCode.setImageBitmap(bitmap);

    }

    public void onClickBack(View view){
        finish();
    }
}
