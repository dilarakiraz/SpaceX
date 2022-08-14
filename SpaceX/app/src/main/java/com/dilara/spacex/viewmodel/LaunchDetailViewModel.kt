package com.dilara.spacex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dilara.spacex.repository.RocketsRepository
import com.dilara.spacex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LaunchDetailViewModel @Inject constructor(
    private val repository: RocketsRepository) : ViewModel() {

    fun getLaunchDetail(id:String)= liveData(Dispatchers.IO){
        emit(Resource.load(data = null))
        try{
            emit(Resource.success(data = repository.getLaunchDetail(id)))

        }catch (e:Exception){
            emit(Resource.error(data = null, message = e.message ?:"ERROR!!"))
        }
    }
}