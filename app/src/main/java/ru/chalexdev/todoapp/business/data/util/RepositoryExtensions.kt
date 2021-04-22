package ru.chalexdev.todoapp.business.data.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import ru.chalexdev.todoapp.business.data.cache.CacheConstant.CACHE_TIMEOUT
import ru.chalexdev.todoapp.business.data.cache.CacheErrors.CACHE_ERROR_TIMEOUT
import ru.chalexdev.todoapp.business.data.cache.CacheErrors.CACHE_ERROR_UNKNOWN
import ru.chalexdev.todoapp.business.data.cache.CacheResult
import ru.chalexdev.todoapp.business.data.network.ApiResult
import ru.chalexdev.todoapp.business.data.network.NetworkConstant.NETWORK_TIMEOUT
import ru.chalexdev.todoapp.business.data.network.NetworkErrors.NETWORK_ERROR
import ru.chalexdev.todoapp.business.data.network.NetworkErrors.NETWORK_ERROR_TIMEOUT
import java.io.IOException


/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */

suspend fun <T> safeCacheCall(
    dispatcher: CoroutineDispatcher,
    cacheCall: suspend () -> T?
): CacheResult<T?> {
    return withContext(dispatcher) {
        try {
            withTimeout(CACHE_TIMEOUT) {
                CacheResult.Success(cacheCall.invoke())
            }
        } catch (throwable: Throwable) {
            //TODO Add logging
            when (throwable) {
                is TimeoutCancellationException -> CacheResult.Error(CACHE_ERROR_TIMEOUT)
                else -> CacheResult.Error(CACHE_ERROR_UNKNOWN)
            }
        }
    }
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ApiResult<T?> {
    return withContext(dispatcher) {
        try {
            withTimeout(NETWORK_TIMEOUT) {
                ApiResult.Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            //TODO Add logging
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408
                    ApiResult.Error(code, NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    ApiResult.NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ApiResult.Error(code, errorResponse)
                }
                else -> {
                    ApiResult.Error(null, NETWORK_ERROR)
                }
            }
        }
    }
}

private fun convertErrorBody(httpException: HttpException): String? {
    return try {
        httpException.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        "Unknown error"
    }
}