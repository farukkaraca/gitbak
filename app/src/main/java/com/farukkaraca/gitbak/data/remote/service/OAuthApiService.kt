package com.farukkaraca.gitbak.data.remote.service

import com.farukkaraca.gitbak.data.model.AccessToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface OAuthApiService {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login/oauth/access_token/")
    suspend fun exchangeCodeForToken(
        @Field("client_id") clientId:String,
        @Field("client_secret") clientSecret:String,
        @Field("code") code:String,
    ):Response<AccessToken>
}