package com.dbp.tools


import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import java.net.URI


@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles("test")
class HttpRequestTest {

    @LocalServerPort   var port: Int = 0

    @Autowired lateinit var restTemplate: TestRestTemplate

    @Test
    fun version(){
        assertThat(
            restTemplate.withBasicAuth("user","password").getForObject<String>(
                url = URI.create("http://localhost:${port}/core/version")
                )
        ).contains("1.0.0")
    }

    @Test
    fun user(){
        assertThat(
        restTemplate.withBasicAuth("user","password").getForObject<String>(
            url = URI.create("http://localhost:${port}/core/user")
        )
        ).contains("user mock")
    }
}