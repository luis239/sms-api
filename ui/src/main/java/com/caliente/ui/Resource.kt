package com.caliente.ui

data class Resource<out T> constructor(val status: ResourceState,
                                       val data: T?,
                                       val message: String?) {

    companion object {

        fun <T> completed(): Resource<T> {
            return Resource(ResourceState.COMPLETED, null, null)
        }

        fun <T> next(data: T?): Resource<T> {
            return Resource(ResourceState.NEXT, data, null)
        }

        fun <T> error(message: String?): Resource<T> {
            return Resource(ResourceState.ERROR, null, message)
        }

        fun <T> loading(): Resource<T> {
            return Resource(ResourceState.LOADING, null, null)
        }
    }

    enum class ResourceState {
        LOADING, NEXT, ERROR, COMPLETED
    }

}