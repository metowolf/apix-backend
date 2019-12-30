package com.metowolf.api.entity

class User: BaseEntity<User>() {
    val username: String? = null
    val email: String? = null
    var password: String? = null
}