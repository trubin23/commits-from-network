package com.example.trubin23.commitsfromnetwork.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trubin23.commitsfromnetwork.data.Commit;

import java.util.List;

/**
 * Created by Andrey on 23.01.2018.
 */

public interface CommitsDataSource {

    interface LoadCommitsCallback {

        void onCommitsLoaded(List<Commit> commits);

        void onDataNotAvailable();
    }

    interface GetPreferenceCallback {

        void onPreference(String value);
    }

    void savePreference(@NonNull String key, @NonNull String value);

    void getPreference(@NonNull String key, @NonNull GetPreferenceCallback callback);

    void getCommitsDb(@NonNull String owner, @NonNull String repo,
                              @NonNull LoadCommitsCallback callback);

    void insertCommitsDb(@NonNull List<Commit> commits, @NonNull String owner,
                         @NonNull String repo);

    void getCommitsNetwork(@NonNull String owner, @NonNull String repo,
                           @Nullable Integer pageNumber, @Nullable Integer pageSize,
                           @NonNull LoadCommitsCallback callback);
}
