package com.ceshidemo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 类描述：
 * 创建人：
 * 创建时间：
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Data.DataBean> list;
    Context mContext;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(List<Data.DataBean> list, Context context) {
        this.list = list;
        mContext = context;
    }
    //回调条目点击
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
//
//    /**
//     * 没有加泛型<MyAdapter.ViewHolder>
//     */
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = View.inflate(mContext,R.layout.item,null);
//        ViewHolder mholder = new ViewHolder(view);
//        return mholder;
//    }


    /**
     * 加泛型<MyAdapter.ViewHolder>
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.item,null);
        ViewHolder mholder = new ViewHolder(view);
        return mholder;
    }

    /**
     * 加泛型<MyAdapter.ViewHolder>
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Data.DataBean bean = list.get(position);
        Glide.with(mContext).load(bean.getImg()).into(holder.image);
        holder.bt_age.setText(bean.getUserAge() + "");
        holder.bt_job.setText(bean.getOccupation());
        holder.tv_content.setText(bean.getIntroduction());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(holder.itemView,position);
            }
        });

        ObjectAnimator anim = ObjectAnimator
                .ofFloat(holder.itemView, "alpha", 0.0F, 1.0F)
                .setDuration(3000);
        anim.start();
    }

//    /**
//     * 没有加泛型<MyAdapter.ViewHolder>
//     * @return
//     */
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ViewHolder mholder = (ViewHolder) holder;
//        Data.DataBean bean = list.get(position);
//        Glide.with(mContext).load(bean.getImg()).into(mholder.image);
//        mholder.bt_age.setText(bean.getUserAge() + "");
//        mholder.bt_job.setText(bean.getOccupation());
//        mholder.tv_content.setText(bean.getIntroduction());
//
//    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView bt_age, bt_job, tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            bt_age = (TextView) itemView.findViewById(R.id.bt_age);
            bt_job = (TextView) itemView.findViewById(R.id.bt_job);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
