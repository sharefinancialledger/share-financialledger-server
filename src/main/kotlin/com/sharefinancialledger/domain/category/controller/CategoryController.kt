package com.sharefinancialledger.domain.category.controller

import com.sharefinancialledger.domain.category.controller.dto.CategoryResponse
import com.sharefinancialledger.domain.category.controller.dto.CreateCategoryRequest
import com.sharefinancialledger.domain.category.controller.dto.FindCategoriesResponse
import com.sharefinancialledger.domain.category.controller.dto.FindCategoryResponse
import com.sharefinancialledger.domain.category.controller.dto.UpdateCategoryRequest
import com.sharefinancialledger.domain.category.service.CategoryService
import com.sharefinancialledger.domain.user.entity.User
import com.sharefinancialledger.global.entity.type.TransactionType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
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
        val category = service.create(user.id!!, request)
        return ResponseEntity.created(URI("/api/v1/categories/${category.id}")).build()
    }
    @PatchMapping("/v1/categories/{categoryId}")
    fun update(
            @AuthenticationPrincipal user: User,
            @PathVariable categoryId: Int,
            @Valid @RequestBody request: UpdateCategoryRequest
    ) {
        service.update(categoryId, user.id!!, request)
    }

    @DeleteMapping("/v1/categories/{categoryId}")
    fun delete(@AuthenticationPrincipal user: User, @PathVariable categoryId: Int) {
        service.delete(categoryId, user.id!!)
    }

    @GetMapping("/v1/categories")
    fun find(@AuthenticationPrincipal user: User, transactionType: TransactionType): FindCategoriesResponse {
        return service.find(user.id!!, transactionType).let { FindCategoriesResponse.listOf(it) }
    }

    @GetMapping("/v1/categories/{categoryId}")
    fun find(@AuthenticationPrincipal user: User, @PathVariable categoryId: Int): FindCategoryResponse {
        return FindCategoryResponse(CategoryResponse.from(service.findOwn(categoryId, user.id!!)))
    }

}
