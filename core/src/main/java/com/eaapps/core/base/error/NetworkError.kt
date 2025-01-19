package com.eaapps.core.base.error

import org.json.JSONException
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import kotlin.coroutines.cancellation.CancellationException

sealed class NetworkError : Throwable(), NetworkErrorCategory {
    override val category: ErrorCategory = ErrorCategory.NETWORK_ERROR

    data class RemoteException(val msg: String? = "") : NetworkError()

    data object Unauthorized401 : NetworkError() {
        private fun readResolve(): Any = Unauthorized401
    }

    data object NotFound404 : NetworkError() {
        private fun readResolve(): Any = NotFound404
    }

    data object ServerError500 : NetworkError() {
        private fun readResolve(): Any = ServerError500
    }

    data object HostUnreachable : NetworkError() {
        private fun readResolve(): Any = HostUnreachable
    }

    data object SslError : NetworkError() {
        private fun readResolve(): Any = SslError
    }

    data object IllegalArgument : NetworkError() {
        private fun readResolve(): Any = IllegalArgument
    }

    data object JsonParseError : NetworkError() {
        private fun readResolve(): Any = JsonParseError
    }

    data object OperationTimeout : NetworkError() {
        private fun readResolve(): Any = OperationTimeout
    }

    data object SocketException : NetworkError() {
        private fun readResolve(): Any = SocketException
    }

    data object CancellationException : NetworkError() {
        private fun readResolve(): Any = CancellationException
    }

    data object ConnectionSpike : NetworkError() {
        private fun readResolve(): Any = ConnectionSpike
    }

    data object UnknownNetworkingError : NetworkError() {
        private fun readResolve(): Any = UnknownNetworkingError
    }
}

object NetworkErrorTransformer {
    fun transform(incoming: Throwable) = when (incoming) {
        is IllegalArgumentException -> NetworkError.IllegalArgument
        is SocketTimeoutException -> NetworkError.OperationTimeout
        is SSLException -> NetworkError.SslError
        is JSONException -> NetworkError.JsonParseError
        is SocketException -> NetworkError.SocketException
        is CancellationException -> NetworkError.CancellationException

        is UnknownHostException, is ConnectException, is NoRouteToHostException -> NetworkError.HostUnreachable
        else -> resolveOtherException(incoming)
    }

    private fun isRequestCanceled(throwable: Throwable) = throwable is IOException && throwable.message?.contentEquals("Canceled") ?: false

    private fun resolveOtherException(incoming: Throwable) = if (isRequestCanceled(incoming)) NetworkError.ConnectionSpike
    else {
        NetworkError.UnknownNetworkingError
    }


}

fun NetworkError.toValidError(): NetworkError? {
    return if (this is NetworkError.CancellationException) null
    else this
}