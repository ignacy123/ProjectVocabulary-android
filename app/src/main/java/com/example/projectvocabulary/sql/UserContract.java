package com.example.projectvocabulary.sql;

import android.provider.BaseColumns;

/**
 * Created by ignacy on 28.04.17.
 */

public final class UserContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserContract() {}

    /* Inner class that defines the table contents */
    public static class UserEntry implements BaseColumns {
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
