package com.metowolf.api.interceptor

import com.alibaba.fastjson.JSON
import com.metowolf.api.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.HandlerInterceptor
import javax.security.auth.message.AuthException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtInterceptor: HandlerInterceptor {

    @Autowired
    lateinit var authService: AuthService

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = request.getHeader("Authorization") ?: throw IllegalArgumentException("令牌为空")
        try {
            val newToken = authService.autoRequire(token)
            response.addHeader("Authorization", newToken)
            val jwt = authService.verify(newToken)
            val json = JSON.parseObject(jwt!!.subject)
            request.setAttribute("username", json.get("username"))
            request.setAttribute("email", json.get("email"))
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            throw AuthException()
        }
    }
}