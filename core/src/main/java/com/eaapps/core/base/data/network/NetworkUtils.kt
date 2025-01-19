package com.eaapps.core.base.data.network

import com.eaapps.core.base.domain.FlowResultSource
import com.eaapps.core.base.error.NetworkError
import com.eaapps.core.base.error.NetworkErrorTransformer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> safeCall(onSuccess: suspend () -> Response<T>, onError: suspend (NetworkError) -> T): T = withContext(Dispatchers.IO) {
    try {
        val response = onSuccess()
        if (response.isSuccessful) {
            response.body() ?: onError(NetworkError.UnknownNetworkingError)
        } else {
            val errorBody = response.errorBody()?.string()
            val errorMessage = errorBody?.let { parseErrorMessage(it) } ?: "Unknown error"
            onError(NetworkError.RemoteException(errorMessage))
        }
    } catch (e: Exception) {
        if (e is CancellationException) {
            onError(NetworkErrorTransformer.transform(e))
        } else onError(NetworkErrorTransformer.transform(e))

    }
}

fun parseErrorMessage(errorBody: String): String {
    return try {
        val jsonObject = JSONObject(errorBody)
        jsonObject.getString("message")
    } catch (e: JSONException) {
        "Failed to parse error message"
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
            emit(queryFlow.map {
                FlowResultSource.Failure(
                    if (throwable is NetworkError) throwable else NetworkErrorTransformer.transform(throwable),
                    it
                )
            }.first())
        }
    } else {
        emit(queryFlow.map { FlowResultSource.Success(it) }.first())
    }
}