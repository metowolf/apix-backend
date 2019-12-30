package com.metowolf.api.exception

import com.metowolf.api.entity.Response
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class IllegalArgumentExceptionResolver {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleException(request: HttpServletRequest, e: Exception): Response {
        return Response(406, "参数校验失败，" + e.localizedMessage, null)
    }
}