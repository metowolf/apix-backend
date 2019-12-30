package com.metowolf.api.entity

import java.util.*

abstract class BaseEntity<T> {
    val id: Int? = null
    val createTime: Date? = null
    val createUser: String? = null
    val updateTime: Date? = null
    val updateUser: String? = null
}