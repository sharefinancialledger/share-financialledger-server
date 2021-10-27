package com.sharefinancialledger.exception

import org.springframework.http.HttpStatus

open class CustomException(override val message: String?, val status: HttpStatus) : RuntimeException(message)

class BadRequestException(override val message: String? = "잘못된 요청입니다.") : CustomException(message, HttpStatus.BAD_REQUEST)

class AuthenticationException(override val message: String? = "인증에 실패했습니다.") : CustomException(message, HttpStatus.UNAUTHORIZED)

class AuthorizationException(override val message: String? = "권한이 없습니다.") : CustomException(message, HttpStatus.FORBIDDEN)