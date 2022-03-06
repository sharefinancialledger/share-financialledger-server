package com.sharefinancialledger.domain.user.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "user")
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        val email: String,

        var password: String, // TODO hashing

        var name: String,

        @ManyToMany(cascade=[CascadeType.ALL])
        @JoinColumn(name="USER_ID")
        val friends:Set<User> = mutableSetOf<User>(),

) {

    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    lateinit var modifiedAt: LocalDateTime
}