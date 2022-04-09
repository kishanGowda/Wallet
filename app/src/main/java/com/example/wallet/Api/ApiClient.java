package com.example.wallet.Api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit = null;
    public static final String BASE_URL = "https://test.theclassroom.biz/api/";
    public static Retrofit retrofitPin = null;
    public static final String BASE_URL_PIN = "https://api.postalpincode.in/pincode/";

    public static Retrofit getRetrofit() {

        if (retrofit == null) {

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitPin() {

        if (retrofitPin == null) {

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

            retrofitPin = new Retrofit.Builder()
                    .baseUrl(BASE_URL_PIN)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitPin;
    }

    public static LoginService getApiService() {
        LoginService apiService = getRetrofit().create(LoginService.class);
        return apiService;
    }
    public static LoginService getApiServicePin() {
        LoginService apiServicePin = getRetrofitPin().create(LoginService.class);
        return apiServicePin;
    }
}
