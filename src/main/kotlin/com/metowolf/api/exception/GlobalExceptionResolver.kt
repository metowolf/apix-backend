package com.metowolf.api.exception

import com.metowolf.api.entity.Response
import org.slf4j.LoggerFactory
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionResolver {

    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @ExceptionHandler(Exception::class)
    fun handleException(request: HttpServletRequest, e: Exception): Response {
        log.error("Global Exception: ", e)
        return Response(500, e.localizedMessage, null)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(request: HttpServletRequest, e: HttpRequestMethodNotSupportedException): Response {
        return Response(404, e.localizedMessage, null)
    }

}