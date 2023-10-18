package com.prac.msa.awsmsauserservice.awsmsauserservice.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class ClientController(
    private val clientService: ClientService
) {

    @GetMapping("/{userId}/exists")
    fun isUserExists(
        @PathVariable userId: Long
    ): Boolean {
        return clientService.isUserExists(userId)
    }
}