package com.dilara.spacex.data.db

import androidx.room.*
import com.dilara.spacex.model.Rockets

@Database(
    entities = [Rockets::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RocketDatabase : RoomDatabase(){

    abstract fun getRocketDao(): RocketDao

}
