package com.sharefinancialledger.global.exception.advice

import com.sharefinancialledger.global.dto.ErrorResponse
import com.sharefinancialledger.global.exception.CustomException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    private val logger = mu.KotlinLogging.logger {}

    @ExceptionHandler(CustomException::class)
    fun handle(e: CustomException): ResponseEntity<ErrorResponse> {
        logger.warn { e }
        return ResponseEntity(ErrorResponse(e.message ?: ""), e.status)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.warn { e }
        return ResponseEntity(ErrorResponse(e.bindingResult.allErrors.first().defaultMessage
                ?: "잘못된 요청입니다."), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handle(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        logger.warn { e }
        return ResponseEntity(ErrorResponse("잘못된 요청입니다."), HttpStatus.BAD_REQUEST)
    }


}