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
import com.liuguangqiang.asyncokhttp.json.GsonEngine;
import com.squareup.okhttp.Headers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Eric on 15/6/11.
 */
public class Configuration {

    private ExecutorService threadPool;
    private BaseJsonEngine jsonEngine;
    private long connectTimeout;
    private long readTimeout;
    private Headers.Builder headersBuilder;

    public BaseJsonEngine getJsonEngine() {
        return jsonEngine;
    }

    public void setJsonEngine(BaseJsonEngine jsonEngine) {
        this.jsonEngine = jsonEngine;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Headers.Builder getHeadersBuilder() {
        return headersBuilder;
    }

    public void setHeadersBuilder(Headers.Builder headersBuilder) {
        this.headersBuilder = headersBuilder;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public Configuration(Builder builder) {
        threadPool = builder.threadPool;
        jsonEngine = builder.jsonEngine;
        connectTimeout = builder.connectTimeout;
        readTimeout = builder.readTimeout;
        headersBuilder = builder.headersBuilder;
    }

    public static Configuration createDefault() {
        return new Builder().build();
    }

    public static class Builder {

        private BaseJsonEngine jsonEngine;
        private long connectTimeout;
        private long readTimeout;
        private Headers.Builder headersBuilder;
        private ExecutorService threadPool;

        public Builder() {
            threadPool = Executors.newCachedThreadPool();
            jsonEngine = new GsonEngine();
            connectTimeout = 30;
            readTimeout = 30;
            headersBuilder = new Headers.Builder();
            headersBuilder.add("User-Agent", Constants.USER_AGENT);
        }

        public Builder ExecutorService(ExecutorService executorService) {
            this.threadPool = executorService;
            return this;
        }

        public Builder jsonEngine(BaseJsonEngine jsonEngine) {
            this.jsonEngine = jsonEngine;
            return this;
        }

        public Builder connectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder readTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder headers(Headers.Builder header) {
            this.headersBuilder = header;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }

    }

}
