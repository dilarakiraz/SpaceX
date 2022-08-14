package com.dilara.spacex.data.remote

import com.dilara.spacex.model.Rockets
import com.dilara.spacex.model.upcoming_launches.UpcomingLaunches
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceAPI {
    @GET("rockets")
     suspend fun getAllRockets():List<Rockets>

    @GET("rockets/{id}")
     suspend fun getRocketDetail(@Path("id") id:String
    ) :Rockets

     @GET("launches/upcoming")
     suspend fun getUpcomingLaunches(): List<UpcomingLaunches>

     @GET("launches/{id}")
     suspend fun getLaunchDetail(
         @Path("id") id :String):UpcomingLaunches
}