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

import android.os.Handler;
import android.os.Message;

/**
 * Created by Eric on 15/6/10.
 */
public abstract class BaseResponseHandler {

    private static final int START = 0;

    private static final int SUCCESS = 1;

    private static final int FAILURE = 2;

    private static final int CANCEL = 3;

    public void sendStart() {
        sendMessage(START);
    }

    public void sendSuccess(int code, String responseString) {
        sendMessage(SUCCESS, code, responseString);
    }

    public void sendFailure(int code, String responseString) {
        sendMessage(FAILURE, code, responseString);
    }

    public void sendCancel() {
        sendMessage(CANCEL);
    }

    private void sendMessage(int what) {
        sendMessage(what, -1);
    }

    private void sendMessage(int what, int code) {
        sendMessage(what, code, null);
    }

    private void sendMessage(int what, int code, String responseString) {
        Message message = mHandler.obtainMessage();
        message.what = what;
        if (code >= 0)
            message.arg1 = code;
        if (responseString != null)
            message.obj = responseString;
        message.sendToTarget();
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START:
                    onStart();
                    break;
                case SUCCESS:
                    onSuccess(msg.arg1, msg.obj.toString());
                    onFinish();
                    break;
                case FAILURE:
                    onFailure(msg.arg1, msg.obj.toString());
                    onFinish();
                    break;
                case CANCEL:
                    onCancel();
                    onFinish();
                    break;
            }
        }
    };

    public void onStart() {
    }

    public void onFinish() {
    }

    public void onCancel() {
    }

    public void onFailure(int code, String responseString) {
    }

    public abstract void onSuccess(int code, String responseString);

}
