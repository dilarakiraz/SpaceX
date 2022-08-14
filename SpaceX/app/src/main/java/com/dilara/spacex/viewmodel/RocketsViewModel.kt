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
class RocketsViewModel @Inject constructor(
    private val repository: RocketsRepository,
    private val roomRepository :  RoomRepository
) : ViewModel() {

    init {
        getRockets()
    }


    fun getRockets()= liveData(Dispatchers.IO){
        emit(com.dilara.spacex.util.Resource.load(data = null))
        try {
            emit(com.dilara.spacex.util.Resource.success(data=repository.getAllRockets()))

        }catch (e:Exception){
            emit(com.dilara.spacex.util.Resource.error(data = null,message = e.message?: "Error"))
        }
    }

    fun saveRockets()=roomRepository.getSavedRockets()


    fun deleteId(id:String,rockets:Rockets)=viewModelScope.launch {
        rockets.isLike=false
        rockets.id?.let { roomRepository.deleteId(it) }

    }
    fun upsert(rockets: Rockets)=viewModelScope.launch {
        rockets.isLike=true
        roomRepository.upsertRockets(rockets)

    }

    fun controlFav(id:String)= liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data=roomRepository.controlFav(id)))
        }catch (e:Exception){
            emit(Resource.error(data = null, message = e.message.toString()))
        }
    }
}