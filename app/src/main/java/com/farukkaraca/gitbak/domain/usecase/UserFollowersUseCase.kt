package com.farukkaraca.gitbak.domain.usecase

import androidx.paging.PagingData
import com.farukkaraca.gitbak.data.model.User
import com.farukkaraca.gitbak.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserFollowersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun execute(username: String): Flow<PagingData<User>> {
        return userRepository.getUserFollowers(username)
    }
}