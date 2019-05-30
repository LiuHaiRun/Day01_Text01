package com.example.liuhairui.day01_text01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.liuhairui.day01_text01.adapter.MyAdapter;
import com.example.liuhairui.day01_text01.api.IMyService;
import com.example.liuhairui.day01_text01.bean.ResultsBean;
import com.example.liuhairui.day01_text01.bean.WelfareData;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItem {


    private XRecyclerView mXrv;
    private ArrayList<ResultsBean> mList;
    private MyAdapter mAdapter;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initData() {
        //获取网络数据
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IMyService.url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(IMyService.class)
                .getData()
                .subscribeOn(Schedulers.io())//观察者模式：被观察者在子线程获取网络数据
                .observeOn(AndroidSchedulers.mainThread())//观察者结果返回主线程
                .subscribe(new Observer<WelfareData>() {//被观察者订阅观察者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WelfareData welfareData) {
                           if (welfareData != null){
                               List<ResultsBean> results = welfareData.getResults();
                               //结果提交到集合
                               mList.addAll(results);
                               mAdapter.notifyDataSetChanged();
                           }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "网络请求失败onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        mXrv = (XRecyclerView) findViewById(R.id.xrv);
        //容器
        mList = new ArrayList<>();
        //布局管理器
        mXrv.setLayoutManager(new LinearLayoutManager(this));
        //分割线
        mXrv.addItemDecoration(new DividerItemDecoration(this,LinearLayout.VERTICAL));
        //适配器
        mAdapter = new MyAdapter(this, mList);
        mXrv.setAdapter(mAdapter);
        //接口回调
        mAdapter.setOnItem(this);
    }

    @Override
    public void OnLongItem(View view, int position) {
        //跳转到Mian2Activity页面展示图片
        Intent intent = new Intent(this, Main2Activity.class);
        //传集合
        intent.putExtra("list",(ArrayList<ResultsBean>)mList);
        //启动
        startActivity(intent);
    }
}
