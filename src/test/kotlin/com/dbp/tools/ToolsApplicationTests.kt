package com.dbp.tools

import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class ToolsApplicationTests {

	@Autowired lateinit  var coreController:CoreController;

	@Test
	fun contextLoads() {
			AssertionsForClassTypes.assertThat(coreController).isNotNull;
	}

}
