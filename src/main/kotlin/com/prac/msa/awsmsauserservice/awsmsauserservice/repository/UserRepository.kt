package com.prac.msa.awsmsauserservice.awsmsauserservice.repository

import com.prac.msa.awsmsauserservice.awsmsauserservice.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    @Modifying
    @Query("update User u set u.imageUrl = :imageUrl where u.id = :userId")
    fun updateUserImageUrl(userId: Long, imageUrl: String)

    @Modifying
    @Query("update User u set u.imageUrl = null where u.id = :userId")
    fun updateUserImageUrlToNull(userId: Long)
}