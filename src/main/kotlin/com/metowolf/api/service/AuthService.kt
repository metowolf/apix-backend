package com.metowolf.api.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService {

    @Value("\${jwt.secret}")
    lateinit var jwtSecret: String

    @Value("\${jwt.issuer}")
    lateinit var jwtIssuer: String

    fun sign(subject: String): String {
        val id = UUID.randomUUID().toString()
        return sign(subject, id)
    }

    fun sign(subject: String, id: String): String {
        val algorithm = Algorithm.HMAC256(jwtSecret)
        val current = Calendar.getInstance()
        val expired = Calendar.getInstance()
        expired.add(Calendar.DAY_OF_YEAR, 7)
        val token = JWT.create()
                .withJWTId(id)
                .withIssuer(jwtIssuer)
                .withSubject(subject)
                .withIssuedAt(current.time)
                .withExpiresAt(expired.time)
                .sign(algorithm)
        return token
    }

    fun refresh(jwt: DecodedJWT): String {
        return sign(jwt.subject, jwt.id)
    }

    fun autoRequire(token: String): String {
        val jwt = verify(token) ?: throw Exception("令牌校验失败")
        val end: Long = jwt.expiresAt.time
        val start: Long = jwt.issuedAt.time
        val current: Long = Date().time
        if ((current - start) * 1.0 / (end - start) > 0.8) {
            return refresh(jwt)
        }
        return token
    }

    fun verify(token: String): DecodedJWT? {
        try {
            val algorithm = Algorithm.HMAC256(jwtSecret)
            val verifier = JWT.require(algorithm)
                    .withIssuer(jwtIssuer)
                    .acceptLeeway(1)
                    .build()
            val jwt = verifier.verify(token)
            return jwt
        } catch (exception: JWTVerificationException) {
            return null
        }
    }
}