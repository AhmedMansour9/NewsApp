package com.eaapps.core.base.data.network

import com.eaapps.core.base.domain.FlowResultSource
import com.eaapps.core.base.error.NetworkError
import com.eaapps.core.base.error.NetworkErrorTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> safeCall(onSuccess: suspend () -> T, onError: suspend (NetworkError) -> T): T = withContext(Dispatchers.IO) {
    try {
        onSuccess.invoke()
    } catch (e: Exception) {
        e.printStackTrace()
        if (e is CancellationException) {
            onError(NetworkErrorTransformer.transform(e))
        } else onError(NetworkErrorTransformer.transform(e))

    }
}


inline fun <Result, Request> networkBoundResource(
    crossinline query: () -> Flow<Result>,
    crossinline fetch: suspend () -> Request,
    crossinline saveFetchResult: suspend (Request) -> Unit,
    crossinline shouldFetch: (Result) -> Boolean = { true },
) = flow {
    val queryFlow = query()
    val data = queryFlow.first()
     if (shouldFetch(data)) {
        emit(queryFlow.map { FlowResultSource.Loading(it) }.first())
        try {
            saveFetchResult(fetch())
            emit(queryFlow.map { FlowResultSource.Success(it) }.first())
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            println("er-->${throwable.message}")
            emit(queryFlow.map { FlowResultSource.Failure(NetworkErrorTransformer.transform(throwable), it) }.first())
        }
    } else {
        emit(queryFlow.map { FlowResultSource.Success(it) }.first())
    }
}