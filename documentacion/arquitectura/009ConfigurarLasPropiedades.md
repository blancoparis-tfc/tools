# Propiedades

Vamos a ver como configurar las propiedades de nuestro sistema.

## Definir los ficheros  

Lo que vamos hacer es crear diferentes ficheros para las configuraciones

* **seguridad.yml**: Es la configuracion de seguridad:
* **jdbc**: Es la configuración de bases de datos.
* **tools**: Las propiedades de la aplicación.

```yaml
server:
  port: 8081
spring:
  config:
    import: jdbc.yml , tools.yml , seguridad.yml
```

## Como cargar las aplicaciones

En este caso utilizamos las anotaciones:
* **@Configuration** Para cargarlo en el contesto.
* **@ConfigurationProperties** Para indicar que cargue las propiedades

```kotlin

@Configuration // Para cargarlo en el contesto
@ConfigurationProperties() // Vincula las configuraciones
data class Config(var version:String = "")
@Configuration // Para cargarlo en el contesto
@ConfigurationProperties(prefix = "database") // Vincula las configuraciones
data class DatasourceConf(var url:String = "",var username:String = "",var password :String = "")

```

## Recursos

* **Documentacion**  https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html
* **guion** https://spring.io/blog/2020/08/14/config-file-processing-in-spring-boot-2-4