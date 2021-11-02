package com.sharefinancialledger.domain.category.entity

import com.sharefinancialledger.global.entity.type.TransactionType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(AuditingEntityListener::class)
@Entity
class Category (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column(nullable = false)
        val userId: Int,

        @Column(nullable = false)
        var title: String,

        @Column(nullable = false)
        val transactionType: TransactionType

) {

    @PrePersist
    fun prePersist() {
        if (title.length > 30) throw IllegalArgumentException("카테고리명은 최대 30글자까지 가능합니다.")
    }

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var modifiedAt: LocalDateTime
}