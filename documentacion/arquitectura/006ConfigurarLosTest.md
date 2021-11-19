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

## Recursos

* **TEST SPRING** https://www.baeldung.com/oauth-api-testing-with-spring-mvc
* **TEST AUTENTIFICACIÓN** https://www.baeldung.com/spring-boot-security-autoconfiguration