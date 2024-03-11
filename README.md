# Actividad Aprendizaje 1 - Acceso a datos

Proyecto en el que se desarrolla una API de temática libre. En este caso particular, se pueden manejar datos relacionados al Festival de Eurovisión y sus diferentes entidades.

- El modelo tiene 5 clases **Artist**, **Song**, **Edition**, **Country** y **Venue** relacionados entre sí, con sus correspondientes atributos.

- Sobre cada una de las clases se pueden realizar las **operaciones CRUD** y se controlan las https responses **400, 404 y 500**, además de las **200 y 201** donde corresponda.

- En todas las  clases, hay una **operación GET** que incluye un **filtrado** de al menos 3 campos diferentes así como una operación de **GET by Id**

- La api cuenta con un **fichero OpenAPI 3.0** (euroapi.yaml) en el que se definen las operaciones y los casos de error.
- La api hace uso de **DTOs** para el control de entrada y salida de datos.

- Cuenta con un **log** para crear trazas de errores y de ejecución.

- Se incluye una **colección Postman**
- Se ha usado **Git y Github** durante el desarrollo, usando los Issues.

## Como poner en marcha el proyecto

- Descárgate el proyecto de github y ábrelo en tu IDE de preferencia.
- Procura tener java configurado (en mi caso he trabajado con Java 17),  asi como el puerto del pc (por defecto, se te abrirá en el 8090, pero puedes cambiarlo en el *application.properties*.)
- Esta api usa el gestor de base de datos h2, también podrás configurar la contraseña y el usuario desde *application.properties*.
- Para lanzar el proyecto, abre la terminal de tu IDE y ejecuta el comando *mvn spring-boot:run* y comprueba los datos con la colección de Postman adjuntada al proyecto. 