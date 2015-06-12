/*
 * Copyright 2015 Eric Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liuguangqiang.asyncokhttp;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Eric on 15/6/10.
 */
public class AsyncOkHttp {

    private static final String TAG = "AsyncOkHttp";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static AsyncOkHttp instance = new AsyncOkHttp();

    private ExecutorService mThreadPool;

    private OkHttpClient mHttpClient;

    private Configuration mConfiguration;

    private AsyncOkHttp() {
        mThreadPool = Executors.newCachedThreadPool();
        mHttpClient = new OkHttpClient();

        setConfiguration(new Configuration.Builder().build());
    }

    public static AsyncOkHttp getInstance() {
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        return mHttpClient;
    }

    public Configuration getConfiguration() {
        return mConfiguration;
    }

    public void setConfiguration(Configuration configuration) {
        mConfiguration = configuration;

        mHttpClient.setConnectTimeout(mConfiguration.getConnectTimeout(), TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(mConfiguration.getReadTimeout(), TimeUnit.SECONDS);
    }

    //**************************** GET ****************************

    public void get(String url, RequestParams params, BaseResponseHandler responseHandler) {
        get(params.toQueryString(url), responseHandler);
    }

    public void get(String url, BaseResponseHandler responseHandler) {
        Log.i(TAG, url);
        Request request = new Request.Builder().url(url).tag(url).build();
        submitRequest(request, responseHandler);
    }

    //**************************** POST ****************************

    /**
     * Post JSON to server.
     *
     * @param url
     * @param json
     * @param responseHandler
     */
    public void post(String url, String json, BaseResponseHandler responseHandler) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        post(url, requestBody, responseHandler);
    }

    /**
     * Post parameters to server.
     *
     * @param url
     * @param params
     * @param responseHandler
     */
    public void post(String url, RequestParams params, BaseResponseHandler responseHandler) {
        RequestBody requestBody = params.toRequestBody();
        post(url, requestBody, responseHandler);
    }

    public void post(String url, RequestBody requestBody, BaseResponseHandler responseHandler) {
        Request request = new Request.Builder().url(url).tag(url).post(requestBody).build();
        submitRequest(request, responseHandler);
    }

    //**************************** PUT ****************************

    public void put(String url, String json, BaseResponseHandler responseHandler) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        put(url, requestBody, responseHandler);
    }

    public void put(String url, RequestParams params, BaseResponseHandler responseHandler) {
        RequestBody requestBody = params.toRequestBody();
        put(url, requestBody, responseHandler);
    }

    public void put(String url, RequestBody requestBody, BaseResponseHandler responseHandler) {
        Request request = new Request.Builder().url(url).tag(url).put(requestBody).build();
        submitRequest(request, responseHandler);
    }

    //**************************** DELETE ****************************

    public void delete(String url, RequestParams params, BaseResponseHandler responseHandler) {
        delete(params.toQueryString(url), responseHandler);
    }

    public void delete(String url, BaseResponseHandler responseHandler) {
        Request request = new Request.Builder().url(url).tag(url).delete().build();
        submitRequest(request, responseHandler);
    }

    /**
     * Cancels all scheduled tasks tagged with tag.
     * Requests that are already complete cannot be canceled.
     *
     * @param url
     */
    public void cancel(String url) {
        mHttpClient.cancel(url);
    }

    /**
     * Submits a Runnable task to ExecutorService.
     *
     * @param request
     * @param responseHandler
     */
    private void submitRequest(Request request, BaseResponseHandler responseHandler) {
        RequestTask task = new RequestTask(mHttpClient, request, responseHandler);
        mThreadPool.submit(task);
    }

}
