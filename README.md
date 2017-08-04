# HTTP Custom Response Server
Creado con propósitos didácticos 

Autor: Mariano García Mattío - email: [magm3333@gmail.com](mailto:magm3333@gmail.com)

### Instalar y Ejecutar

Para ejecutar se requiere JRE 1.7 o posterior.
Para compilar y empaquetar, se requiere Maven 3.x
 
```
$ mvn clean package
```
Una vez empaquetado, en la carpeta target se encuentra el archivo CustomHttpResnposerServer.jar, se puede ejecutar de la sieguiente forma:

```
$ java -jar CustomHttpResnposerServer.jar
```
### TODO
* Emitir errores de parseo en el mensaje de petición
* Agregar constantes de HTTP STATUS en HttpResponse
* En la Interface gráfica
  * Respuesta gráfica guiada (Wizzard), con la posibilidad de agregar cabeceras y body. Posibilidad de cargar el body desde un archivo. 
  * Respuestas preguardadas en archivos
* Escucho propuestas
* Recibo aportes con muchas ganas!  

License
----
[GPLv2](https://www.gnu.org/licenses/license-list.es.html#GPLv2)

Open Source & Libre!
