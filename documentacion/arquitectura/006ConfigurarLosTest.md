# Configuración Test

Vamos a ver como se crear diferentes tipos de test

## Test para probar una peticion real a un controlador

La prueba lo que hace es recuperar la versión.

> Ojo esta url la tenemos que quitar de la securización.

1. Ponemos un puerto aleatorio (Para no chocar)

La siguiente constante es la que nos indica que el puerto es aleatorio **SpringBootTest.WebEnvironment.RANDOM_PORT**

```kotlin
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT  )
class HttpRequestTest {}
```
2. Recuperamos el puerto.

La siguiente anotación **@LocalServerPort** nos permite recuperar el puerto del servidor del test.
```kotlin
    @LocalServerPort  lateinit var port:Integer;
```

3. Invocar una url.

* Utilizamos el objeto **TestRestTemplate**, para poder invocar a las diferentes opciones

```kotlin
@Autowired lateinit var restTemplate: TestRestTemplate;
// Recupera la respuesta.
restTemplate.getForObject<String>(
    url = URI.create("http://localhost:${port}/core/version")
)
```

## Test mock de mvc **MockMvc**

Podemos hacer un test que llame a la pila sin arrancar un tomcat, usando el mock de mvc

>Levanta el contexto, pero no el servidor.

1. Configuramos el **MockMvc** usaremos la siguiente anotación (**@AutoConfigureMockMvc**)
```kotlin
@SpringBootTest
@AutoConfigureMockMvc
class TestingWebApplicationTest {
```
2. Lo invocamos, de tipo MockMvc.

```kotlin
    @Autowired
    lateinit var mockMvc: MockMvc;
```

3. Ejemplo de un test
```kotlin
    @Test
    fun retornoDefectoMensaje(){
        this.mockMvc
            .perform(get("/core/version"))
            .andDo(print())
            .andExpect(status().isOk)  // Miramo si es OK
        .andExpect(content().string(containsString("1.0.0"))) // Miramos si el resultado es correcto
}

```

## Capa de test

En este caso solo se prueba la capa de vista, cualquier cosa que utilize fuera de la capa web,  se lo tenemos que mock.

La diferencia con el anterior es la anotación **@WebMvcTest**

```kotlin
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
            .andExpect(  status().isOk)  // Miramo si es OK
            .andExpect(content().string(containsString("1.0.0"))) // Miramos si el resultado es correcto
    }
}
```
## Preparar la securización.

### Configurar la seguridad de los test.

La idea, es que utilicemos el perfil, para activar el registro en memoria.

1. Configuramos el aplication el resource de memoria.
```kotlin
    @Autowired
    lateinit var env: Environment;
    
    @Throws(java.lang.Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
    
        if(env.acceptsProfiles(Profiles.of("test"))) {
            val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
            auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(encoder.encode("admin"))
                .roles("USER", "ADMIN")
        }
    }
```
> Hay que poner el Encoder.
2. En la invocación de los test **@ActiveProfiles**.
```kotlin
@ActiveProfiles("test")
class CapaWebTest {
    ///
}
```
> Tenemos que activar los porfiles.

### Como pasar la autorización.

1. En el caso de **TestRestTemplate**
```kotlin
    restTemplate.withBasicAuth("user","password")
```
> Esto pasa la autorizaciónd del basic.
2. En el caso de **MockMvc**
```kotlin
        this.mockMvc
            .perform(get("/core/user")
                .with(
                    SecurityMockMvcRequestPostProcessors
                    .httpBasic("user", "password"))
            )
```
 
## Correcion del error que sale cuando sale un test

El problema radica en que no sabe donde esta el fichero de configuración de los logs

1. Creamos el fichero. (**resources/logging-test.properties**)
```properties
 .level=INFO
```
2. Lo configuramos en el build de gradle

```kotlin
tasks.test {
	useJUnitPlatform()
	// Correcion de la consola (https://github.com/junit-team/junit5/issues/1774)
	systemProperty ("java.util.logging.config.file", "${project.buildDir}/resources/test/logging-test.properties")
}
```

## Conceptos de los test

### Mock de servicio

Se utilizara mokito:

1. Como lo inyectamos al contexto:

```kotlin
@MockBean
	private GreetingService service;
```

2. Mockeamos la salida de un servicio

```kotlin
		when(service.greet()).thenReturn("Hello, Mock");
```

### DirtiesContext

Es una anotación que lo que hace es limpiar el contexto entre pruebas. 

- Si es a nivel de clase el sistema lo hace en cada uno de los metodos.
- Si se pone en el metodo lo hace a nivel de metodo.

Para mas información esta el siguiente guia: https://www.baeldung.com/spring-dirtiescontext 

## Recursos

* **GUIA TEST SPRING** https://spring.io/guides/gs/testing-web/
* **TEST SPRING** https://www.baeldung.com/oauth-api-testing-with-spring-mvc
* **TEST AUTENTIFICACIÓN** https://www.baeldung.com/spring-boot-security-autoconfiguration
* **Mokito kotlin** https://www.baeldung.com/kotlin/mockito