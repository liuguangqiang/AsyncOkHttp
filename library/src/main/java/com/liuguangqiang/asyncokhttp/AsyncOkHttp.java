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

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Eric on 15/6/10.
 */
public class AsyncOkHttp {

    private static final String TAG = "AsyncOkHttp";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private volatile static AsyncOkHttp instance;

    private final OkHttpClient mHttpClient;

    private Configuration mConfiguration;
    private ExecutorService mThreadPool;
    private Headers.Builder mHeadersBuilder;

    private AsyncOkHttp() {
        mHttpClient = new OkHttpClient();

        init(Configuration.createDefault());
    }

    public static AsyncOkHttp getInstance() {
        if (instance == null) {
            synchronized (AsyncOkHttp.class) {
                if (instance == null) {
                    instance = new AsyncOkHttp();
                }
            }
        }
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        return mHttpClient;
    }

    public void init(Configuration configuration) {
        if (configuration == null)
            throw new IllegalArgumentException(
                    "AsyncOkHttp can not be initialized with null");

        mConfiguration = configuration;

        mThreadPool = mConfiguration.getThreadPool();
        mHeadersBuilder = mConfiguration.getHeadersBuilder();
        mHttpClient.setConnectTimeout(mConfiguration.getConnectTimeout(), TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(mConfiguration.getReadTimeout(), TimeUnit.SECONDS);
    }

    public Configuration getConfiguration() {
        return mConfiguration;
    }

    /**
     * Add a header for all requests.
     *
     * @param name  the name of header.
     * @param value the value of header.
     */
    public void addHeader(String name, String value) {
        mHeadersBuilder.add(name, value);
    }

    /**
     * Remove a header for all requests.
     *
     * @param name the name of header.
     */
    public void removeHeader(String name) {
        mHeadersBuilder.removeAll(name);
    }

    //************************************ HEAD ************************************

    /**
     * Perform HTTP HEAD request with a RequestParams.
     *
     * @param url
     * @param params
     * @param responseHandler
     */
    public void head(String url, RequestParams params, BaseResponseHandler responseHandler) {
        head(params.toQueryString(url), responseHandler);
    }

    /**
     * Perform HTTP HEAD request.
     *
     * @param url             the URL of HTTP request.
     * @param responseHandler the callback of the response.
     */
    public void head(String url, BaseResponseHandler responseHandler) {
        Request.Builder requestBuilder = createRequestBuilder(url).head();
        submitRequest(requestBuilder, responseHandler);
    }

    //************************************ GET ************************************

    /**
     * Perform HTTP GET request with a RequestParams.
     *
     * @param url             the URL of HTTP request.
     * @param params          the parameter of Request Body.
     * @param responseHandler the callback of the response.
     */
    public void get(String url, RequestParams params, BaseResponseHandler responseHandler) {
        get(params.toQueryString(url), responseHandler);
    }

    /**
     * Perform HTTP GET request.
     *
     * @param url             the URL of HTTP request.
     * @param responseHandler the callback of the response.
     */
    public void get(String url, BaseResponseHandler responseHandler) {
        Request.Builder requestBuilder = createRequestBuilder(url).get();
        submitRequest(requestBuilder, responseHandler);
    }

    //************************************ POST ************************************

    /**
     * Perform HTTP POST request with a JSON string.
     *
     * @param url             the URL of HTTP request.
     * @param json            the parameter of Request Body.
     * @param responseHandler the callback of the response.
     */
    public void post(String url, String json, BaseResponseHandler responseHandler) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        post(url, requestBody, responseHandler);
    }

    /**
     * Perform HTTP POST request with a RequestParams.
     *
     * @param url             the URL of HTTP request.
     * @param params          the parameter of Request Body.
     * @param responseHandler the callback of the response.
     */
    public void post(String url, RequestParams params, BaseResponseHandler responseHandler) {
        RequestBody requestBody = params.toRequestBody();
        post(url, requestBody, responseHandler);
    }

    /**
     * Perform HTTP POST request with a RequestBody.
     *
     * @param url             the URL of HTTP request.
     * @param requestBody     the Request Body
     * @param responseHandler the callback of the response.
     */
    public void post(String url, RequestBody requestBody, BaseResponseHandler responseHandler) {
        Request.Builder requestBuilder = createRequestBuilder(url).post(requestBody);
        submitRequest(requestBuilder, responseHandler);
    }

    //************************************ PUT ************************************

    /**
     * Perform HTTP PUT request with a JSON string.
     *
     * @param url             the URL of HTTP request.
     * @param json            the parameter of Request Body.
     * @param responseHandler the callback of the response.
     */
    public void put(String url, String json, BaseResponseHandler responseHandler) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        put(url, requestBody, responseHandler);
    }

    /**
     * Perform HTTP PUT request with a RequestParams.
     *
     * @param url             the URL of HTTP request.
     * @param params          the parameter of Request Body.
     * @param responseHandler the callback of the response.
     */
    public void put(String url, RequestParams params, BaseResponseHandler responseHandler) {
        RequestBody requestBody = params.toRequestBody();
        put(url, requestBody, responseHandler);
    }

    /**
     * Perform HTTP PUT request with a RequestBody.
     *
     * @param url             the URL of HTTP request.
     * @param requestBody     the Request Body
     * @param responseHandler the callback of the response.
     */
    public void put(String url, RequestBody requestBody, BaseResponseHandler responseHandler) {
        Request.Builder requestBuilder = createRequestBuilder(url).put(requestBody);
        submitRequest(requestBuilder, responseHandler);
    }

    //************************************ DELETE ************************************

    /**
     * Perform HTTP DELETE request with a RequestParams.
     *
     * @param url             the URL of HTTP request.
     * @param params          the parameter of Request Body.
     * @param responseHandler the callback of the response.
     */
    public void delete(String url, RequestParams params, BaseResponseHandler responseHandler) {
        delete(params.toQueryString(url), responseHandler);
    }

    /**
     * Perform HTTP DELETE request.
     *
     * @param url             the URL of HTTP request.
     * @param responseHandler the callback of the response.
     */
    public void delete(String url, BaseResponseHandler responseHandler) {
        Request.Builder request = createRequestBuilder(url).delete();
        submitRequest(request, responseHandler);
    }

    /**
     * Create a Request.Builder.
     *
     * @param url the URL of HTTP request.
     * @return
     */
    private Request.Builder createRequestBuilder(String url) {
        Request.Builder builder = new Request.Builder().url(url).tag(url).headers(mHeadersBuilder.build());
        return builder;
    }

    /**
     * Cancels all scheduled tasks tagged with tag.
     * Requests that are already complete cannot be canceled.
     *
     * @param url the URL of HTTP request.
     */
    public void cancel(String url) {
        mHttpClient.cancel(url);
    }

    /**
     * Submit a Runnable task to thread pool.
     *
     * @param builder
     * @param responseHandler the callback of the response.
     */
    public void submitRequest(Request.Builder builder, BaseResponseHandler responseHandler) {
        submitRequest(builder.build(), responseHandler);
    }

    /**
     * Submit a Runnable task to thread pool.
     *
     * @param request
     * @param responseHandler the callback of the response.
     */
    public void submitRequest(Request request, BaseResponseHandler responseHandler) {
        RequestTask task = new RequestTask(mHttpClient, request, responseHandler);
        mThreadPool.submit(task);
    }

}
