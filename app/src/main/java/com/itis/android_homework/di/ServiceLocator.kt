package com.itis.android_homework.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.itis.android_homework.db.InceptionDatabase
import com.itis.android_homework.db.migrations.MIGRATION_1_2

object ServiceLocator {

    private var dbInstance: InceptionDatabase? = null

    fun initData(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, InceptionDatabase::class.java, "inception_db").build()
    }

    fun getDbInstance(): InceptionDatabase {
        return dbInstance ?: throw IllegalStateException("Db not initialized")
    }
}