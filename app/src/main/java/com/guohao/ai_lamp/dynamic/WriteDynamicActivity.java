package com.coolweather.ai_lamp.dynamic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.ai_lamp.R;
import com.coolweather.ai_lamp.utils.StatusBarUtils;
import com.coolweather.ai_lamp.utils.ToastUtils;
import com.coolweather.ai_lamp.utils.StudentInfo;

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

        DynamicBean bean = new DynamicBean();
        bean.setNickname(StudentInfo.nickname);
        bean.setContent(content);
        bean.setHeadPortrait(StudentInfo.img);
        bean.setCommentCount(0);
        bean.setReadCount(0);
        bean.setLikeCount(0);

        showLoadingDialog(bean);

    }


    public void cancelDynamic(View view) {
        finish();
    }

    private void showLoadingDialog(final DynamicBean bean){
        final ProgressDialog dialog = ProgressDialog.show(WriteDynamicActivity.this, "动态发表中...", ""
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
                ToastUtils.showToast(WriteDynamicActivity.this, "动态发表成功！");
                Intent intent = new Intent();
                intent.putExtra("bean", bean);
                setResult(RESULT_OK, intent);
                finish();
            }
        }).start();
    }

}
