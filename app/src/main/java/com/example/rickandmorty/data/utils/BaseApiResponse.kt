package com.example.rickandmorty.data.utils

import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiColl (apiColl: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiColl()
            if (response.isSuccessful){
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        }catch (e: Exception){
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")
}