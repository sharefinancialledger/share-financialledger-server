package com.sharefinancialledger.base

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import javax.persistence.EntityManager

@EnableJpaAuditing
@DataJpaTest
abstract class RepositoryTest {

    @Autowired
    lateinit var em: EntityManager
}