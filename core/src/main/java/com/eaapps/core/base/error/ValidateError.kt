package com.eaapps.core.base.error

sealed class ValidateError : Throwable(), ValidateErrorCategory {
    override val category: ErrorCategory = ErrorCategory.VALIDATE_ERROR

    data object MissingField : ValidateError() {
        private fun readResolve(): Any = MissingField
    }

    data object CountryMissing : ValidateError() {
        private fun readResolve(): Any = CountryMissing
    }

}