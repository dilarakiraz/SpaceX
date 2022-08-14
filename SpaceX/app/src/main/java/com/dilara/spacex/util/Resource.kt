package com.dilara.spacex.util

data class Resource<out T >(val status:Status,val data:T?,val message:String?){
    companion object{
        fun<T> success(data:T):Resource<T> = Resource(status=Status.SUCCESS ,data=data, message = null)

        fun<T> error(data:T?,message:String) : Resource<T> = Resource(status=Status.ERROR ,data=data,message=message)

        fun<T>  load(data:T?): Resource<T> =  Resource(status =Status.LOADİNG,data=data,message = null)
    }
}
