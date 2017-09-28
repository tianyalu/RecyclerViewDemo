package com.sty.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sty.recyclerview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by shity on 2017/9/27/0027.
 */

public class StaggerGridRecyclerViewAdapter extends RecyclerView.Adapter<StaggerGridRecyclerViewAdapter.MyViewHolder>{
    private List<String> mDatas;
    private List<Integer> mHeights;
    private LayoutInflater mInflater;

    private MyRecyclerViewAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(MyRecyclerViewAdapter.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public StaggerGridRecyclerViewAdapter(Context context, List<String> mDatas){
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.mHeights = new ArrayList<>();
        for(int i = 0; i < mDatas.size(); i++){
            mHeights.add(generateRandomNumber(100, 400));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.layout_item_stagger, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));

        //holder.tv.setHeight(randomHeight); //此种设置方式不起作用
        ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.tv.setLayoutParams(lp);

        //如果设置了回调，则设置点击事件
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mDatas != null){
            return mDatas.size();
        }else {
            return 0;
        }
    }

    /**
     * 生成min,max之间的随机数
     * @param min
     * @param max
     * @return
     */
    private int generateRandomNumber(int min, int max){
        Random random = new Random();
        int num = random.nextInt(max) % (max - min + 1) + min;
        return num;
    }

    public void addData(int position){
        mDatas.add(position, "Insert One");
        mHeights.add(generateRandomNumber(100, 400));
        notifyItemInserted(position);
    }

    public void deleteData(int position){
        mDatas.remove(position);
        mHeights.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.id_num);
        }
    }
}
