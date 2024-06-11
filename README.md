**BaseUrl** https://ejercicio-meli-dna-dot-springexample-424021.uc.r.appspot.com

## Endpoints

```/mutant``` Post

Body json de ejemplo, las posibles matrices a ingresar son cuadradas con letras G A T C la matriz entera debe contener esas letras sino devuelve 400 en otro caso.

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

```/reset-cache``` y ```/reset-db``` POST con body vacio resetear base o cache a cero.

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

## Docker

Ubicados donde esta el docker-compose.yml
**Crear una imagen y ejecutar el docker-compose, orquestador de containers:**

 ```docker compose up```

 Se puede ver en Docker Desktop que hay un container en el puerto 8080 ejecutandose.
 **Guardar el .tar de la imagen, ejecutar una terminal nueva:**
 ```docker save ejercicio > ejercicio.tar```

**Cargar la imagen:**
 ```docker load < ejercicio.tar```

 En Docker Desktop se verá la imagen cargada, hay que darle play para ejecutarla en un container.
 
 ## Otras cosas

para usar google engine aparte se debe crear una cuenta de servicios y darle permisos porque Mongo Atlas es un servicio en la nube.

 Instale una dependencia llamada actuator que agrega endpoints útiles
 
```/actuator/env``` GET con body vacio devuelve propiedades

ver más endpoints
https://www.baeldung.com/spring-boot-actuators
 

el archivo env.properties contiene propiedades que no quiero mostrar, se llama de application.propertiies.
se ignora en .gitignore pero en este ejemplo lo subi.

**el endpoint reset-db se debe llamar así para que no sepan que base de datos uso.**
