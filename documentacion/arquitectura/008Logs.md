# Logs

En este caso vamos a utilizar la libreria slfj4.

## Configurarlo

Aparte de los ficheros de logs estandar... podemos usar la siguiente configuración:

```yaml
logging:
  level:
    root: DEBUG
```

## Usarlo

1. Instanciarlo en la clase
```kotlin

class ToolsApplication	: WebSecurityConfigurerAdapter() {

    private val logger = LoggerFactory.getLogger(javaClass)
}

```
2. Usarlo

```kotlin
			logger.info("Cargamos la autenticacion en memoria para el entorno de test")

```

## Recursos

https://www.baeldung.com/kotlin/logging