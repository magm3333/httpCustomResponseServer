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
### TODO
* Emitir errores de parseo en el mensaje de petición
* Agregar constantes de HTTP STATUS en HttpResponse
* En la Interface gráfica
  * Respuesta gráfica guiada (Wizzard), con la posibilidad de agregar cabeceras y body. 
  * Guardar prerespuestas en archivo
  * Chequear que siempre existan los defaults (no escribirlos)  
  * Respuestas automática en base a verbo y path?
  * Reemplazo optativo de variables en body?
* Interface gráfica Web!
* Escucho propuestas
* Recibo aportes con muchas ganas!  

License
----
[GPLv2](https://www.gnu.org/licenses/license-list.es.html#GPLv2)

Open Source & Libre!
