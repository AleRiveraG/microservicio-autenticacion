# Microservicio Autenticación

Este microservicio está dedicado exclusivamente a la seguridad de acceso y emisión de credenciales. Su función principal es recibir los intentos de inicio de sesión (login), validar las credenciales contra la base de datos de Usuarios, y en caso de éxito, generar y firmar el Token JWT que contiene los permisos y roles del usuario, sin este token, el acceso a las rutas protegidas de los demás microservicios será denegado.

## Dependencias

* Spring Web

* Spring Security

* JSON Web Token (JJWT)

* OpenFeign 

* Eureka

* Lombok
