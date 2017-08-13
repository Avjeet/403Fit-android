package com.ccloudapp.fit403.data.network.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by amit on 15/2/17.
 */

public class ServerFailureInterceptor implements Interceptor {

    private static final String MSG =
            "We are facing some technical issues. Please try after some time.";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.code() == 503) {
            //TODO: Add a server error event here
        }
        return response;
    }
}
