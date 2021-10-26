//package com.sharefinancialledger.auth.controller
//
//import com.sharefinancialledger.auth.service.UserService
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.http.MediaType
//import org.springframework.test.web.client.RequestMatcher
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.RequestBuilder
//import org.springframework.test.web.servlet.ResultMatcher
//import org.springframework.test.web.servlet.get
//import org.springframework.test.web.servlet.post
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//
//@WebMvcTest(UserController::class)
//
//class UserControllerTest {
//
//    @Autowired
//    lateinit var mvc: MockMvc
//
//    @MockBean
//    lateinit var service: UserService
//
//    @Test
//    fun test() {
//        val request= """
//            {
//            "email": "",
//            "password": ""
//            }
//        """.trimIndent()
//
//        mvc.perform {
//            MockMvcRequestBuilders.post("/api/v1/users")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(request)
//                    .buildRequest(it)
//        }.andExpect { status().is4xxClientError }
//    }
//}