package com.dbp.tools


import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.web.server.LocalServerPort
import java.net.URI

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT  )
class HttpRequestTest {

    @LocalServerPort  lateinit var port:Integer;

    @Autowired lateinit var restTemplate: TestRestTemplate;

    @Test
    fun version(){
        assertThat(
            restTemplate.getForObject<String>(
                url = URI.create("http://localhost:${port}/core/version")
                )
        ).contains("1.0.0");
    }
}