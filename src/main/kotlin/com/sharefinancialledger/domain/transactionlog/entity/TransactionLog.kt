package com.sharefinancialledger.domain.transactionlog.entity

import com.sharefinancialledger.domain.category.entity.Category
import com.sharefinancialledger.domain.subcategory.entity.SubCategory
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.ConstraintMode
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.ForeignKey
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table


@EntityListeners(AuditingEntityListener::class)
@Table(name = "transaction_log")
@Entity
class TransactionLog(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column(name = "user_id", nullable = false)
        val userId: Int,

        @Column(nullable = false)
        var date: LocalDate,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false)
        var amount: Int
) {

    @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = ForeignKey(ConstraintMode.CONSTRAINT))
    @ManyToOne(targetEntity = Category::class, fetch = FetchType.LAZY)
    lateinit var category: Category

    @JoinColumn(name = "sub_categody_id", referencedColumnName = "id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(targetEntity = SubCategory::class, fetch = FetchType.LAZY)
    var subcategory: SubCategory? = null

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var modifiedAt: LocalDateTime

}