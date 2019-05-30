package com.example.liuhairui.day01_text01;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.liuhairui.day01_text01.adapter.VpAdapter;
import com.example.liuhairui.day01_text01.bean.ResultsBean;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private ViewPager mVp;
    private ArrayList<ResultsBean> mList;
    private ArrayList<View> mViews;
    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //获取传递的集合
        mList = getIntent().getParcelableArrayListExtra("list");

        initView();
    }

    private void initView() {
        mVp = (ViewPager) findViewById(R.id.vp);
        mViews = new ArrayList<>();
        //循环集合获取对象中的图片网址
        for (int i = 0; i < mList.size(); i++) {
            String url = mList.get(i).getUrl();
            //获取子布局
            View view = View.inflate(this, R.layout.item_vp_layout, null);
            mImg = (ImageView) view.findViewById(R.id.img);
            //将图片网址转换为图片添加到子布局转中
            Glide.with(this).load(url).into(mImg);
            mViews.add(view);
        }
        //适配器
        VpAdapter vpAdapter = new VpAdapter(mViews);
        mVp.setAdapter(vpAdapter);

    }
}
