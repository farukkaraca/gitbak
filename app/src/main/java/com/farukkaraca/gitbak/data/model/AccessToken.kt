package com.farukkaraca.gitbak.data.model

data class AccessToken(
    val access_token: String,
    val scope: String? = null,
    val token_type: String? = null,
)