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

package com.liuguangqiang.asyncokhttp.json;

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * Created by Eric on 15/6/11.
 */
public abstract class BaseJsonEngine {

    public abstract <T> T parse(String json, Class<?> cls);

    public boolean isJson(String json) {
        if (TextUtils.isEmpty(json)) return false;

        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

}
