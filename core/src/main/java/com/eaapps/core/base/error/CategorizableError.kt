package com.eaapps.core.base.error

interface CategorizableError {
    val category: ErrorCategory
}

enum class ErrorCategory {
    NETWORK_ERROR,
    VALIDATE_ERROR
}

interface NetworkErrorCategory : CategorizableError

interface ValidateErrorCategory : CategorizableError

fun CategorizableError.getErrorMessage(): String {
    return when (this) {
        is NetworkError -> when (this) {
            is NetworkError.RemoteException -> "Remote error: ${msg.orEmpty()}"
            NetworkError.Unauthorized401 -> "Authentication failed."
            NetworkError.NotFound404 -> "Resource not found."
            NetworkError.ServerError500 -> "Server error occurred."
            NetworkError.HostUnreachable -> "Host is unreachable."
            NetworkError.SslError -> "SSL error detected."
            NetworkError.IllegalArgument -> "Illegal argument provided."
            NetworkError.JsonParseError -> "JSON parsing error."
            NetworkError.OperationTimeout -> "Operation timed out."
            NetworkError.SocketException -> "Socket error occurred."
            NetworkError.CancellationException -> "Operation cancelled."
            NetworkError.ConnectionSpike -> "Network instability detected."
            NetworkError.UnknownNetworkingError -> "Unknown network error occurred."
        }

        is ValidateError -> when (this) {
            ValidateError.MissingField -> "A required field is missing."
            ValidateError.CountryMissing -> "Country is required. Please select your country."
        }

        else -> "Unknown error."
    }
}