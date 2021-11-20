package com.dbp.tools

import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
@WebMvcTest
class CapaWebTest {

    @Autowired
    lateinit var mockMvc: MockMvc;

    @Test
    fun retornoDefectoMensaje(){
        this.mockMvc
            .perform(get("/core/version"))
            .andDo(                         print())
            .andExpect(status().isOk)  // Miramo si es OK
            .andExpect(content().string(containsString("1.0.0"))) // Miramos si el resultado es correcto
    }
}