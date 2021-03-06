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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Eric on 15/6/11.
 */
public class GsonEngine extends BaseJsonEngine {

    /**
     * exclude fields without expose annotation
     */
    private boolean enableExpose = false;

    public GsonEngine() {
    }

    public GsonEngine(boolean enableExpose) {
        this.enableExpose = enableExpose;
    }

    public void setEnableExpose(boolean enableExpose) {
        this.enableExpose = enableExpose;
    }

    @Override
    public <T> T parse(String json, Class<?> cls) {
        return (T) newGson().fromJson(json, cls);
    }

    private Gson newGson() {
        return enableExpose ? new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() : new Gson();
    }

}
