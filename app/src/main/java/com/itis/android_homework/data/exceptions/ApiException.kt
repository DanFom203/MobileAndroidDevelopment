package com.itis.android_homework.data.exceptions

sealed class ApiException(message: String?) : Exception(message) {
    class BadRequestException(message: String) : Throwable(message = message)
    class ErrorNotFoundException(message: String) : Throwable(message = message)
    class InternalServerErrorException(message: String) : Throwable(message = message)
    class UserNotAuthorizedException(message: String) : Throwable(message = message)
    class TooManyRequestsException(message: String) : Throwable(message = message)
}