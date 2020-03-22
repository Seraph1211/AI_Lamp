package com.coolweather.ai_lamp.dynamic;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.ai_lamp.R;

public class WriteDynamicActivity extends AppCompatActivity {

    EditText et_dynamic_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_dynamic);
        et_dynamic_content = findViewById(R.id.et_dynamic_content);
    }

    /*
     * @param view
    public void uploadDynamic(View view) {
        String content = et_dynamic_content.getText().toString();
        if (content.equals("")){
            Toast.makeText(WriteDynamicActivity.this, "写点什么吧", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog("发表动态中...");
        final RequestBody requestBody = new FormBody.Builder()
                .add("content", content)
                .build();
        OkHttpUtil.sendOkHttpRequestWithToken(OkHttpUtil.url_uploadArticle, getContext(), requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeDialog();
                showServiceDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200){
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<NormalBean>() {}.getType();
                    NormalBean bean = gson.fromJson(response.body().string(), type);
                    if (bean.getCode() == 0){
                        closeDialog();
                        setResult(RESULT_OK);
                        finish();
                    }else{
                        closeDialog();
                        showUserWrong(bean.getMsg());
                    }
                }else {
                    closeDialog();
                    showServiceDown();
                }
            }
        });

    }
     */

    public void cancelDynamic(View view) {
        finish();
    }

}
