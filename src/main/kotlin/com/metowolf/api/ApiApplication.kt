package com.metowolf.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiApplication

inline fun <reified T> loggerFor(): Logger = LoggerFactory.getLogger(T::class.java)

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}
