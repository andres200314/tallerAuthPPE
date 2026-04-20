# FairTicket - Taller AUTH

## Datos del estudiante

**Nombre:** Andrés Arroyave Cardona

## Variables individuales

| Variable | Valor |
|----------|-------|
| Puerto de Hash | CredentialEncoder |
| Base Path | /api/security |
| Claim JWT | authority |
| Propiedad YAML | app.security.secret / app.security.expiration |

## Comandos cURL

### 1. Registro
```bash
curl -X POST http://localhost:6001/api/security/register \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"usuario1\",\"email\":\"usuario1@test.com\",\"password\":\"123456\",\"role\":\"ORGANIZER\"}"
```

### 2. Login
```bash
curl -X POST http://localhost:6001/api/security/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"usuario1@test.com\",\"password\":\"123456\"}"
```

### 3. Acceso con token (crear evento)
```bash
curl -X POST http://localhost:6001/api/events \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Mi evento\",\"description\":\"Evento de prueba\",\"venue\":\"Medellin\",\"eventDate\":\"2026-05-01T18:00:00\",\"saleStartDate\":\"2026-04-20T10:00:00\",\"saleEndDate\":\"2026-04-30T23:59:00\",\"category\":\"MUSICAL\",\"maxTicketsPerUser\":5,\"totalTickets\":100,\"organizerId\":\"<organizerId>\"}"
```

### 4. Acceso denegado (403) - BUYER intentando crear evento
```bash
curl -X POST http://localhost:6001/api/events \
  -H "Authorization: Bearer <token_de_buyer>" \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Mi evento\"}"
```