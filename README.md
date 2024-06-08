**BaseUrl** https://ejercicio-meli-dna-dot-springexample-424021.uc.r.appspot.com

## Endpoints

```/mutant``` Post

Body json de ejemplo

```
{
    "dna": [
        "CCGCCA",
        "CCGTCA",
        "AGTACG",
        "CACGAA",
        "ACCTGA",
        "AAAAAG"
    ]
}
```
Retorna 200 y true si es mutante, si no es retorna 403 y false.

```/ping``` GET  retorna http status 200 y pong, para ver que la aplicación fue deployada y esta andando.

 ```/stats``` Get ver las estadisticas.

```/reset-cache``` y ```/reset-mongo``` POST con body vacio resetear mongo o cache a cero.

crear jar parados donde esta el pom.xml ```mvn clean package```
se genera el .jar en target/
con nombre ejercicio-0.0.1.jar

para ejecutarlo fuera de target ```java -jar target/ejercicio-0.0.1.jar```
dentro de target ```java -jar ejercicio-0.0.1.jar```

## Deployar en google app engine

Bajar el google app engine CLI que son comandos de google engine app para la terminal.

Una vez creado el .ar ejecutar afuera de /src
hay que crear un app.yaml en el directorio donde esta el pom.xml, es el archivo de configuración de google app engine
 ```gcloud app deploy target/ejercicio-0.0.1.jar --appyaml=app.yaml```

 ## Otras cosas

 Instale una dependencia llamada actuator que agrega endpoints útiles
 
```/actuator/env``` GET con body vacio devuelve propiedades

ver más endpoints
https://www.baeldung.com/spring-boot-actuators
 

el archivo env.properties contiene propiedades que no quiero mostrar, se llama de application.propertiies.
se ignora en .gitignore pero en este ejemplo lo subi.

el endpoint reset-mongo se debe llamar reset-db para que no sepan que uso mongodb.
