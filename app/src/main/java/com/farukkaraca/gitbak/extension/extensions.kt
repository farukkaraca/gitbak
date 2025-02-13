package com.farukkaraca.gitbak.extension

import com.farukkaraca.gitbak.data.model.ApiResponse
import retrofit2.Response

fun <T> Response<T>.toResult(): ApiResponse<T> {
    return if (this.isSuccessful) {
        this.body()?.let {
            ApiResponse.Success(it)
        } ?: ApiResponse.Error(Exception("Response body is null"))
    } else {
        ApiResponse.Error(Exception("API call failed with code: ${this.code()}"))
    }
}
