package com.prac.msa.awsmsauserservice.awsmsauserservice.client

import org.apache.catalina.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<User, Long>