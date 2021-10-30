package com.sharefinancialledger.global.exception.advice

import com.sharefinancialledger.global.dto.ErrorResponse
import com.sharefinancialledger.global.exception.CustomException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(CustomException::class)
    fun handle(e: CustomException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(e.message ?: ""), e.status)
    }
}