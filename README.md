# GymTracker API

API REST para gestión de rutinas y seguimiento semanal de entrenamiento.

## Stack
- Java 21
- Spring Boot 4
- PostgreSQL
- JPA / Hibernate
- OpenAPI / Swagger UI

## Cómo correrlo
1. Clonar el repositorio
2. Crear una base PostgreSQL llamada `gymtracker`
3. Si hace falta, sobrescribir credenciales con `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`
4. `./mvnw spring-boot:run`

## Datos de prueba
- Al iniciar la app se carga `src/main/resources/data.sql`
- Usuario demo: `demo@gymtracker.local`
- Password demo: `1234`

## Frontend React
1. Ir a `../gymtracker-frontend/`
2. Instalar dependencias con `npm install`
3. Levantar con `npm run dev`
4. El frontend usa `VITE_API_URL`, por defecto `http://localhost:8080`

## CORS
- Permitidos por defecto: `http://localhost:5173` y `http://localhost:3000`
- Se configura con `app.cors.allowed-origins`

## Documentacion API
- OpenAPI JSON: `/v3/api-docs`
- Swagger UI: `/swagger-ui/index.html`

## Tests
- El perfil `test` usa H2 en memoria
- Correr: `./mvnw test`

## Módulos
- Rutina template (días y ejercicios)
- Registro semanal de sesiones
- Historial por año y mes
