# 📦 Sistema de Gestión de Inventario

API REST para la gestión de **inventarios, materiales, almacenes, usuarios y movimientos de stock**, desarrollada con **Java y Spring Boot**.

El proyecto utiliza un enfoque basado en **Arquitectura Hexagonal (Ports and Adapters)**, buscando separar la lógica de negocio de los detalles de infraestructura. Además, incorpora autenticación mediante **JWT**, persistencia con **Spring Data JPA**, manejo de excepciones personalizadas e integración con servicios externos.

---

## 🚀 Características

* 🔐 Autenticación y autorización mediante **JWT**
* 👤 Gestión de usuarios y roles
* 🏢 Gestión de almacenes
* 📦 Gestión de materiales
* 🗂️ Gestión de tipos de material
* 📥 Registro de movimientos de entrada
* 📤 Registro de movimientos de salida
* 📊 Consulta de stock por almacén
* ⚠️ Validación de stock insuficiente
* 🔑 Recuperación y cambio de contraseña
* 📧 Envío de correos para recuperación de contraseña
* 🖼️ Gestión de imágenes mediante **Cloudinary**
* ❌ Manejo de excepciones personalizadas
* 📄 Documentación de API mediante **OpenAPI / Swagger**

---

## 🏗️ Arquitectura

El proyecto utiliza un enfoque basado en **Arquitectura Hexagonal (Ports and Adapters)**, con separación entre dominio, persistencia y exposición de la API.

```text
src/main/java/com/integrador/inventario
│
├── config
│   ├── SecurityConfig
│   ├── JwtSecurityConfig
│   ├── JwtAuthFilter
│   ├── CloudinaryConfig
│   └── OpenApiConfig
│
├── domain
│   ├── exception
│   ├── model
│   ├── repository
│   └── service
│
├── persistence
│   ├── almacen
│   ├── material
│   ├── movimiento
│   ├── tipoMaterial
│   ├── token
│   └── user
│
└── web
    ├── almacen
    ├── auth
    ├── cloudinary
    ├── material
    ├── movimiento
    ├── recuperacionPassword
    ├── tipoMaterial
    └── user
```

### Capas principales

**Domain**

Contiene los modelos de dominio, servicios, puertos de repositorio, reglas de negocio y excepciones.

**Persistence**

Contiene las entidades, repositorios JPA, adaptadores y mappers encargados de la persistencia.

**Web**

Expone la API REST mediante controllers, DTOs, mappers y manejadores de excepciones.

**Config**

Contiene la configuración relacionada con seguridad, JWT, Cloudinary, OpenAPI y otros componentes de infraestructura.

---

## 🛠️ Tecnologías

### Backend

![Java](https://img.shields.io/badge/Java-orange?style=flat-square\&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-brightgreen?style=flat-square\&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-brightgreen?style=flat-square\&logo=springsecurity)

### Persistencia

![JPA](https://img.shields.io/badge/JPA%2FHibernate-orange?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=flat-square\&logo=mysql)

### Herramientas y servicios

![Gradle](https://img.shields.io/badge/Gradle-Build%20Tool-02303A?style=flat-square\&logo=gradle)
![Git](https://img.shields.io/badge/Git-Version%20Control-F05032?style=flat-square\&logo=git)
![Swagger](https://img.shields.io/badge/OpenAPI%2FSwagger-85EA2D?style=flat-square\&logo=swagger)
![Cloudinary](https://img.shields.io/badge/Cloudinary-Images-3448C5?style=flat-square\&logo=cloudinary)

---

## 🔐 Seguridad

La API implementa autenticación basada en **JSON Web Tokens (JWT)**.

El flujo principal es:

```text
Cliente
   │
   │ Credenciales
   ▼
AuthController
   │
   ▼
AuthService
   │
   ▼
Validación de usuario
   │
   ▼
JWT generado
   │
   ▼
Cliente
   │
   │ Authorization: Bearer <token>
   ▼
JwtAuthFilter
   │
   ▼
Spring Security
   │
   ▼
Endpoint protegido
```

El sistema permite:

* Autenticación de usuarios
* Autorización mediante roles
* Protección de endpoints
* Validación de credenciales
* Identificación del usuario autenticado
* Identificación del almacén asignado
* Recuperación de contraseña mediante token

---

## 📦 Módulos principales

### 👤 Usuarios y roles

Permite gestionar usuarios y roles dentro del sistema.

### 🏢 Almacenes

Permite administrar almacenes y consultar los materiales disponibles en cada uno.

### 📦 Materiales

Permite gestionar los materiales registrados y asociarlos con su tipo correspondiente.

### 🗂️ Tipos de material

Permite administrar los tipos utilizados para clasificar los materiales.

### 🔄 Movimientos de inventario

Permite registrar movimientos de **entrada y salida** de materiales y mantener actualizado el stock.

El sistema valida reglas de negocio como el **stock insuficiente** antes de realizar una salida.

### 🔑 Recuperación de contraseña

Implementa un flujo para:

1. Solicitar la recuperación.
2. Generar un token.
3. Enviar un correo.
4. Validar el token.
5. Permitir el cambio de contraseña.

### 🖼️ Gestión de imágenes

Las imágenes asociadas a los recursos del sistema se gestionan mediante **Cloudinary**.

---

## 📚 API

La API está documentada mediante **OpenAPI / Swagger**, permitiendo consultar y probar los endpoints disponibles.

Una vez iniciada la aplicación:

```text
http://localhost:8090/swagger-ui/index.html
```

El puerto está configurado mediante:

```properties
server.port=8090
```

---

## ⚙️ Requisitos

Antes de ejecutar el proyecto necesitas:

* Java
* MySQL
* Git
* Cuenta de Cloudinary
* Cuenta de correo con SMTP habilitado

El proyecto incluye **Gradle Wrapper**, por lo que no es necesario instalar Gradle manualmente.

---

## 🔧 Configuración

El proyecto utiliza **variables de entorno** para mantener las credenciales fuera del código fuente.

Variables requeridas:

```env
DB_URL=jdbc:mysql://localhost:3306/inventario
DB_USERNAME=TU_USUARIO
DB_PASSWORD=TU_PASSWORD

CORREO=TU_CORREO
PASW_APLI=TU_PASSWORD_DE_APLICACION

JWT_SECRET=TU_CLAVE_SECRETA_JWT

CLOUDINARY_CLOUD_NAME=TU_CLOUD_NAME
CLOUDINARY_API_KEY=TU_API_KEY
CLOUDINARY_API_SECRET=TU_API_SECRET
```

La aplicación utiliza estas variables desde su configuración:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.mail.username=${CORREO}
spring.mail.password=${PASW_APLI}

jwt.secret=${JWT_SECRET}
```

> ⚠️ **Importante:** nunca subas credenciales reales, contraseñas, claves JWT o claves de Cloudinary al repositorio.

---

## ▶️ Ejecución

Clona el repositorio:

```bash
git clone https://github.com/GinoAMB/Proyecto_Buggati.git
```

Ingresa al proyecto:

```bash
cd inventario
```

Ejecuta la aplicación con Gradle Wrapper.

### Windows

```bash
.\gradlew bootRun
```

### Linux / macOS

```bash
./gradlew bootRun
```

Para compilar el proyecto:

```bash
.\gradlew build
```

---

## 🧪 Pruebas

Para ejecutar las pruebas:

```bash
.\gradlew test
```

---

## 📂 Estructura del proyecto

```text
inventario/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/integrador/inventario/
│   │   │       ├── config/
│   │   │       ├── domain/
│   │   │       ├── persistence/
│   │   │       └── web/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── gradle/
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
└── README.md
```

> Los directorios generados como `build/`, `.gradle/` y `.idea/` deben mantenerse fuera del repositorio mediante `.gitignore`.

---

## 🎯 Objetivo del proyecto

El proyecto fue desarrollado como una aplicación backend orientada a la gestión de inventarios, con énfasis en:

* Diseño y desarrollo de APIs REST
* Arquitectura de software
* Seguridad y autenticación
* Persistencia de datos
* Separación de responsabilidades
* Implementación de reglas de negocio
* Manejo de excepciones
* Integración con servicios externos
* Documentación de APIs

---

## 👨‍💻 Autor

**Gino Anderson Moreno Bejarano**

Desarrollador Full Stack Junior
**Java · Spring Boot · React · Node.js**

📍 Chimbote, Perú

* LinkedIn: linkedin.com/in/ginomorenobejarano
* GitHub: github.com/GinoAMB
* Email: [gino.anderson2011@gmail.com](mailto:gino.anderson2011@gmail.com)

---

⭐ Si este proyecto te resulta interesante, puedes dejar una estrella al repositorio.
