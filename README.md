# Servidor-FTP

*Aplicacion de Escritorio realizada por Italo Visconti 💯* 

Servidor FTP, programado utilizando la libreria de Apache FTP Server, con manejo automatico de threads y clientes simultaneos.

Posibilita tambien la creacion y manejo de datos para la autenticacion de clientes (Usuario, Contraseña y Carpeta Raiz), asi como visualizacion de los archivos de todos los usuarios.

Cuenta adicionalmente con un archivo Log, donde se registran todos los inicios de sesion de los clientes al servidor.

Tiene una foto de Hasbulla.

---
## Instalacion

Importar el proyecto, cambiar el JRE dependiendo de la version de su computador, referenciar librerias encontradas en la carpeta Lib (esto ya deberia de estar hecho), compilar. 👍

Al momento de Iniciar por primera vez el servidor, se crea un usuario test, de contrasena test.

---
### Errores
Ninguno esta vaina es perfecta.

Mentira, hay varios:
- Cuando se detiene el servidor, no se puede volver a empezar, esto debido a una restriccion de la libreria de Apache.
- Es posible que el puerto con el que iniciaste el servidor quede abierto, por lo tanto al momento de volver a iniciar el servidor, es probable que debas cambiar el puerto. 
- El usuario debe autenticarse cada vez que desea cargar un archivo, por lo tanto en el log se mostrara varias veces el inicio de sesion de un mismo usuarios.

---
## Screenshots

*Ventana Principal*

![ss1](https://user-images.githubusercontent.com/108308939/221065369-5ad47733-40b5-45ba-831b-1753b186b4ea.png)

*Ventana Principal (Server Corriendo)*

![ss2](https://user-images.githubusercontent.com/108308939/221065420-a6b31257-d818-43b2-98e6-8a1cb152b76e.png)

*Ventana Agregar Usuario*

![ss3](https://user-images.githubusercontent.com/108308939/221065440-bf7438a8-8966-41ba-ae87-a18a6b243995.png)

*Ventana Ver Usuarios*

![ss4](https://user-images.githubusercontent.com/108308939/221065452-e571e5b0-7dec-4ec0-bae7-6de218d8297c.png)
