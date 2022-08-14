package com.dilara.spacex.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dilara.spacex.model.Rockets

@Dao
interface RocketDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun upsert(rockets: Rockets):Long

    @Query("SELECT * FROM rockets")
    fun getAllRockets():LiveData<List<Rockets>>

    @Delete
    suspend fun deleteRocket(rockets:Rockets)

    @Query("DELETE FROM rockets WHERE id= :id")
    suspend fun deleteId(id:String)

    @Query("SELECT EXISTS (SELECT 1 FROM rockets WHERE id= :id)")
    fun controlFav(id:String) : Boolean

}