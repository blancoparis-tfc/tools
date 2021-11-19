package com.dbp.tools

import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ToolsApplicationTests {

	@Autowired lateinit  var coreController:CoreController;

	@Test
	fun contextLoads() {
			AssertionsForClassTypes.assertThat(coreController).isNotNull;
	}

}
