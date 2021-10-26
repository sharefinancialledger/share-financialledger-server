package com.sharefinancialledger.exception

import org.springframework.http.HttpStatus

open class CustomException(override val message: String?, val status: HttpStatus) : RuntimeException(message)

class BadRequestException(override val message: String? = "잘못된 요청입니다.") : CustomException(message, HttpStatus.BAD_REQUEST)

