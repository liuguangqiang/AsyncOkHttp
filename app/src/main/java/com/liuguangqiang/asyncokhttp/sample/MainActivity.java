package com.liuguangqiang.asyncokhttp.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.liuguangqiang.asyncokhttp.AsyncOkHttp;
import com.liuguangqiang.asyncokhttp.RequestParams;
import com.liuguangqiang.asyncokhttp.BaseResponseHandler;
import com.liuguangqiang.asyncokhttp.JsonResponseHandler;
import com.liuguangqiang.asyncokhttp.sample.entity.TestEntity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AsyncOkHttp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get();
        put();
    }

    public void get() {
        String url = "http://news-at.zhihu.com/api/4/news/latest";

        AsyncOkHttp.getInstance().get(url, new JsonResponseHandler<TestEntity>(TestEntity.class) {

            @Override
            public void onSuccess(TestEntity result) {
                if (result != null) {
                    Log.i(TAG, "date:" + result.date);
                }
            }

            @Override
            public void onFailure(int code, String responseString) {
                Log.i(TAG, "onFailure:" + responseString);
            }
        });
    }

    public void put() {
        String url = "http://staging.api.fishsaying.com/authenticates.json";
        RequestParams params = new RequestParams();
        params.put("authorize", "eric");
        params.put("password", "yushuo@2013");

        AsyncOkHttp.getInstance().put(url, params, new BaseResponseHandler() {
            @Override
            public void onSuccess(int code, String responseString) {
                Log.i(TAG, "onSuccess-code:" + code);
                Log.i(TAG, "onSuccess:" + responseString);
            }

            @Override
            public void onFailure(int code, String responseString) {
                Log.i(TAG, "onFailure-code:" + code);
                Log.i(TAG, "onFailure:" + responseString);
            }
        });
    }

}
