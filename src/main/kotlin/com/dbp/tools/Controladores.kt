package com.dbp.tools


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController



@RestController
@RequestMapping("/core")
class CoreController{

    @Autowired
    lateinit var config: Config;

    @GetMapping("/version")
    fun getVersion()=config.version

    @GetMapping("/user")
    fun user()    =  "user mock"//principal.getAttribute<Object>("name")

}