package com.farukkaraca.gitbak.data.model

data class RepoDetail(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: User,
    val description: String?,
    val stargazers_count: Int,
    val watchers_count: Int,
    val forks_count: Int,
    val open_issues_count: Int,
    val language: String?,
    val topics: List<String>,
    val visibility: String,
    val default_branch: String,
    val created_at: String,
    val updated_at: String,
    val homepage: String?,
    val license: License?,
    val subscribers_count: Int,
)

data class License(
    val key: String,
    val name: String,
    val url: String?
)