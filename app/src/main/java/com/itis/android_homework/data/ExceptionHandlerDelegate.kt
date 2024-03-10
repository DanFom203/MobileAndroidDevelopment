package com.itis.android_homework.data

import com.itis.android_homework.R
import com.itis.android_homework.data.exceptions.ApiException
import com.itis.android_homework.utils.ResManager
import retrofit2.HttpException

class ExceptionHandlerDelegate(
    private val resManager: ResManager,
) {

    fun handleException(ex: Throwable): Throwable {
        return when (ex) {
            is HttpException -> {
                when (ex.code()) {
                    400 -> {
                        ApiException.BadRequestException(message = resManager.getString(R.string.bad_request))
                    }
                    401 -> {
                        ApiException.UserNotAuthorizedException(message = resManager.getString(R.string.user_not_authorized))
                    }
                    404 -> {
                        ApiException.ErrorNotFoundException(message = resManager.getString(R.string.error_not_found))
                    }
                    429 -> {
                        ApiException.TooManyRequestsException(message = resManager.getString(R.string.too_many_requests))
                    }
                    500 -> {
                        ApiException.InternalServerErrorException(message = resManager.getString(R.string.internal_server_error))
                    }

                    else -> {
                        ex
                    }
                }
            }

            else -> {
                ex
            }
        }
    }
}