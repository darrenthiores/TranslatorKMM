package com.dev.translatorkmm.translate.data.local.driver

import com.dev.translatorkmm.database.TranslateDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(
            TranslateDatabase.Schema,
            "translate.db"
        )
    }
}