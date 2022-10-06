# MeliTechnicalExamMutants
## _Ejercicio DNA mutante_

[![JAVA](https://jmonkeyengine.org/images/java-logo.png)](https://www.java.com/es/)

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Esta aplicación permite clasificar humanos y mutantes a partir de su DNA.

## Características

- Con base al DNA de cada sujeto a clasificar, se identifica si es humano o mutante.
- Cuenta con estadísticas de uso, que permiten identificar cuantas clasificaciones se han realizado por cada tipo de sujeto y nos presenta la razón de cuantos mutantes hay por cada humano.
- La comunicación se realiza a través de servicios rest
- La información procesada se guarda en una base de datos no relacional para su uso en las estadísticas.

## Tecnologías

Este proyecto utiliza las siguientes herramientas openSource para funcionar correctamente:

- [Rest] - Tipo de servicio expuesto para recibir las peticiones.
- [Dynamo](https://aws.amazon.com/es/dynamodb/) - Base de datos no relacional de Amazon.
- [EC2](https://aws.amazon.com/es/ec2/) - Host de la aplicación en la nube de Amazon.
- [Java](https://www.java.com/es/)- Lenguaje de programación del código fuente
- [Junit] - Framework para la automatización de las pruebas
- [Bootstrap] - IDE para la códificación
- [Jacoco] - Plugin para medición de cobertura de las pruebas automáticas
- [JMeter] - Programa para las pruebas de estrés y rendimiento de la aplicación
- [Maven] - Herramienta de gestión de dependencias

## Instalación

La aplicación requiere [Java](https://www.java.com/es/) v8+ para ejecutar. Descargamos la versión correspondiente y seguimos los pasos.

Tambien se requiere tener una cuenta en [Amazon AWS](https://aws.amazon.com/es/) con credenciales generadas para acceso. 

Dentro de AWS, se deben crear dos tablas dentro del producto DynamoDb, con las siguientes características:

| Nombre | Clave Partición | Tipo Clave Partición |
| ------ | ------ | ------ |
| MutantDnaTable | DnaKey  | Numérica/Number |
| MutantDnaStatsTable | DnaStatsKey | Texto/String |

Se sugiere utilizar el IDE eclipse con Sprintboot incorporado.
El proyecto usa maven, por ende para abrirlo se puede usar en IDE:
```sh
File -> import
-> Existing Maven Project
-> Click derecho al proyecto -> run -> maven install
```

## Plugins

Para ejecutar las pruebas de cobertura y de estres se necesitan las siguienes herramientas

| Plugin | 
| ------ | 
| [Jacoco](https://www.eclemma.org/jacoco/) |
| [JMeter](https://jmeter.apache.org/) |

## Ejecución

### 1. Ejecutar proyecto
Para ejecutar el proyecto, debemos crear un run configuration de tipo SpringbootApplication.

Dentro del run configuration, pestaña arguments, definir los siguientes Vm Arguments:

```sh
-Daws.region=us-east-1
-Daws.access_key={yourAwsAccessKey}
-Daws.-Daws.secret_key={yourAwsSecretKey}
```
> Es de anotar que los parámetros son requeridos y se deben reemplazar: `--yourAwsAccessKey` `--yourAwsSecretKey` por las claves generadas en AWS.

Se da click en run y listo! La aplicación esta lista y los servicios están desplegados para ser utilizados. Ver la documentación de estos más adelante.

### 2. Ejecutar Cobertura de Pruebas

Cuando se requiera ejecutar el test de cobertura, se debe proceder de forma similar que el paso anterior. Una vez descargado el plugin por el marketplace del IDE, procedemos a crear un nuevo run configuration pero de tipo maven.
Definimos en el campo goal colocamos verify y damos click en run.

Una vez finalizado, se pueden ver los resultados abriendo el archivo index.html, de la carpeta target/site en la raiz del proyecto.

`IMPORTANTE: El proyecto en su última versión cuenta con un porcentaje de cobertura del 87%`

## Servicios

### 1. Servicio de verificación de código DNA

Este servicio permite verificar si un DNA que pasamos en el cuerpo de la petición en forma de arreglo, pertenece a un mutante o no.

**URL** : `http://localhost/mutant/`
**Method** : `POST`
**Auth required** : NO
**Permissions required** : None
**Data constraints**
Arreglo con la cadena DNA. Es Obligatorio
```json
{
    "dna": ["ATGC", "TTAA", "GGCC","CGTA"]
}
```

## Success Response

**Condition** : Si dentro del arreglo existen cuatro letras consecutivas de manera horizontal, vertical u oblicua. Es decir, el DNA es mutante.
**Code** : `200 OK`
**Content** Vacío

## Error Response

**Condition** : Si dentro del arreglo no existen cuatro letras consecutivas de manera horizontal, vertical u oblicua. Es decir, el DNA es humano.
**Code** : `403 FORBIDDEN`
**Content** : `ADN informed is not mutant`

## OPCIONAL
En el ejercicio se creó inicialmente solo un método de búsqueda dentro del DNA, pero se publican otros dos métodos para fines educativos y de evaluación. Si se desea seleccionar entre los tres métodos, se debe adicionar el parámetro en el encabezado de la petición:
**Parameter** : `searchMethod`
**Value**: `los posibles valores son: 1 -> busqueda por defecto(ciclos), 2 para buscar con algoritmo recursivo. 3->Algoritmo fusionado del 1 y 2. 

### 2. Servicio de consulta de estadistícas de verificaciones de código DNA

Este servicio permite verificar las estadistícas de uso de las verificaciones. Retorna el número de 

**URL** : `http://localhost/stats/`
**Method** : `GET`
**Auth required** : NO
**Permissions required** : None
**Data constraints** : None

## Success Response

**Condition** : Se retornan las estadísticas.
**Code** : `200 OK`
**Content** 
```json 
{"count_mutant_dna":40, "count_human_dna":100: "ratio":0.4}
```
Donde `count_mutant_dna`, es el número de DNA validados como mutantes
`count_human_dna`, es el número de DNA validados como humanos
`ratio`, es la razón de mutantes por cada humano.

## Error Response

**Condition** : Error dentro de la aplicación.No debería mostrarse en un caso normal
**Code** : `500 Internal Server Error`
**Content** : `ADN informed is not mutant`


## Licencia

OpenSource
