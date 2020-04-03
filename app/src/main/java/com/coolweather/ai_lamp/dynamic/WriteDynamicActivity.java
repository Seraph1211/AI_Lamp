package com.coolweather.ai_lamp.dynamic;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.ai_lamp.R;
import com.coolweather.ai_lamp.report.LearningReportActivity;
import com.coolweather.ai_lamp.tomato.TomatoClockActivity;
import com.coolweather.ai_lamp.tools.StatusBarUtils;

public class WriteDynamicActivity extends AppCompatActivity {

    EditText et_dynamic_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_dynamic);

        StatusBarUtils.setStatusBarColor(WriteDynamicActivity.this, R.color.colorWhite);
        StatusBarUtils.setLightStatusBar(WriteDynamicActivity.this, true, true);  //状态栏字体颜色-黑


        et_dynamic_content = findViewById(R.id.et_dynamic_content);
    }

    /**
     * @param view
     */
    public void uploadDynamic(View view) {
        String content = et_dynamic_content.getText().toString();
        if (content.equals("")){
            Toast.makeText(WriteDynamicActivity.this, "写点什么吧", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(WriteDynamicActivity.this, "发表动态", Toast.LENGTH_SHORT).show();
    }


    public void cancelDynamic(View view) {
        finish();
    }

}
