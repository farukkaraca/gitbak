package com.farukkaraca.gitbak.data.model

data class UserSearchResponse(
    val total_count: Int? = null,
    val incomplete_results: Boolean? = null,
    val items: List<User> = listOf()
)

data class User(
    val login: String ? = null,
    val id: Int? = null,
    val node_id: String? = null,
    val avatar_url: String? = null,
    val html_url: String? = null
)