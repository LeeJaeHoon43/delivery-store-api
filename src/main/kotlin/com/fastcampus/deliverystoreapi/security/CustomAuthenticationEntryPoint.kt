package com.fastcampus.deliverystoreapi.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

/**
 * 인증이 실패했을 때 처리하는 핸들러
 */
@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
): AuthenticationEntryPoint {

    companion object {
        private val logger = KotlinLogging.logger(this::class.java.name)
    }

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        logger.info { ">>> 인증이 실패하였습니다, ${request?.requestURI}, ${authException?.message}" }
        val responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authException?.message)
        response?.writer?.write(objectMapper.writeValueAsString(responseEntity))
    }
}