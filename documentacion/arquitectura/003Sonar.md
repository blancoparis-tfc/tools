# Sonar

Vamos a ver como configurar el sonar desde gradle

> La información para configurar el sonar hay que buscarla en la configuración de sonar

## Obtener la información:

* **organizaction:** blancoparis-tfc
* **ProyectKey:** blancoparis-tfc_tools
* **URL** https://sonarcloud.io
* **TOKEN** 

> La url es la del servidor de sonar o en este caso sonarcloud.

## Configurar en sonar gradle.

1. Indicamos la version del plugin de sonar

```kotlin
plugins {
    //...
	id( "org.sonarqube" ) version "3.3"
    //...
}
```

2. Configuramos el sonar

Esta información la obtenemos **Analyze from your local sources** dentro de sonarcloud

``` kotlin
sonarqube{
	properties {
		property ("sonar.projectKey", "blancoparis-tfc_tools")
		property ("sonar.organization", "blancoparis-tfc")
		property ("sonar.host.url", "https://sonarcloud.io")
	}
} 
```

## Configurar el ciclo de vida

La idea es que:
- Cuando se ejecuten los test se monte la cobertura.
- El sonar antes de ejecutarse lanzara los test.

1. Que se ejecute la cobertura una finalizado los test
```kotlin
// Cuando termine la de test, generaremos el report de cobertura
tasks.named("test").configure{
	finalizedBy("jacocoTestReport")
}
```
2. Antes de lanzar el sonar que se ejecute los test y la cobertura.

```kotlin
// Cuando se ejecute sonarqube  se lanzara test antes de sonarqube.
tasks.named("sonarqube").configure {
	dependsOn("test")
}
```

## Configurar el token

En este caso tenemos que ponerlo,  

```properties
systemProp.sonar.login=<token>
```

## Configurar el tema de las propiedades


1. En este caso se tiene que establecer la propiedades **ignoreFailures**.

Tenemos que añadir la siguiente logica 

```kotlin
    ignoreFailures = (project.hasProperty("testIgnoreFailures") && project.property("testIgnoreFailures")=="true")
```

```kotlin
tasks.test {
    useJUnitPlatform()
    // Correcion de la consola (https://github.com/junit-team/junit5/issues/1774)
    systemProperty ("java.util.logging.config.file", "${project.buildDir}/resources/test/logging-test.properties")
    ignoreFailures = (project.hasProperty("testIgnoreFailures") && project.property("testIgnoreFailures")=="true")
}
```

2. Uso
* **Propiedad** -PtestIgnoreFailures=true
* **gradle.properties**  testIgnoreFailures=false

> Pordefecto mantiene el comportamiento, de gradle.