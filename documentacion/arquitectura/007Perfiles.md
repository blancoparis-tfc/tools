# La idea es montar los perfiles de la aplicación

En este caso vamos a tener 3 profiles:
* **dev** Es el entorno de desarrollo
* **test** Es el entorno para realizar los test
* **prod** Es el de producción

## Configurar los profile

En esta caso hay que tener tres cosas:

* Como arrancar con uno de los entornos.
* Como establecer las configuraciones de cada entorno.
* Como establecer un componente de spring a un entorno

### Arrancar spring boot con uno de los entornos.

La idea es crear una tarea por cada uno de los entornos, en nuestro caso para ejecucición vamos a tener 2:
* **Dev:** Es el de desarrollo.
* **Prod:** Es el entorno de producción.

Vamos a ver como se configura un entorno en gradle miramos los siguientes propiedades:
* **Group** Indicamos el grupo de tareas, en este caso es de **application**
* **classpath** Establecemos el classpath, en este caso tenemos que poner este **sourceSets["main"].runtimeClasspath**
* **mainClass** Es el punto de entrada para arrancar springboot **com.dbp.tools.ToolsApplicationKt**
> En este caso tenemos ponerle el sufijo Kt, en la clase del main (Es por usar kotlin)

* Establecemo la propiedad de profile activo.

**dev**

```kotlin
tasks.register<org.springframework.boot.gradle.tasks.run.BootRun>("bootRunDev") {
	group = "application"
	classpath = sourceSets["main"].runtimeClasspath
	mainClass.set("com.dbp.tools.ToolsApplicationKt")
	systemProperty ("spring.profiles.active", "dev")
}
```

**prod**
```kotlin
tasks.register<org.springframework.boot.gradle.tasks.run.BootRun>("bootRunProd") {
	group = "application"
	classpath = sourceSets["main"].runtimeClasspath
	mainClass.set("com.dbp.tools.ToolsApplicationKt")
	systemProperty ("spring.profiles.active", "prod")
}
```

### Configuraciones 

En nuestro caso vamos a utilizar el fichero **application.yml**, utilizaremos el marcado **---** y con la anotacion profiles,

> El previo a estas configuraciones es comun para todos.

```yaml

---

spring:
  profiles: test
logging:
  level:
    root: WARN

---

spring:
  profiles: dev
logging:
  level:
    root: DEBUG

---

spring:
  profiles: prod
logging:
  level:
    root: INFO
```

### Poner un componete en spring

En principio, si se utiliza la anotación **@Profile**