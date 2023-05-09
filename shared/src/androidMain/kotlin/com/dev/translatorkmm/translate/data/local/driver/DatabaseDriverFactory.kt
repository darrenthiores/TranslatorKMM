package com.dev.translatorkmm.translate.data.local.driver

import android.content.Context
import com.dev.translatorkmm.database.TranslateDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context
){
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            TranslateDatabase.Schema,
            context,
            "translate.db"
        )
    }
}