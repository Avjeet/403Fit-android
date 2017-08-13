package com.ccloudapp.fit403.data.network.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by amit on 27/2/17.
 */

public class UnauthorisedInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == 401) {

        }
        return response;
    }
}
