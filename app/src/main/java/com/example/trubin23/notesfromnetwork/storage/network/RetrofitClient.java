package com.example.trubin23.notesfromnetwork.storage.network;

import android.support.annotation.NonNull;
import com.example.trubin23.notesfromnetwork.storage.model.NoteStorage;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by Andrey on 31.12.2017.
 */

public class RetrofitClient {

    private static final String TAG = RetrofitClient.class.getSimpleName();
    private static final String BASE_URL = "https://api.github.com/";

    private static SOService mSOService = null;

    @NonNull
    private static SOService getSOService() {
        if(mSOService == null) {
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

    public static void getNotes(@NonNull String repoName, @NonNull Callback<List<NoteStorage>> callback) {
        SOService soService = getSOService();
        soService.getNotes(repoName).enqueue(callback);
    }
}
