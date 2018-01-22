package com.example.trubin23.commitsfromnetwork.storage.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trubin23.commitsfromnetwork.storage.model.load.CommitLoad;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Andrey on 31.12.2017.
 */

public class RetrofitClient {

    private static final String BASE_URL = "https://api.github.com/";

    private static SOService mSOService = null;

    @NonNull
    private static SOService getSOService() {
        if (mSOService == null) {
            OkHttpClient httpClient = new OkHttpClient().newBuilder().addInterceptor(
                    chain -> {
                        Request request = chain.request();
                        Request newRequest = request.newBuilder()
                                .addHeader("User-Agent", "test-agent")
                                .build();

                        return chain.proceed(newRequest);
                    }
            ).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            mSOService = retrofit.create(SOService.class);
        }
        return mSOService;
    }

    public static void getCommits(@NonNull String owner, @NonNull String repo,
                                  @Nullable Integer pageNumber, @Nullable Integer pageSize,
                                  @NonNull Callback<List<CommitLoad>> callback) {
        SOService soService = getSOService();
        if (pageNumber == null) {
            soService.getCommits(owner, repo).enqueue(callback);
        } else {
            soService.getPageCommits(owner, repo, pageNumber, pageSize).enqueue(callback);
        }
    }
}
