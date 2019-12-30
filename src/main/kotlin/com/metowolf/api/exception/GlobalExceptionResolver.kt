package com.metowolf.api.exception

import com.metowolf.api.entity.Response
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionResolver {

    @ExceptionHandler(Exception::class)
    fun handleException(request: HttpServletRequest, e: Exception): Response {
        return Response(500, e.localizedMessage, null)
    }

}