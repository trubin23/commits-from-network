package com.example.trubin23.commitsfromnetwork.data.source.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Andrey on 23.01.2018.
 */

public interface RepoPreferences {

    String OWNER_VALUE = "owner_value";
    String REPO_VALUE = "repo_value";

    void putString(@NonNull String key, @NonNull String value);

    @Nullable
    String getString(@NonNull String key);
}
