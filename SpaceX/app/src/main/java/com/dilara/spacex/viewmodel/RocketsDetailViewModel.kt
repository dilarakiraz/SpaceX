package com.dilara.spacex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dilara.spacex.model.Rockets
import com.dilara.spacex.repository.RocketsRepository
import com.dilara.spacex.repository.RoomRepository
import com.dilara.spacex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RocketsDetailViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val repository: RocketsRepository
): ViewModel()

{

    fun getRocketDetail(id:String)= liveData(Dispatchers.IO){
        emit(Resource.load(data = null))
        try {
            emit(Resource.success(data=repository.getRocketDetail(id)))
        }catch (e:Exception){
            emit(Resource.error(data=null,message = e.message?:"ERROR"))
        }
    }

    fun upsert(rockets:Rockets)=viewModelScope.launch {
        rockets.isLike=true
        roomRepository.upsertRockets(rockets)
    }
    fun deleteId(rockets: Rockets)=viewModelScope.launch {
        rockets.isLike=false
        rockets.id?.let { roomRepository.deleteId(it) }
    }
    fun controlFav(id:String)= liveData(Dispatchers.IO) {
        try{
            emit(Resource.success(data = roomRepository.controlFav(id)))
        }catch (e:Exception){
            emit(Resource.error(data=null, message = e.message.toString()))
        }
    }
}