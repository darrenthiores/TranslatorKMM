package com.dev.translatorkmm.translate.data.local.driver

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}