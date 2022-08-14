package com.dilara.spacex.di

import android.content.Context
import androidx.room.Room
import com.dilara.spacex.data.db.RocketDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RocketDatabase {
        return Room.databaseBuilder(
            context,
            RocketDatabase::class.java,
            "rocket_db.db"
        ).fallbackToDestructiveMigration().build()
    }
}
