package com.example.trubin23.commitsfromnetwork.data.source.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Andrey on 16.01.2018.
 */

public interface OwnerDao {
    String TABLE_OWNER = "table_owner";

    String COLUMN_OWNER_ID = "id";
    String COLUMN_OWNER_NAME = "name";

    void insertOwner(@NonNull String owner);

    @Nullable
    Long getOwnerId(@NonNull String owner);
}
