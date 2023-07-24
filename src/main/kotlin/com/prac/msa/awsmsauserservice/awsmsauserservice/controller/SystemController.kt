package com.prac.msa.awsmsauserservice.awsmsauserservice.controller

import com.sun.management.OperatingSystemMXBean
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.management.ManagementFactory
import java.net.InetAddress
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/users")
class SystemController {

    @GetMapping("/health_check")
    @Transactional(readOnly = true)
    fun healthCheck() = ResponseEntity.ok(
        mapOf(
            "status" to "OK",
            "message" to "User Service is healthy",
            "ipAddress" to InetAddress.getLocalHost().hostAddress,
            "timestamp" to LocalDateTime.now(),
            "cpuUsage" to getCurrentCpuUsage()
        )
    )


    fun getCurrentCpuUsage(): Double {
        val osBean = ManagementFactory.getOperatingSystemMXBean() as OperatingSystemMXBean
        return osBean.cpuLoad * 100
    }
}