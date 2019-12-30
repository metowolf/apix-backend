package com.metowolf.api.exception

import com.metowolf.api.entity.Response
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.security.auth.message.AuthException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class AuthExceptionResolver {

    @ExceptionHandler(AuthException::class)
    fun handleException(request: HttpServletRequest, e: Exception): Response {
        return Response(401, "令牌不合法", null)
    }

}