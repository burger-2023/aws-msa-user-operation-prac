package com.prac.msa.awsmsauserservice.awsmsauserservice

import com.prac.msa.awsmsauserservice.awsmsauserservice.entity.User
import com.prac.msa.awsmsauserservice.awsmsauserservice.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val userRepository: UserRepository
) : CommandLineRunner {

    override fun run(vararg args: String) {
        val count = userRepository.count()
        if (count == 0L) {
            val user1 = User(
                email = "burger@burger.com",
                name = "burger",
                password = "burgerpwd"
            )
            val user2 = User(
                email = "burger2@burger.com",
                name = "burger2",
                password = "burger2pwd"
            )
            userRepository.save(user1)
            userRepository.save(user2)
        }
    }
}