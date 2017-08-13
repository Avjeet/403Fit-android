package com.ccloudapp.fit403.data.network;

import android.content.Context;

import com.ccloudapp.fit403.BuildConfig;
import com.ccloudapp.fit403.data.model.AuthRequest;
import com.ccloudapp.fit403.data.model.AuthResponse;
import com.ccloudapp.fit403.data.network.interceptors.ServerFailureInterceptor;
import com.ccloudapp.fit403.data.network.interceptors.UnauthorisedInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by amit on 14/2/17.
 */

public interface FitnessRestClient {

    String REST_ENDPOINT = "https://api.ccloudapp.com/403FitServerAPI/v1/API.php";
    String AUTH_HEADER = "Authorization";


    @POST("?Command=Login")
    Observable<AuthResponse> login(@Body AuthRequest authRequest);

    class Creator {
        public static FitnessRestClient makeFitApi(Context context) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ?
                    HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new ServerFailureInterceptor())
                    .addInterceptor(new UnauthorisedInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(REST_ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(FitnessRestClient.class);
        }
    }
}
