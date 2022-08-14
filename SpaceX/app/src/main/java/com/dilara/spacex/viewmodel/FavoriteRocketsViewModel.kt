package com.dilara.spacex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dilara.spacex.model.Rockets
import com.dilara.spacex.repository.RoomRepository
import com.dilara.spacex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRocketsViewModel @Inject constructor(
    private val repository: RoomRepository,
): ViewModel() {

    fun getSavedRockets()=repository.getSavedRockets()
    fun deleteRockets(rockets:Rockets)=viewModelScope.launch {
        rockets.isLike=false
        repository.deleteRocket(rockets)
    }

    fun upsert(rockets: Rockets)=viewModelScope.launch {
        rockets.isLike=true
        repository.upsertRockets(rockets)
    }

    fun controlFav(id:String)= liveData(Dispatchers.IO){
        try{
            emit(Resource.success(data=repository.controlFav(id)))
        }catch (e:Exception){
            emit(Resource.error(data = null, message = e.message.toString()))
        }
    }

}