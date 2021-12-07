package com.sharefinancialledger.domain.subcategory.controller

import com.sharefinancialledger.domain.subcategory.controller.dto.CreateSubCategoryRequest
import com.sharefinancialledger.domain.subcategory.controller.dto.UpdateInfoRequest
import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import com.sharefinancialledger.domain.subcategory.service.SubCategoryService
import com.sharefinancialledger.domain.user.entity.User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api")
class SubCategoryController(
        private val service: SubCategoryService
) {

    @PostMapping("/v1/subcategories")
    fun create(@AuthenticationPrincipal user: User, @RequestBody request: CreateSubCategoryRequest): ResponseEntity<Unit> {
        service.create(user.id!!, request)
        return ResponseEntity.created(URI("")).build() // TODO 서브카테고리 정보 조회 API 개발 시 URI 넣을 예정
    }

    @GetMapping("/v1/subcategories")
    fun getAll(@AuthenticationPrincipal user: User): List<SubCategory> {
        return service.get(user.id!!)
    }

    @PatchMapping("/v1/subcategories/{subCategoryId}")
    fun updateInfo(@AuthenticationPrincipal user: User, @RequestBody request: UpdateInfoRequest, @PathVariable subCategoryId: Int): SubCategory {
        return service.update(subCategoryId, request)
    }

    @DeleteMapping("/v1/subcategories/{subCategoryId}")
    fun delete(@AuthenticationPrincipal user:User, @PathVariable subCategoryId: Int): Unit {
        service.delete(subCategoryId)
    }
}