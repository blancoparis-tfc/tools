# Configurar la cobertura

La idea es ver como configurar la cobertura.

## Cofigurar jococo

* 1º Configurar en gradle
```kotlin
plugins { 
	id ("jacoco")
}


```

* 2º Configurar la cobertura
```kotlin
tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
		csv.required.set(true)
	}
}
```