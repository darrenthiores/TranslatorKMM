package com.dev.translatorkmm.translate.data.local.database

import com.dev.translatorkmm.database.TranslateDatabase
import com.squareup.sqldelight.db.SqlDriver

class DatabaseFactory {
    fun createDatabase(driver: SqlDriver): TranslateDatabase {
        return TranslateDatabase(driver)
    }
}