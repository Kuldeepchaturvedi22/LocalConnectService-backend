# LocalConnectService Java API

Spring Boot 3 / Java 21 foundation with JWT authentication, BCrypt passwords, stateless protected APIs, roles and persistent H2 demo storage.

## Run

1. Install JDK 21 and Maven 3.9+.
2. For production, set `JWT_SECRET`, database variables and exact `CORS_ALLOWED_ORIGINS`.
3. Run `mvn spring-boot:run`.

API: `http://localhost:8080/api/v1`  
Health: `http://localhost:8080/api/v1/health`

Demo login: `9999999999` / `admin123`. This credential is development-only and must be removed before deployment.
