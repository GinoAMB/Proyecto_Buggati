# Bugatti Inventory Project

Sistema de gestión de inventario diseñado para **Bugatti**, empresa peruana especializada en soluciones de seguridad industrial, salud ocupacional y equipos certificados.  
Este proyecto busca optimizar el control de materiales, movimientos de almacén, usuarios y procesos logísticos internos.

---

## 🧠 Objetivo del sistema

El sistema permite:

✅ Registrar y clasificar materiales (tipos, unidades, características)  
✅ Controlar entradas y salidas de almacén (movimientos)  
✅ Gestionar usuarios y roles con seguridad  
✅ Mantener trazabilidad de operaciones  
✅ Preparar la base para reportes e integración con frontend / sistemas externos

---

## 🏗 Arquitectura utilizada

Se implementa **Arquitectura Hexagonal (Ports & Adapters)** para lograr un sistema modular, escalable y mantenible.

<pre> ```txt src/main/java/com/integrador/inventario | ├── config/ # Configuración general (seguridad, CORS, beans) ├── domain/ # Lógica de negocio (Core) │ ├── model/ # Entidades del dominio (Almacen, Material, Movimiento, etc.) │ ├── repository/ # Puertos (interfaces) para acceso a datos │ └── service/ # Casos de uso / Reglas del negocio | ├── persistence/ # Implementación de acceso a datos (adaptadores) │ ├── entity/ # Entidades JPA │ ├── repository/ # Repositorios Spring Data (JpaRepository, CrudRepository) │ ├── mapper/ # MapStruct: Entity ↔ Domain Model │ └── adapter/ # Implementación de puertos del dominio | ├── web/ # Capa de presentación (API REST) │ ├── controller/ # Endpoints HTTP │ ├── dto/ # Entrada y salida de datos (records con @Validation) │ ├── mapper/ # Mappers DTO ↔ Model │ └── exception/ # Manejo de errores personalizados | └── InventarioApplication.java # Clase principal ``` </pre>



✅ El dominio no depende de frameworks  
✅ La infraestructura (BD, Web, Seguridad) se conecta mediante adaptadores  
✅ Fácil de testear, mantener y extender

---

## 🔧 Tecnologías principales

- Java 21
- Spring Boot 3.5
- Spring Data JPA (MySQL)
- Spring Security + JWT
- MapStruct
- Bean Validation (DTOs)
- Lombok
- OpenAPI / Swagger
- Gradle

---

## ✅ Beneficios del diseño

✔ Código limpio y mantenible  
✔ Alta separación de responsabilidades  
✔ Fácil de testear y extender  
✔ Reemplazo de tecnologías sin afectar el dominio  
✔ Estándar empresarial (puertos y adaptadores)

---

## 🚀 Próximas mejoras

- Gestión de permisos avanzados por rol
- Reportes de inventario y movimientos
- Notificaciones por bajo stock
- Integración con frontend (React, Angular, etc.)
- Auditoría y registro de historial
- Soporte para múltiples almacenes
