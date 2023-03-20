package com.svdroid.avivgrouptest.data.utils

sealed class DataState<M> {
    class Loading<M>: DataState<M>()
    data class Data<M>(val data: M?): DataState<M>()
    data class Error<M>(val message: String): DataState<M>()
}