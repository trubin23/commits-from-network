package com.example.trubin23.commitsfromnetwork.storage.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trubin23.commitsfromnetwork.storage.model.OwnerStorage;

/**
 * Created by Andrey on 16.01.2018.
 */

public interface OwnerDao {
    String TABLE_OWNER = "table_owner";

    String COLUMN_OWNER_ID = "id";
    String COLUMN_OWNER_NAME = "date";

    void insertOwner(@NonNull String owner);

    @Nullable
    OwnerStorage getOwner(@NonNull String owner);
}
