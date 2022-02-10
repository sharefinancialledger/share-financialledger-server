package com.sharefinancialledger.domain.subcategory.entity

import com.sharefinancialledger.global.entity.type.TransactionType
import com.sharefinancialledger.global.exception.AuthorizationException
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

        @Column(length = 30)
        var title: String,

        val categoryId: Int

) {

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var modifiedAt: LocalDateTime

    lateinit var deletedAt: LocalDateTime

    @Transient
    fun delete() {
        deletedAt = LocalDateTime.now()
    }
}