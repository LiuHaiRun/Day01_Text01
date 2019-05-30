package com.example.liuhairui.day01_text01.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liuhairui.day01_text01.R;
import com.example.liuhairui.day01_text01.bean.ResultsBean;
import com.example.liuhairui.day01_text01.bean.WelfareData;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class MyAdapter extends XRecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ResultsBean> mList;

    public MyAdapter(Context context, ArrayList<ResultsBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载布局
        View view = View.inflate(mContext, R.layout.item_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //加载数据
        ResultsBean resultsBean = mList.get(position);

        holder.tv.setText(resultsBean.get_id());
        Glide.with(mContext).load(resultsBean.getUrl()).into(holder.img);
        //子条目点击数据
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //调用接口传值
                mOnItem.OnLongItem(v,position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tv = itemView.findViewById(R.id.tv);
        }
    }
    //定义接口
    private OnItem mOnItem;
    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }
    public interface  OnItem{
        void OnLongItem(View view,int position);
    }
}
