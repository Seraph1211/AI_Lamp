package com.coolweather.ai_lamp.dynamic;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.coolweather.ai_lamp.R;
import com.coolweather.ai_lamp.utils.Base64Utils;
import com.coolweather.ai_lamp.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于展示和发布动态
 */
public class DynamicActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar tb_dynamic;
    public static int ACTION_WRITE_DYNAMIC = 1;
    SwipeRefreshLayout sr_dynamic;
    RecyclerView rv_dynamic;
    DynamicAdapter adapter;
    List<DynamicBean> mList;

    private boolean mIsRefreshing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        StatusBarUtils.setStatusBarColor(DynamicActivity.this, R.color.colorWhite);
        StatusBarUtils.setLightStatusBar(DynamicActivity.this, true, true);  //状态栏字体颜色-黑

        mIsRefreshing = false;
        mList = new ArrayList<>();
        initList();
        adapter = new DynamicAdapter(this, mList);
        adapter.setButtonOnClick(new ButtonInterface() {
            @Override
            public void onclick(Intent intent, int pos) {
                startActivity(intent);
            }
        });
        initView();
    }

    public void initList(){
        DynamicBean bean1 = new DynamicBean();
        bean1.setNickname("落魄山大总管");
        bean1.setContent("墙里秋千墙外道。 墙外行人， 墙里佳人笑。 笑渐不闻声渐悄。 多情却被无情恼。");
        bean1.setHeadPortrait(Base64Utils.encodeImageToBase64String(BitmapFactory.decodeResource(getResources(), R.drawable.head_img_1)));
        bean1.setCommentCount(0);
        bean1.setReadCount(0);
        bean1.setLikeCount(0);
        mList.add(bean1);

        DynamicBean bean2 = new DynamicBean();
        bean2.setNickname("骑龙巷总护法");
        bean2.setContent(" 莫说青山多障碍，风也清，水也净，白云过山峰也可传情。");
        bean2.setHeadPortrait(Base64Utils.encodeImageToBase64String(BitmapFactory.decodeResource(getResources(), R.drawable.head_img_2)));
        bean2.setCommentCount(0);
        bean2.setReadCount(0);
        bean2.setLikeCount(0);
        mList.add(bean2);

        DynamicBean bean3 = new DynamicBean();
        bean3.setNickname("稻香村保安队大队长");
        bean3.setContent(" 少年易老学难成，一寸光阴不可轻。未觉池塘春草梦，阶前梧叶已秋声。");
        bean3.setHeadPortrait(Base64Utils.encodeImageToBase64String(BitmapFactory.decodeResource(getResources(), R.drawable.head_img_3)));
        bean3.setCommentCount(0);
        bean3.setReadCount(0);
        bean3.setLikeCount(0);
        mList.add(bean3);
    }

    private void initView(){
        tb_dynamic = findViewById(R.id.tb_dynamic);
        setSupportActionBar(tb_dynamic);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        sr_dynamic = findViewById(R.id.sr_dynamic);
        sr_dynamic.setColorSchemeResources(R.color.colorPrimary);
        sr_dynamic.setOnRefreshListener(this);

        rv_dynamic = findViewById(R.id.rv_dynamic);
        rv_dynamic.setLayoutManager(new LinearLayoutManager(DynamicActivity.this));
        rv_dynamic.setAdapter(adapter);
        rv_dynamic.addItemDecoration(new DividerItemDecoration(DynamicActivity.this, LinearLayoutManager.VERTICAL));
        /*
        rv_dynamic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mIsRefreshing){
                    return true;
                }else{
                    return false;
                }
            }
        });
        sr_dynamic.post(new Runnable() {
            @Override
            public void run() {
                sr_dynamic.setRefreshing(true);
            }

        });
        onRefresh();
         */
    }

    /*
     * 获取动态（最多前10条）
    private void getAllDynamics(){
        mList.clear();
        final RequestBody requestBody = new FormBody.Builder()
                .add("articleId","0")
                .add("type","init")
                .build();
        OkHttpUtil.sendOkHttpRequestWithToken(OkHttpUtil.url_getArticle, getContext(), requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG","getAllDynamics OnFailure");
                showServiceDown();
                stopRefreshing();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200){
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<DynamicsBean>() {}.getType();
                    DynamicsBean bean = gson.fromJson(response.body().string(), type);
                    if (bean.getCode() == 0){
                        List<DynamicsBean.DataBean.ArticleListBean> list = bean.getData().getArticleList();
                        for (int i=0; i<list.size(); i++){
                            mList.add(list.get(i));
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                stopRefreshing();
                            }
                        });
                    }else {
                        Log.e("TAG","bean.getCode() != 0");
                        showUserWrong(bean.getMsg());
                        stopRefreshing();
                    }
                }else {
                    showServiceDown();
                    stopRefreshing();
                }
            }
        });
    }
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.release_dynamics, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_release_dynamics:
                Intent intent = new Intent(DynamicActivity.this, WriteDynamicActivity.class);
                startActivityForResult(intent, ACTION_WRITE_DYNAMIC);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_WRITE_DYNAMIC && resultCode == RESULT_OK){
            DynamicBean bean = (DynamicBean)data.getSerializableExtra("bean");
            adapter.addItem(bean);
        }
    }

    private void stopRefreshing(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sr_dynamic.setRefreshing(false);
                mIsRefreshing = false;
            }
        });
    }

    @Override
    public void onRefresh() {
        mIsRefreshing = true;
        Log.e("TAG", "onRefresh");
        //getAllDynamics();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sr_dynamic.post(new Runnable() {
                            @Override
                            public void run() {
                                sr_dynamic.setRefreshing(false);
                            }
                        });
                    }
                });
            }
        }).start();
    }
}
