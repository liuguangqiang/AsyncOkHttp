AsyncOkHttp
===========
An Android HTTP Library with [OkHttp](https://github.com/square/okhttp). 

##Why use OkHttp?
OkHttp is an HTTP client that’s efficient by default:

* HTTP/2 and SPDY support allows all requests to the same host to share a socket.
* Connection pooling reduces request latency (if SPDY isn’t available).
* Transparent GZIP shrinks download sizes.
* Response caching avoids the network completely for repeat requests.

##Features
* Asynchronous HTTP requests.
* Perform HTTP requests with OkHttp.
* Parse json string with Gson or LoganSquare.
* Optional or customize json engine.

##Examples
### Get
    AsyncOkHttp.getInstance().get(url, new BaseResponseHandler() {
        @Override
        public void onSuccess(int code, String responseString) {
			//TODO
        }
    });
    
### POST
    AsyncOkHttp.getInstance().post(url, params, new BaseResponseHandler() {
        @Override
        public void onSuccess(int code, String responseString) {
			//TODO
        }
    });

##JSON
AsyncOkHttp allows user to set json engine for parsing json string.

* [Gson](https://github.com/google/gson)
* [LoganSquare](https://github.com/bluelinelabs/LoganSquare)

###[LoganSquare Sample Model](https://github.com/bluelinelabs/LoganSquare/blob/master/docs/AnnotationsOnlyModel.md)
    @JsonObject
    public class TestEntity {

        @JsonField
        public int id;

    }

###Gson Sample Model
    public class TestEntity {

        public int id;

    }

##Usage

###Gradle
```
dependencies {
   	compile 'com.github.liuguangqiang.asyncokhttp:library:0.0.1'
}
```

###Maven
```
<dependency>
  	<groupId>com.github.liuguangqiang.swipeback</groupId>
  	<artifactId>asyncokhttp</artifactId>
  	<version>0.0.1</version>
  	<type>aar</type>
</dependency>
```

##License

    Copyright 2015 Eric Liu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.