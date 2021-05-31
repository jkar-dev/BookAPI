package com.study.auth.controller

import com.study.auth.jwt.JwtTokenUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping()
class AuthController @Autowired constructor(
    private val jwtTokenUtil: JwtTokenUtil
) {
    @PostMapping
    fun provideToken(
        @RequestBody
        serviceName: String?
    ): ResponseEntity<Any> {
        if (serviceName == null) return ResponseEntity.badRequest().build()
        val token = jwtTokenUtil.generateToken(serviceName)
        return ResponseEntity.ok(token)
    }

    @PostMapping("/validate")
    fun validateToken(
        @RequestHeader("Authorization")
        token: String?,
        @RequestBody
        serviceName: String?
    ): ResponseEntity<Any> {
        if (token == null || serviceName == null) return ResponseEntity.badRequest().build()
        if (jwtTokenUtil.isValid(token, serviceName)) return ResponseEntity.status(HttpStatus.OK).build()
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }
}