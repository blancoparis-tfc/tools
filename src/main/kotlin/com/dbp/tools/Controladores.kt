package com.dbp.tools


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController



@RestController
@RequestMapping("/core")
class CoreController{
    @GetMapping("/version")
    fun getVersion()="1.0.0"

    @GetMapping("/user")
    fun user()    =  "user mock"//principal.getAttribute<Object>("name")

}