package com.coolweather.ai_lamp.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolweather.ai_lamp.R;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private int articleId;

    RecyclerView rv_comments;
    CommentAdapter adapter;
    List<CommentBean> mList;

    public static int ACTION_WRITE_COMMENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Intent intent = getIntent();
        articleId = intent.getIntExtra("articleId", -1);
        Log.e("TAG", "articleId = "+articleId);

        mList = new ArrayList<>();
        adapter = new CommentAdapter(this, mList);
        adapter.setButtonOnClick(new ButtonInterface() {//删除自己的评论时的监听
            @Override
            public void onclick(Intent intent, int pos) {//pos为list中的位置
                int commentId = mList.get(pos).getCommentId();
                //delSelfComment(commentId);
            }
        });
        rv_comments = findViewById(R.id.rv_comments);
        rv_comments.setLayoutManager(new LinearLayoutManager(this));
        rv_comments.setAdapter(adapter);
        //loadComments();
    }

    /*
    private void loadComments(){
        mList.clear();
        //showDialog("加载评论中...");
        final RequestBody requestBody = new FormBody.Builder()
                .add("articleId",articleId+"")
                .build();
        OkHttpUtil.sendOkHttpRequestWithToken(OkHttpUtil.url_getComments, getContext(), requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeDialog();
                showServiceDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200){
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<DynamicCommentsBean>() {}.getType();
                    DynamicCommentsBean bean = gson.fromJson(response.body().string(), type);
                    if (bean.getCode() == 0){
                        List<DynamicCommentsBean.DataBean.CommentsBean> list = bean.getData().getComments();
                        for (int i=0; i<list.size(); i++){
                            mList.add(list.get(i));
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                closeDialog();
                            }
                        });
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
     * 写评论
     * @param view
     */
    public void writeComment(View view) {
        Intent intent = new Intent(CommentsActivity.this, WriteCommentActivity.class);
        intent.putExtra("articleId", articleId);
        startActivityForResult(intent, ACTION_WRITE_COMMENT);
    }


    /*
     * 删除自己的评论
    private void delSelfComment(int commentId){
        DialogUtil.showProgressDialog(getContext(), "删除评论中...");
        final RequestBody requestBody = new FormBody.Builder()
                .add("commentId",commentId+"")
                .build();
        OkHttpUtil.sendOkHttpRequestWithToken(OkHttpUtil.url_delComment, getContext(), requestBody, new Callback() {
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
                        showUserSuccess("删除成功");
                        loadComments();
                    }else {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_WRITE_COMMENT && resultCode == RESULT_OK) {
            //loadComments();
        }
    }

}
