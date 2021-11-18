package com.dbp.tools

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/core")
class CoreController{
    @GetMapping("/version")
    fun getVersion()="1.0.0"

    @GetMapping("/user")
    fun user(@AuthenticationPrincipal principal: OAuth2User)    =  principal.getAttribute<Object>("name")

}