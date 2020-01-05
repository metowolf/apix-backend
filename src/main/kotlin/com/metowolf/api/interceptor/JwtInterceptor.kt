package com.metowolf.api.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
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
            val objectMapper = ObjectMapper()
            val nodeTree = objectMapper.readTree(jwt!!.subject)
            request.setAttribute("username", nodeTree.path("username").asText())
            request.setAttribute("email", nodeTree.path("email").asText())
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            throw AuthException()
        }
    }
}