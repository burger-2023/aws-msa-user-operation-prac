package com.prac.msa.awsmsauserservice.awsmsauserservice.service

import com.prac.msa.awsmsauserservice.awsmsauserservice.dto.Credentials
import com.prac.msa.awsmsauserservice.awsmsauserservice.entity.User
import com.prac.msa.awsmsauserservice.awsmsauserservice.error.InvalidCredentialsException
import com.prac.msa.awsmsauserservice.awsmsauserservice.error.UserAlreadyExistsException
import com.prac.msa.awsmsauserservice.awsmsauserservice.error.UserNotFoundException
import com.prac.msa.awsmsauserservice.awsmsauserservice.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile


@Service
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun registerUser(user: User): User {
        val isExist = userRepository.findByEmail(user.email)
        if (isExist != null) throw UserAlreadyExistsException("User already exist with email: ${user.email}")

        return userRepository.save(user)
    }

    @Transactional(readOnly = true)
    fun findByEmail(credentials: Credentials): User {
        val user = userRepository.findByEmail(credentials.email) ?: throw UserNotFoundException("User not found with email: ${credentials.email}")
        if (user.password != credentials.password) throw InvalidCredentialsException("Invalid credentials")

        return user
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException("User not found with id: $id") }
    }
}