# React + Java migration

The original HTML prototype under `outputs/` remains unchanged as a functional and visual reference.

## New architecture

- `frontend-react/`: React 18-style component application built with Vite.
- `backend-java/`: Java 21 Spring Boot REST API.
- Authentication: BCrypt passwords + signed JWT bearer tokens.
- Authorization foundation: server-side Spring Security roles; UI hiding is not treated as security.
- Persistence: file-backed H2 for local demo. Replace with PostgreSQL before production.

## Start locally

On this workspace, dependencies are installed and both services can be started with:

```powershell
.\start-react-java.ps1
```

Backend terminal:

```powershell
cd backend-java
mvn spring-boot:run
```

Frontend terminal:

```powershell
cd frontend-react
npm install
npm run dev
```

Open `http://localhost:5173`. Demo login is `9999999999` / `admin123`.

## Migration sequence

1. Authentication, shared responsive shell and Super Admin dashboard — foundation included.
2. Users, role permissions and hierarchy mapping.
3. Enquiries, documents, assignment and operations workflow.
4. Complaints/queries, chat/mentions, notifications and audit logs.
5. Company empanelment, payments, wallet and commission ledger.
6. PostgreSQL migrations, automated tests, security review and deployment setup.

Do not deploy with the demo credentials, default JWT secret, H2 database or permissive development CORS configuration.
