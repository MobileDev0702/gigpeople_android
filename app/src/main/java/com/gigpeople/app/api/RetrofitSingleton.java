package com.gigpeople.app.api;



import com.gigpeople.app.utils.GlobalMethods;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton{

    private static HttpLoggingInterceptor logging;
    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(GlobalMethods.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <T> T createService(Class<T> serviceClass) {
        //Set Logging
        if (logging == null) {
            logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            httpClient.retryOnConnectionFailure(true);
            httpClient.connectTimeout(5
                    , TimeUnit.MINUTES);
            httpClient.readTimeout(5, TimeUnit.MINUTES);
        }

        retrofit = builder.client(httpClient.retryOnConnectionFailure(true).build()).build();

        return retrofit.create(serviceClass);
    }
}
