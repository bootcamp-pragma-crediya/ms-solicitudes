# Testing del Endpoint HU4: Listado de Solicitudes para Revisión Manual

## Endpoint Implementado

**GET /api/v1/solicitud**

### Funcionalidades:
- ✅ Lista solicitudes paginadas que necesitan revisión manual
- ✅ Filtra por estados: `PENDING_REVIEW`, `REJECTED`, `MANUAL_REVIEW`
- ✅ Validación JWT con autorización por rol "Asesor"
- ✅ Paginación con parámetros `page` y `size`
- ✅ Logs de traza para monitoreo
- ✅ Manejo centralizado de excepciones

### Parámetros de consulta:
- `page` (opcional): Número de página (default: 0)
- `size` (opcional): Tamaño de página (default: 10)

### Headers requeridos:
- `Authorization: Bearer <JWT_TOKEN>`

### Respuesta esperada:
```json
{
  "content": [
    {
      "id": "1",
      "monto": 50000,
      "plazo": 24,
      "email": "juan@example.com",
      "nombre": "Juan Pérez",
      "tipo_prestamo": "PERSONAL",
      "tasa_interes": 12.5,
      "estado_solicitud": "PENDING_REVIEW",
      "salario_base": 3000000,
      "monto_mensual_solicitud": 2500
    }
  ],
  "page": 0,
  "size": 10,
  "total_elements": 2,
  "total_pages": 1
}
```

## Cómo probar:

### 1. Ejecutar la aplicación:
```bash
./gradlew bootRun --args='--app.mock-mode=true --server.port=8080'
```

### 2. Usar el script de prueba:
```bash
./test-endpoint.sh
```

### 3. Pruebas manuales con curl:

**Sin autenticación (401 Unauthorized):**
```bash
curl "http://localhost:8080/api/v1/solicitud?page=0&size=10"
```

**Con JWT válido:**
```bash
curl -H "Authorization: Bearer <JWT_TOKEN>" \
     "http://localhost:8080/api/v1/solicitud?page=0&size=10"
```

**Con paginación:**
```bash
curl -H "Authorization: Bearer <JWT_TOKEN>" \
     "http://localhost:8080/api/v1/solicitud?page=1&size=5"
```

## Validación JWT

El filtro JWT valida:
- ✅ Presencia del header `Authorization: Bearer <token>`
- ✅ Validez del token JWT
- ✅ Rol "Asesor" para acceso al endpoint GET
- ✅ Respuestas 401/403 apropiadas

## Tests Ejecutados

- ✅ Tests unitarios del caso de uso (100% cobertura)
- ✅ Tests de mutación (100% killed)
- ✅ Tests de integración del handler

## Arquitectura Implementada

- **Dominio**: `ListLoanApplicationsUseCase`, `LoanApplication` actualizado
- **Infraestructura**: Handler, Router, DTOs, JWT Filter
- **Persistencia**: Repository con paginación, Mock adapter
- **Configuración**: Beans de casos de uso

La implementación sigue Clean Architecture y cumple todos los criterios de aceptación de la HU4.