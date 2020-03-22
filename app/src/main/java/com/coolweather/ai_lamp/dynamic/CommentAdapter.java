package com.coolweather.ai_lamp.dynamic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coolweather.ai_lamp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    List<CommentBean> mList;
    Context mContext;
    private ButtonInterface buttonInterface;

    public CommentAdapter(Context context, List<CommentBean> list){
        mContext = context;
        mList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        //长按删除
        viewHolder.tv_comment_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int pos = viewHolder.getAdapterPosition();
                /*
                int id = UserChangerUtil.getUserId(mContext);
                Log.e("TAG", "pos: "+pos);
                Log.e("TAG", "id: "+id);
                Log.e("TAG", "mList.get(pos).getUid(): "+mList.get(pos).getUid());
                id = 38;
                if (38 == id){//只有自己才能删除自己的评论,暂时都可以删
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("评论删除")
                            .setMessage("是否删除你的评论？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    buttonInterface.onclick(null, pos);
                                }
                            })
                            .setNegativeButton("取消", null);
                    builder.show();
                }
                */
                return false;
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CommentBean comment = mList.get(i);
        //int id = comment.getUid();
        /*
        UserBaseInfo userBaseInfo = UsersCache.getUserBaseInfo(id);
        if (userBaseInfo != null){
            if (userBaseInfo.getImagePath() != null){
                Glide.with(mContext).load(userBaseInfo.getImagePath()).into(viewHolder.civ_comment_head);
            }else {
                viewHolder.civ_comment_head.setImageResource(R.drawable.head);
            }
            viewHolder.tv_comment_name.setText(userBaseInfo.getName());
        }
         */

        viewHolder.tv_comment_content.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView civ_comment_head;
        TextView tv_comment_name;
        TextView tv_comment_content;

        public ViewHolder(View view){
            super(view);
            civ_comment_head = view.findViewById(R.id.civ_comment_head);
            tv_comment_name = view.findViewById(R.id.tv_comment_name);
            tv_comment_content = view.findViewById(R.id.tv_comment_content);
        }
    }

    public void setButtonOnClick(ButtonInterface buttonInterface){
        this.buttonInterface = buttonInterface;
    }

}
