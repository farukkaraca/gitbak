package com.farukkaraca.gitbak.data.model

data class AccessToken(
    val access_token: String,
    val scope: String,
    val token_type: String,
)