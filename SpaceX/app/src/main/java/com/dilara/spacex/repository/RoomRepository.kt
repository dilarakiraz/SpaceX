package com.dilara.spacex.repository

import com.dilara.spacex.data.db.RocketDatabase
import com.dilara.spacex.model.Rockets
import javax.inject.Inject

class RoomRepository @Inject constructor(db: RocketDatabase){

    private val dao=db.getRocketDao()


    suspend fun upsertRockets(rockets: Rockets) = dao.upsert(rockets)
    fun getSavedRockets() = dao.getAllRockets()
    suspend fun deleteRocket(rockets:Rockets)=dao.deleteRocket(rockets)
    fun controlFav(id:String)=dao.controlFav(id)
    suspend fun deleteId(id: String)=dao.deleteId(id)
}