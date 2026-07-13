# Sistema Veterinaria — Arquitectura de Microservicios

Proyecto semestral de la asignatura **DSY1103 – Desarrollo FullStack 1**, DuocUC.
Sistema de gestión integral para una clínica veterinaria, construido como una arquitectura distribuida de **11 microservicios** independientes (10 de dominio + 1 API Gateway), cada uno con su propia base de datos, siguiendo el patrón **Controller–Service–Repository (CSR)**.

## Integrantes

- (agrega aquí los nombres del equipo)

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

---

# Pauta de Evaluación — Rúbrica (EFT DSY1103)

**Ponderación total de la asignatura de esta evaluación: 40%**

## Dimensión Entrega de Encargo (Grupal) — 40%

| Indicador de Evaluación | Ponderación |
|---|---|
| IE 1.1.1 – Diseña endpoints REST aplicando principios de arquitectura distribuida, métodos HTTP y nombres semánticos, validando comunicación y respuesta conforme a principios REST | 4% |
| IE 1.2.1 – Estructura el microservicio aplicando el patrón CSR (Controller–Service–Repository/Model) | 2% |
| IE 1.3.1 – Implementa mecanismos de validación de datos de entrada | 3% |
| IE 1.3.2 – Aplica estructuras de manejo de errores para controlar y mantener la estabilidad del servicio | 3% |
| IE 2.1.1 – Modela esquemas de base de datos normalizados y coherentes, integrando CRUD completo | 4% |
| IE 2.2.1 – Implementa reglas de negocio y validaciones con Bean Validation en la capa de servicio | 4% |
| IE 2.3.1 – Implementa manejo global/específico de excepciones con logs estructurados | 3% |
| IE 2.4.1 – Implementa comunicación REST entre microservicios mediante DTOs, controlando errores remotos | 3% |
| IE 2.5.1 – Gestiona un repositorio GitHub ordenado, con commits técnicos y tablero colaborativo (Trello) | 2% |
| IE 3.1.1 – Desarrolla pruebas unitarias con cobertura mínima del 80% sobre funciones y reglas de negocio | 4% |
| IE 3.2.1 – Elabora documentación técnica clara con Swagger/OpenAPI | 4% |
| IE 3.3.1 – Configura archivos YAML, rutas del Gateway y despliega el ecosistema completo | 4% |
| **Subtotal Entrega de Encargo** | **40%** |

## Dimensión Defensa (Individual) — 60%

| Indicador de Evaluación | Ponderación |
|---|---|
| IE 1.3.3 – Ejecuta pruebas de comunicación REST verificando respuestas y códigos HTTP de cada endpoint | 5% |
| IE 2.1.3 – Explica con claridad el modelado de entidades, relaciones y estructura, justificando decisiones técnicas | 5% |
| IE 2.1.4 – Realiza modificaciones en vivo sobre CRUD, reglas de negocio o validaciones | 10% |
| IE 2.2.2 – Justifica la lógica de negocio implementada en la capa de servicio, con cambios en vivo | 4% |
| IE 2.3.2 – Realiza cambios en el código demostrando control de logs, excepciones y códigos HTTP | 4% |
| IE 2.4.2 – Explica el flujo de comunicación entre microservicios y realiza cambios en la comunicación remota | 4% |
| IE 2.5.2 – Explica su aporte personal al proyecto, respaldado con evidencia en GitHub/Trello | 5% |
| IE 3.1.2 – Explica el funcionamiento de las pruebas unitarias e integra nuevas pruebas en tiempo acotado | 12% |
| IE 3.2.2 – Explica la documentación técnica generada con Swagger/OpenAPI, verificando consistencia con el código | 4% |
| IE 3.3.2 – Realiza la configuración y ejecuta los microservicios de forma individual y sin apoyo | 7% |
| **Subtotal Defensa** | **60%** |

### Escala de logro

| Categoría | % logro |
|---|---|
| Muy buen desempeño | 100% |
| Buen desempeño | 80% |
| Desempeño aceptable | 60% |
| Desempeño incipiente | 30% |
| Desempeño no logrado | 0% |

> ⚠️ La defensa es **individual**: la nota depende exclusivamente de lo que cada estudiante demuestre, independientemente de si el proyecto está bien construido grupalmente. No se permite IA ni asistencia externa durante la sesión de defensa.
