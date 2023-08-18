# Home4Pets_AdopcionMascotas

Bienvenido al sistema Home 4 Pets, una aplicación para la adopción de perros y gatos.

Esta aplicación permite la publicación de perros y gatos en busca de un hogar con personas amantes de los gatos y/o perros

## REQUISITOS PREVIOS 

- JDK 17
- Cualquier versión de MySQL

## INICIACIÓN DEL PROYECTO
1. **CLONAR O DESCARGAR EL PROYECTO**

    En primer lugar, clona o descarga nuestro proyecto desde la rama MASTER, en tu equipo.

2. **CREAR BASE DE DATOS EN MYSQL**

    Antes de ejecutar el proyecto, crea en MySQL una base de datos con el nombre **home4petsbd**, a continuación te mostramos la sentencia:

        CREATE DATABASE home4petsbd;

3. CONFIGURAR EL ARCHIVO *application.properties*

     Después de haber creado exitosamente la base de datos, configura el application.properties colocando los datos correspondientes de tu usuario en MySQL.

        spring.datasource.username = root spring.datasource.password = root

4. CREAR DIRECTORIO EN LA RAÍZ DEL DISCO LOCAL DE TU COMPU

     Una vez hecho lo anterior, crearemos una carpeta en el directorio principal de tu disco local, con el siguiente nombre:
   
        C:/images

5. EJECUTAR EL PROYECTO

   Una vez que ejecute la aplicación creará las entidades de la base datos, por lo que ahora si podemos realizar registros

   Para acceder al proyecto ir a la ruta **http://localhost:8080/**

7. CREAR REGISTROS EN LA TABLA role

     Para poder acceso a la aplicación debemos de crear los roles iniciales así como usuarios y otros datos de prueba

        USE home4petsdb;
        
        INSERT INTO role(id, name) VALUES (1 , 'ROLE_ADMINISTRADOR'), (2, 'ROLE_VOLUNTARIO'), (3, 'ROLE_ADOPTADOR');
        
        INSERT INTO users (id, created_at, enabled, fist_lastname, name, password, second_lastname, username) VALUES (1, '2022-03-21 10:23:34', 1, 'Martínez','Enrique', '$2a$10$ril3o2EeV8py8pP5rTWbMunh0NFr6hy6pwsiqhWZPAnpreD6yPUpi', 'Ojeda','admin@adoptame.com');
        
        INSERT INTO color (name) VALUES ('Blanco'), ('Café'), ('Gris'), ('Negro'), ('Atigrado'), ('Bicolor'), ('Con manchas'), ('Varios colores');
        
        INSERT INTO personality(name) VALUES ('Activo'), ('Independiente'), ('Juguetón'), ('Protector'), ('Ruidoso'), ('Tímido'), ('Tranquilo'), ('Amoroso');
        
        INSERT INTO size(name) VALUES ('Pequeño') , ('Mediano'), ('Grande');
        
        INSERT INTO users (id, created_at, enabled, fist_lastname, name, password, second_lastname, username) VALUES (2, '2022-03-21 10:23:34', 1, 'Ortiz','Michel', '$2a$10$ril3o2EeV8py8pP5rTWbMunh0NFr6hy6pwsiqhWZPAnpreD6yPUpi', 'Vega','voluntario@adoptame.com');
        
        INSERT INTO users (id, created_at, enabled, fist_lastname, name, password, second_lastname, username) VALUES (3, '2022-03-21 10:23:34', 1, 'Ayala','Cynthia', '$2a$10$ril3o2EeV8py8pP5rTWbMunh0NFr6hy6pwsiqhWZPAnpreD6yPUpi', 'Calderon','adoptador@adoptame.com');
        
        INSERT INTO authorities (username, authority) VALUES (1,1),(2,2),(3,3);

Con lo anterior podrás acceder mediante el usuario y contraseña "adno1234".

### DATOS DE ACCESO

**- ADMINISTRADOR**
  
  USUARIO:
        admin@adoptame.com
  
  CONTRASEÑA:
        adno1234


**- VOLUNTARIO**
  
  USUARIO:
        voluntario@adoptame.com
  
  CONTRASEÑA:
        adno1234


**- ADOPTADOR**
  
  USUARIO:
        adoptador@adoptame.com
  
  CONTRASEÑA
        adno1234

