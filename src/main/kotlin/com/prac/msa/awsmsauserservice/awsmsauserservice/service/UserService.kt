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
    private val userRepository: UserRepository,
    private val s3Service: S3Service
) {

    @Transactional
    fun registerUser(user: User): User {
        val isExist = userRepository.findByEmail(user.email)
        if (isExist != null) throw UserAlreadyExistsException("User already exist with email: ${user.email}")

        return userRepository.save(user).apply {
            imageUrl = s3Service.convertToCloudFrontUrl(imageUrl)
        }
    }

    @Transactional
    fun registerUserImage(image: MultipartFile, userId: Long): String? {
        // 이미지 키값 생성
        val key = "user/$userId/${image.originalFilename?.replace(" ", "_")}"

        // S3에 이미지 업로드
        s3Service.uploadFile(key, image.bytes)

        // imageUrl 저장
        userRepository.updateUserImageUrl(userId, key)

        return s3Service.convertToCloudFrontUrl(key)
    }

    @Transactional(readOnly = true)
    fun findByEmail(credentials: Credentials): User {
        val user = userRepository.findByEmail(credentials.email)?.apply {
            imageUrl = s3Service.convertToCloudFrontUrl(imageUrl)
        } ?: throw UserNotFoundException("User not found with email: ${credentials.email}")
        if (user.password != credentials.password) throw InvalidCredentialsException("Invalid credentials")

        return user
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException("User not found with id: $id") }.apply {
            imageUrl = s3Service.convertToCloudFrontUrl(imageUrl)
        }
    }

    @Transactional
    fun deleteUserImage(userId: Long) {
        val user = findById(userId)
        if (user.imageUrl == null) return

        s3Service.deleteFile(user.imageUrl!!)

        // imageUrl to null
        userRepository.updateUserImageUrlToNull(userId)
    }
}