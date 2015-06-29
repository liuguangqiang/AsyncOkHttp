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

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Perform a http request.
 * <p>
 * Created by Eric on 15/6/10.
 */
public class RequestTask implements Runnable {

    private OkHttpClient mClient;
    private Request mRequest;
    private BaseResponseHandler mResponseHandler;

    @Override
    public void run() {
        execute();
    }

    public RequestTask(OkHttpClient client, Request request, BaseResponseHandler responseHandler) {
        mClient = client;
        mRequest = request;
        mResponseHandler = responseHandler;
    }

    public void execute() {
        try {
            Response response = mClient.newCall(mRequest).execute();
            mResponseHandler.sendStart();
            int code = response.code();
            String responseString = "without response body";
            if (response.body() != null)
                responseString = response.body().string();

            if (response.isSuccessful())
                mResponseHandler.sendSuccess(code, responseString);
            else
                mResponseHandler.sendFailure(code, responseString);
        } catch (IOException e) {
            String error = "unknown";
            if (e.getMessage() != null) error = e.getMessage();

            if (error.equals("Canceled"))
                mResponseHandler.sendCancel();
            else
                mResponseHandler.sendFailure(0, error);
        }
    }

}
