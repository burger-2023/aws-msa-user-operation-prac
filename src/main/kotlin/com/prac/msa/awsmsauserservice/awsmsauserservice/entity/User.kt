package com.prac.msa.awsmsauserservice.awsmsauserservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = true)
    var imageUrl: String? = null,
) {
}