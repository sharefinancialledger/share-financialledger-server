package com.sharefinancialledger

import com.sharefinancialledger.global.config.Env
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class Application

fun main(args: Array<String>) {

	runApplication<Application>(*args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
		setDefaultProperties(hashMapOf("spring.profiles.default" to Env.SPRING_PROFILE_DEFAULT) as Map<String, *>)
	}
}
