package com.metowolf.api.entity

data class Response (
        val code: Int,
        val message: String,
        val data: Any?
)