package com.dbp.tools

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration



@Configuration // Para cargarlo en el contesto
@ConfigurationProperties() // Vincula las configuraciones
data class Config(var version:String = "")




