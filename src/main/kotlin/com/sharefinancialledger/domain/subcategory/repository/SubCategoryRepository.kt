package com.sharefinancialledger.domain.subcategory.repository

import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import org.springframework.data.jpa.repository.JpaRepository

interface SubCategoryRepository : JpaRepository<SubCategory, Int>
