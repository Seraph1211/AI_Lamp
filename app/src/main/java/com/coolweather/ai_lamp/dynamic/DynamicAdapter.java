package com.coolweather.ai_lamp.dynamic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coolweather.ai_lamp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder>{

    private Context mContext;
    private ButtonInterface buttonInterface;
    public List<DynamicBean> mList;

    public DynamicAdapter(Context context, List<DynamicBean> list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dynamic, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.ib_dynamic_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, CommentsActivity.class);
                intent.putExtra("articleId", mList.get(pos).getId());
                buttonInterface.onclick(intent, pos);
            }
        });

        viewHolder.toggle_dynamic_likes.setChecked(false);
        viewHolder.toggle_dynamic_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(viewHolder.tv_dynamic_likes_num.getText().toString());
                if (viewHolder.toggle_dynamic_likes.isChecked()){
                    num++;
                }else {
                    num--;
                }
                viewHolder.tv_dynamic_likes_num.setText(num+"");
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DynamicBean dynamic = mList.get(i);
        /*
        UserBaseInfo userBaseInfo = UsersCache.getUserBaseInfo(id);
        if (userBaseInfo != null){
            if (userBaseInfo.getImagePath() != null){
                Glide.with(mContext).load(userBaseInfo.getImagePath()).into(viewHolder.civ_dynamic_head);
            }else {
                viewHolder.civ_dynamic_head.setImageResource(R.drawable.head);
            }

            viewHolder.tv_dynamic_name.setText(userBaseInfo.getName());
        }
         */
        viewHolder.tv_dynamic_name.setText(dynamic.getNickname());
        viewHolder.tv_dynamic_content.setText(dynamic.getContent());
        viewHolder.tv_dynamic_times.setText("浏览次数 "+dynamic.getReadCount());
        viewHolder.tv_dynamic_comment_num.setText(dynamic.getCommentCount()+"");
        viewHolder.tv_dynamic_likes_num.setText(dynamic.getLikeCount()+"");
        if (dynamic.isLike()) {
            viewHolder.toggle_dynamic_likes.setChecked(true);
        }else {
            viewHolder.toggle_dynamic_likes.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setButtonOnClick(ButtonInterface buttonInterface){
        this.buttonInterface = buttonInterface;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civ_dynamic_head;
        TextView tv_dynamic_name;
        TextView tv_dynamic_content;

        TextView tv_dynamic_times;

        ImageButton ib_dynamic_comment;
        TextView tv_dynamic_comment_num;

        ToggleButton toggle_dynamic_likes;
        TextView tv_dynamic_likes_num;

        public ViewHolder(View view){
            super(view);
            civ_dynamic_head = view.findViewById(R.id.civ_dynamic_head);
            tv_dynamic_name = view.findViewById(R.id.tv_dynamic_name);
            tv_dynamic_content = view.findViewById(R.id.tv_dynamic_content);
            tv_dynamic_times = view.findViewById(R.id.tv_dynamic_times);
            ib_dynamic_comment = view.findViewById(R.id.ib_dynamic_comment);
            tv_dynamic_comment_num = view.findViewById(R.id.tv_dynamic_comment_num);
            toggle_dynamic_likes = view.findViewById(R.id.toggle_dynamic_likes);
            tv_dynamic_likes_num = view.findViewById(R.id.tv_dynamic_likes_num);

        }
    }
}
