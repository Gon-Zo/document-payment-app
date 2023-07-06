package com.example.document.infra.handler

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExampleHandlerFilterFunctionTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    @Throws(Exception::class)
    fun whenUserNameIsTester_thenHandlerIsApplied() {

        val pathValue = "tester"

        //given
        //when
        val result = webTestClient.get()
            .uri("/player/$pathValue")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(String::class.java)
            .returnResult()

        //then
        assertEquals(result.responseBody, pathValue)
        assertEquals(result.responseHeaders.getFirst("web-filter"), "web-filter-test")
    }

    @Test
    @Throws(Exception::class)
    fun whenPlayerNameIsTest_thenHandlerFilterFunctionIsApplied() {

        //given

        //when
        val pathValue = "test"

        webTestClient.get().uri("/player/$pathValue")
            .exchange()
            .expectStatus()
            .isForbidden()

        //then
    }
}
