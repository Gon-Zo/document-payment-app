package com.example.document.infra.filter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExampleWebFilterTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    @Throws(Exception::class)
    fun web_filter_working_to_header_add() {
        //given
        //when
        val result = webTestClient.get()
            .uri("/hello")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(Map::class.java)
            .returnResult()

        //then
        val responseBody = result.responseBody as Map<*, *>
        assertEquals(responseBody["health"], "OK")
        assertEquals(result.responseHeaders.getFirst("web-filter"), "web-filter-test")
    }

    @Test
    @Throws(Exception::class)
    fun whenUserNameIsTester_thenWebFilterIsApplied() {

        val pathValue = "tester"

        //given
        //when
        val result = webTestClient.get()
            .uri("/hello/$pathValue")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(String::class.java)
            .returnResult()

        //then
        assertEquals(result.responseBody, pathValue)
        assertEquals(result.responseHeaders.getFirst("web-filter"), "web-filter-test")
    }
}
