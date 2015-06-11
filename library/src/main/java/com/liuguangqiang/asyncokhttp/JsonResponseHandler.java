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

import com.liuguangqiang.asyncokhttp.json.BaseJsonEngine;

/**
 * <p/>
 * Created by Eric on 15/6/11.
 */
public class JsonResponseHandler<T> extends BaseResponseHandler {

    private Class<T> cls;

    private BaseJsonEngine mJsonEngine;

    private JsonResponseHandler() {
    }

    public JsonResponseHandler(Class<T> jsonObjectClass) {
        cls = jsonObjectClass;
        mJsonEngine = AsyncOkHttp.getInstance().getConfiguration().getJsonEngine();
    }

    public JsonResponseHandler(Class<T> jsonObjectClass, BaseJsonEngine jsonEngine) {
        cls = jsonObjectClass;
        mJsonEngine = jsonEngine;
    }

    @Override
    public void onSuccess(int code, String responseString) {
        T t = parse(responseString);
        if (t != null) {
            onSuccess(t);
        } else {
            onFailure(code, "parse failure");
        }
    }

    public void onSuccess(T result) {

    }

    public T parse(String json) {
        return mJsonEngine.parse(json, cls);
    }

}
