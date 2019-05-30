package com.example.liuhairui.day01_text01.api;

import com.example.liuhairui.day01_text01.bean.WelfareData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface IMyService {

//    福利：
//    http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1
//    GET方式：无参
//    请求头设置：Content-Type:application/x-www-form-urlencoded

    public String url = "http://gank.io/api/data/";

    @GET("%E7%A6%8F%E5%88%A9/20/1")
    @Headers({"Content-Type:application/x-www-form-urlencoded"})
    Observable<WelfareData> getData();

}
