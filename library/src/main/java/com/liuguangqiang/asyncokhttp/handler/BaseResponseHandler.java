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

package com.liuguangqiang.asyncokhttp.handler;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Eric on 15/6/10.
 */
public abstract class BaseResponseHandler {

    private static final int START = 0;

    private static final int SUCCESS = 1;

    private static final int FINISH = 2;

    private static final int FAILURE = 3;

    private static final int CANCLE = 4;

    public void sendStart() {
        sendMessage(START);
    }

    public void sendSuccess(int code, String responseString) {
        sentMessage(SUCCESS, code, responseString);
    }

    public void sendFailure(int code, String responseString) {
        sentMessage(FAILURE, code, responseString);
    }

    public void sendFinished() {
        sendMessage(FINISH);
    }

    public void sendMessage(int what) {
        sentMessage(what, -1, null);
    }

    public void sendMessage(int what, int code) {
        sentMessage(what, code, null);
    }

    public void sentMessage(int what, int code, String responseString) {
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
                    break;
            }
        }
    };

    protected abstract void onStart();

    protected abstract void onSuccess(int code, String result);

}
