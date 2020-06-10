package com.coolweather.ai_lamp.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.ai_lamp.R;

public class WriteCommentActivity extends AppCompatActivity {

    private int articleId;

    EditText et_comment_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);
        Intent intent = getIntent();
        articleId = intent.getIntExtra("articleId", -1);
        Log.e("TAG", "articleId = "+articleId);

        et_comment_content = findViewById(R.id.et_comment_content);
    }

    /*
     * 发表评论
    public void addComment(View view) {
        String content = et_comment_content.getText().toString();
        if (content.equals("")){
            Toast.makeText(getContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog("发表评论中...");
        final RequestBody requestBody = new FormBody.Builder()
                .add("articleId",articleId+"")
                .add("content", content)
                .build();
        OkHttpUtil.sendOkHttpRequestWithToken(OkHttpUtil.url_addComment, getContext(), requestBody, new Callback() {
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
                        showUserSuccess("发表成功");
                        setResult(RESULT_OK);
                        finish();
                    }else{
                        closeDialog();
                        showUserWrong(bean.getMsg());
                    }
                }else{
                    closeDialog();
                    showServiceDown();
                }
            }
        });
    }
     */

    /**
     * 取消发表
     * @param view
     */
    public void cancelComment(View view) {
        finish();
    }
}
