package com.sharefinancialledger.domain.category.repository

import com.sharefinancialledger.domain.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Int>
