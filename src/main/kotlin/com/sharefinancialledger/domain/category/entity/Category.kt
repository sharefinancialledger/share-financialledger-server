package com.sharefinancialledger.domain.category.entity

import com.sharefinancialledger.global.entity.type.TransactionType
import com.sharefinancialledger.global.exception.AuthorizationException
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Transient

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
        val transactionType: TransactionType,

        @Column(columnDefinition = "tinyint(1) not null default 0", nullable = false)
        var isDelete: Boolean = false
) {

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var modifiedAt: LocalDateTime


    @PrePersist
    fun prePersist() {
        if (title.length > 30) throw IllegalArgumentException("카테고리명은 최대 30글자까지 가능합니다.")
    }

    @Transient
    fun isOwn(userId: Int) = this.userId == userId

    @Transient
    fun raiseIfIsNotOwn(userId: Int) {
        if (!isOwn(userId)) throw AuthorizationException("접근할 수 없는 카테고리입니다.")
    }

    @Transient
    fun delete() {
        isDelete = true
    }

}