package com.sharefinancialledger.domain.category.controller

import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.service.CategoryService
import com.sharefinancialledger.domain.user.entity.User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class CategoryController(
        private val service: CategoryService
) {

    @PostMapping("/v1/categories")
    fun create(@AuthenticationPrincipal user: User, @Valid @RequestBody request: CreateCategoryRequest): ResponseEntity<Unit> {
        service.create(user.id!!, request)
        return ResponseEntity.created(URI("")).build() // TODO 카테고리 정보 조회 API 개발 시 URI 넣을 예정
    }
}