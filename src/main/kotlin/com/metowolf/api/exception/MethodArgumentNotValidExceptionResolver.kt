package com.metowolf.api.exception

import com.metowolf.api.entity.Response
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

/**
 * 参数校验异常处理
 *
 * Created by metoxie on 2019-12-12
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class MethodArgumentNotValidExceptionResolver {
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handle(request: HttpServletRequest?, e: MethodArgumentNotValidException): Response {
        val message = e.bindingResult.fieldError?.defaultMessage ?: "传递参数不正确"
        return Response(406, message, null)
    }
}