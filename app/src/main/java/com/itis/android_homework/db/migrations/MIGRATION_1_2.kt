package com.itis.android_homework.db.migrations

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MIGRATION_1_2 : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        try {
            db.execSQL("ALTER TABLE 'users' ADD COLUMN 'address' TEXT NOT NULL DEFAULT 'sample'")
        } catch (ex: Exception) {
            Log.e("DB_MIGRATION", "Error occurred: " + ex.localizedMessage)
        }
    }
}