package com.ccloudapp.fit403.data.network;

import android.content.Context;

import com.ccloudapp.fit403.BuildConfig;
import com.ccloudapp.fit403.data.model.AuthResponse;
import com.ccloudapp.fit403.data.model.Credentials;
import com.ccloudapp.fit403.data.model.ExerciseName;
import com.ccloudapp.fit403.data.model.Exercise_category;
import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.data.model.UserPublic;
import com.ccloudapp.fit403.data.model.Workout;
import com.ccloudapp.fit403.data.network.interceptors.ServerFailureInterceptor;
import com.ccloudapp.fit403.data.network.interceptors.UnauthorisedInterceptor;
import com.ccloudapp.fit403.data.network.model.RequestFriend;
import com.ccloudapp.fit403.data.network.model.ResponseFriendRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Completable;
import rx.Observable;

/**
 * Created by amit on 14/2/17.
 */

public interface FitnessRestClient {

    String REST_ENDPOINT = "http://development.ccloudapp.com:8080/api/v1/";
    String AUTH_HEADER = "Authorization";


    @GET("users")
    Observable<List<UserPublic>> getUsers(@Header(AUTH_HEADER) String token);

    @POST("users")
    Observable<User> register(@Body User user);

    @POST("users/auth")
    Observable<User> login(@Body Credentials credentials);

    @PUT("users/me")
    Completable updateProfile(@Header(AUTH_HEADER) String token, @Body User user);

    @GET("users/{id}")
    Observable<User> getUser(@Header(AUTH_HEADER) String token, @Path("id") String userId);

    @POST("friendship/me/add_friend")
    Observable<ResponseFriendRequest> addFriend(@Header(AUTH_HEADER) String token,
            @Body RequestFriend requestFriend);

    @PUT("friendship/me/confirm_friend")
    Observable<ResponseFriendRequest> confirmFriend(@Header(AUTH_HEADER) String token,
            @Body RequestFriend requestFriend);

    @PUT("friendship/me/decline_friend")
    Observable<ResponseFriendRequest> declineFriend(@Header(AUTH_HEADER) String token,
            @Body RequestFriend requestFriend);

    @GET("workout/me")
    Observable<List<Workout>> getPreviousWorkouts(@Header(AUTH_HEADER) String token);

    @GET("exercises")
    Single<List<Exercise_category>> getExerciseCategory(@Header(AUTH_HEADER) String token);

    @GET("exercises/{id}")
    Single<List<ExerciseName>> getExerciseName(@Header(AUTH_HEADER) String token, @Path("id") String id);

    @POST("workout/me")
    Completable postNewWorkout(@Header(AUTH_HEADER) String token,@Body Workout workout);

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
