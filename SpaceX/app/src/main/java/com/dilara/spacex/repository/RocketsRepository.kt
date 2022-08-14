package com.dilara.spacex.repository


import com.dilara.spacex.data.remote.ServiceAPI
import javax.inject.Inject

class RocketsRepository @Inject constructor(
    private val retrofitApi : ServiceAPI) {

    suspend fun getAllRockets() = retrofitApi.getAllRockets()
    suspend fun getRocketDetail(id:String)= retrofitApi.getRocketDetail(id)
    suspend fun getUpcomingLaunches() =retrofitApi.getUpcomingLaunches()
    suspend fun getLaunchDetail(id:String)= retrofitApi.getLaunchDetail(id)
}