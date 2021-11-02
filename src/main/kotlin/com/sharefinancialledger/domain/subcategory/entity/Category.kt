package com.sharefinancialledger.domain.subcategory.entity

import com.sharefinancialledger.global.entity.type.TransactionType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(AuditingEntityListener::class)
@Entity
class SubCategory (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        val userId: Int,

        var title: String,

        val categoryId: Int

) {

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var modifiedAt: LocalDateTime
}