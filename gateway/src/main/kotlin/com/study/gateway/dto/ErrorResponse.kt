package com.study.gateway.dto

import org.springframework.http.HttpStatus

data class ErrorResponse(val statusCode: Int, val message: String) {
    constructor(status: HttpStatus) : this(status.value(), status.reasonPhrase)
}