# HTTP Custom Response Server
Creado con propósitos didácticos 

Autor: Mariano García Mattío - email: [magm3333@gmail.com](mailto:magm3333@gmail.com) - Twitter: [@magm3333](https://twitter.com/magm3333)

### Instalar y Ejecutar

Para ejecutar se requiere JRE 1.7 o posterior.
Para compilar y empaquetar, se requiere Maven 3.x
 
```
$ git clone https://github.com/magm3333/httpCustomResponseServer
$ cd httpCustomResponseServer
$ mvn clean package
```
Una vez empaquetado, en la carpeta target se encontrará el archivo CustomHttpResnposerServer.jar, se puede ejecutar de la siguiente forma:

```
$ java -jar CustomHttpResnposerServer.jar
```

### Expresiones en el body de la respuesta
Existen dos tipos de expresiones, simples y full body, las expresiones simples se pueden combinar en el cuerpo de la respuesta, las de tipo full body no se pueden combinar y solo puede existir una.
* Simples
  * ${now[;format} : se convertirá en la fecha/hora actual formateada, el formato es opcional y sigue las especificaciones de [SimnpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html). El formato por defecto es: dd/MM/yyy H:s:m
* Full Body
  * ${selectFile[;ext]} : permite al usuario seleccionar el archivo a enviar en la respuesta. El parámetro opcional ext, permite especificar la o las extensiones de arhivo que se podrán seleccionar, si hay más de una se separa por coma. Ejemplo: {selectFile[;jpg,jpeg]}
  * ${loadFile;localPath} : permite cargar automáticamente, sin intervensión del usuario, el archivo a enviar en la respuesta. Ejemplo: ${loadFile;/home/magm/archivo.pdf}


### TODO
* Emitir errores de parseo en el mensaje de petición
* Agregar constantes de HTTP STATUS en HttpResponse
* En la Interface gráfica
  * Respuesta gráfica guiada (Wizzard), con la posibilidad de agregar cabeceras y body. 
  * Respuestas automática en base a verbo y path?
  * Calcular el tipo mime cuando en las expresiones ${selectFile} y ${loadFile;localPath}
  * Respuesta automática para favicon
* Interface gráfica Web!
* Escucho propuestas
* Recibo aportes con muchas ganas!  

License
----
[GPLv2](https://www.gnu.org/licenses/license-list.es.html#GPLv2)

Open Source & Libre!
