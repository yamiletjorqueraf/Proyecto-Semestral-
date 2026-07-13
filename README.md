# Sistema Veterinaria — Arquitectura de Microservicios

Proyecto semestral de la asignatura **DSY1103 – Desarrollo FullStack 1**, DuocUC.
Sistema de gestión integral para una clínica veterinaria, construido como una arquitectura distribuida de **11 microservicios** independientes (10 de dominio + 1 API Gateway), cada uno con su propia base de datos, siguiendo el patrón **Controller–Service–Repository (CSR)**.

## Integrantes

- Yamilet Jorquera y Paulina Muñoz

## Contexto del proyecto

El sistema modela el funcionamiento de una clínica veterinaria: registro de dueños y sus mascotas, personal médico, agendamiento de citas, hospitalizaciones, resultados de exámenes, farmacia/stock de medicamentos, y el ciclo de ventas y pagos asociado a cada atención.

## Arquitectura

```
                        ┌────────────────┐
                        │  API Gateway   │  :8080
                        └───────┬────────┘
        ┌───────────┬───────────┼───────────┬───────────┐
        ▼           ▼           ▼           ▼           ▼
   ms-usuario   ms-dueno   ms-mascota  ms-personal   ms-cita
     :8091       :8082       :8083       :8085       :8084
        │           │           │           │           │
   db_usuarios  db_duenos  db_mascotas db_personal  db_citas

        ┌───────────┬───────────┬───────────┬───────────┐
        ▼           ▼           ▼           ▼           ▼
   ms-ventas    ms-pago   ms-hospital.  ms-result.  ms-farmacia
     :8089       :8090       :8086       :8087       :8088
        │           │           │           │           │
   db_ventas    db_pago  db_hospital.  db_resultados db_farmacia
```

Cada microservicio:
- Expone su propia API REST documentada con **Swagger/OpenAPI**.
- Gestiona su propia base de datos MySQL, con esquema versionado mediante **Liquibase**.
- Sigue el patrón **Controller → Service → Repository/Model**, separando el manejo de peticiones REST, la lógica de negocio y el acceso a datos.
- Usa **DTOs** para exponer y recibir datos, evitando acoplar la API a las entidades JPA internas.
- Se comunica con otros microservicios vía **Feign Client** (ej. `ms-dueno` consulta a `ms-usuario`; `ms-ventas` consulta a `ms-dueno` y `ms-mascota`).
- Incluye **pruebas unitarias** (JUnit + Mockito) sobre la capa de servicio.

## Microservicios

| Microservicio | Puerto | Responsabilidad | Base de datos |
|---|---|---|---|
| `ms-usuario` | 8091 | Cuentas de acceso al sistema (admin, dueño, veterinario) | `db_usuarios` |
| `ms-dueno` | 8082 | Datos de los dueños de mascotas | `db_duenos` |
| `ms-mascota` | 8083 | Registro de mascotas y su dueño asociado | `db_mascotas` |
| `ms-personal` | 8085 | Personal de la clínica (veterinarios, recepción) | `db_personal` |
| `ms-cita` | 8084 | Agendamiento de citas (mascota + personal) | `db_citas` |
| `ms-ventas` | 8089 | Ventas/servicios prestados a un dueño/mascota | `db_ventas` |
| `ms-pago` | 8090 | Pagos asociados a una venta | `db_pago` |
| `ms-hospitalizacion` | 8086 | Hospitalizaciones de mascotas | `db_hospitalizacion` |
| `ms-resultados-examenes` | 8087 | Resultados de exámenes médicos | `db_resultados` |
| `ms-farmacia` | 8088 | Stock y precios de medicamentos | `db_farmacia` |
| `api-gateway` | 8080 | Punto de entrada único hacia todos los microservicios | — |

## Documentación Swagger / OpenAPI

Cada microservicio expone su documentación interactiva en:

| Microservicio | Swagger UI | OpenAPI JSON |
|---|---|---|
| ms-usuario | http://localhost:8091/swagger-ui.html | http://localhost:8091/api-docs |
| ms-dueno | http://localhost:8082/swagger-ui.html | http://localhost:8082/api-docs |
| ms-mascota | http://localhost:8083/swagger-ui.html | http://localhost:8083/api-docs |
| ms-personal | http://localhost:8085/swagger-ui.html | http://localhost:8085/api-docs |
| ms-cita | http://localhost:8084/swagger-ui.html | http://localhost:8084/api-docs |
| ms-ventas | http://localhost:8089/swagger-ui.html | http://localhost:8089/api-docs |
| ms-pago | http://localhost:8090/swagger-ui.html | http://localhost:8090/api-docs |
| ms-hospitalizacion | http://localhost:8086/swagger-ui.html | http://localhost:8086/api-docs |
| ms-resultados-examenes | http://localhost:8087/swagger-ui.html | http://localhost:8087/api-docs |
| ms-farmacia | http://localhost:8088/swagger-ui.html | http://localhost:8088/api-docs |

## Ejecución local

### Requisitos
- Docker Desktop
- MySQL (XAMPP) corriendo en `localhost:3306`, usuario `root` sin contraseña

### Levantar el proyecto

```bash
docker compose up --build
```

Esto construye las 11 imágenes (multi-stage build con Maven) y levanta todos los contenedores. Cada microservicio crea su propia base de datos automáticamente (`createDatabaseIfNotExist=true`) y Liquibase crea las tablas + datos de prueba iniciales.

### Verificar estado

```bash
docker compose ps
```

### Apagar

```bash
docker compose down
```

## Tecnologías utilizadas

- **Spring Boot** — framework base de cada microservicio
- **Spring Data JPA / Hibernate** — persistencia de datos
- **Liquibase** — versionado y migración de esquema de base de datos
- **MySQL** — motor de base de datos
- **Spring Cloud OpenFeign** — comunicación REST entre microservicios
- **Spring Cloud Gateway** — API Gateway
- **springdoc-openapi (Swagger)** — documentación interactiva de la API
- **JUnit + Mockito** — pruebas unitarias
- **Docker / Docker Compose** — contenerización y orquestación
- **Bean Validation (JSR 380)** — validación de datos de entrada


