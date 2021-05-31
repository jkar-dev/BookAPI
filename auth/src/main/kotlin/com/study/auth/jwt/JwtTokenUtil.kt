package com.study.auth.jwt

import com.study.auth.service.AuthService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class JwtTokenUtil(private val authService: AuthService) : Serializable {
    private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(serviceName: String) : String {
        return Jwts.builder()
            .setSubject(serviceName)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .signWith(key)
            .compact()
    }

    fun isValid(token : String, nameTo : String) : Boolean {
        val nameFrom = getServiceNameFromToken(token)
        val allowedServices = authService.getAllowedServices(nameTo)
        return allowedServices.contains(nameFrom)
    }

    private fun getServiceNameFromToken(token : String) : String {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
    }
}