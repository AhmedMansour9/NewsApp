package com.eaapps.core.base.domain

sealed class ResultSource<out T> {
    data class Success<T>(val result: T?, val message: String? = "") : ResultSource<T>()
    data class Failure(val throwable: Throwable) : ResultSource<Nothing>()

}

sealed class FlowResultSource<out T> {
    data class Success<T>(val result: T?, val message: String? = "") : FlowResultSource<T>()
    data class Failure<T>(val throwable: Throwable,val  data: T? = null) : FlowResultSource<T>()
    data class Loading<T>(val data: T? = null) : FlowResultSource<T>()
}