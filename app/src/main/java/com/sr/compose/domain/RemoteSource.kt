package com.sr.compose.domain

import com.sr.compose.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import timber.log.Timber
import java.net.HttpURLConnection

const val COMPOSE_TAG = "COMPOSE_LOGGER"

object RemoteSource {
    fun <T> launchResultFlow(apiResponse: suspend () -> Response<T>): Flow<Resource<T>> =
        flow {
            emit(Resource.Loading)
            val response = apiResponse.invoke()
            response.let {
                if (response.isSuccessful) {
                    emit(Resource.Success(it.body()!!))
                } else {
                    emit(Resource.Error(response.code()))
                    Timber.d("$COMPOSE_TAG Http request failed errorBody -> ${response.errorBody()}")
                }
            }
        }.catch { httpErr ->
            emit(Resource.Error(R.string.general_api_error))
            Timber.d("$COMPOSE_TAG Http request failed error -> $httpErr")
        }.flowOn(Dispatchers.IO)

    suspend fun <T> launchResult(apiResponse: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = apiResponse.invoke()
            return if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Timber.d("$COMPOSE_TAG Http request failed error -> ${response.code()}")
                Resource.Error(if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) response.code() else R.string.general_api_error)
            }
        } catch (e: Exception) {
            Timber.d("$COMPOSE_TAG Http request error: ${e.printStackTrace()}.}")
            Resource.Error(R.string.general_api_error)
        }
    }
}