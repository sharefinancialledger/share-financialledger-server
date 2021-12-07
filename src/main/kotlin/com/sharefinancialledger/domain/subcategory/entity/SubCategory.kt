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

    var deletedAt: LocalDateTime? = null

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var modifiedAt: LocalDateTime

    @PrePersist
    fun prePersist() {
        if (title.length > 30) throw IllegalArgumentException("서브카테고리명은 최대 30글자까지 가능합니다.")
    }

    @Transient
    fun isOwn(userId: Int) = this.userId == userId

    fun raiseIfIsNotOwn(userId: Int) {
        if (!isOwn(userId)) throw AuthorizationException("접근할 수 없는 서브카테고리입니다.")
    }

    @Transient
    fun delete() {
        deletedAt = LocalDateTime.now()
    }
}