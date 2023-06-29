package com.prac.msa.awsmsauserservice.awsmsauserservice.client

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ClientService(
    private val clientRepository: ClientRepository
) {

    fun isUserExists(productId: Long): Boolean {
        return clientRepository.existsById(productId)
    }
}