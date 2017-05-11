package com.liuguangqiang.asyncokhttp.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.liuguangqiang.asyncokhttp.AsyncOkHttp;
import com.liuguangqiang.asyncokhttp.JsonResponseHandler;
import com.liuguangqiang.asyncokhttp.sample.entity.TestEntity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AsyncOkHttp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    private void test() {
        AsyncOkHttp.getInstance().addHeader("Header1", "abc");
        AsyncOkHttp.getInstance().addHeader("Header2", "123");
        get();
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

}
