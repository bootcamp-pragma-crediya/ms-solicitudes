# 🐳 Contenerización de Microservicios Crediya

Este proyecto implementa la contenerización completa del sistema de microservicios Crediya, incluyendo:

- **ms-autenticacion**: Microservicio de autenticación y gestión de usuarios
- **ms-solicitudes**: Microservicio de gestión de solicitudes de préstamos
- **Bases de datos separadas**: PostgreSQL independiente para cada microservicio

## 🏗️ Arquitectura

```
┌─────────────────┐    ┌─────────────────┐
│  ms-solicitudes │    │ ms-autenticacion│
│     :8082       │◄──►│     :8080       │
└─────────┬───────┘    └─────────┬───────┘
          │                      │
          ▼                      ▼
┌─────────────────┐    ┌─────────────────┐
│ db-solicitudes  │    │ db-autenticacion│
│     :5434       │    │     :5433       │
└─────────────────┘    └─────────────────┘
```

## 🚀 Despliegue Rápido

### Prerrequisitos
- Docker Desktop instalado y corriendo
- Java 21 (para construcción local)
- Gradle (incluido en wrapper)

### Despliegue con un comando
```bash
./deploy.sh
```

Este script:
1. ✅ Construye ambos microservicios
2. ✅ Crea las imágenes Docker
3. ✅ Levanta las bases de datos
4. ✅ Despliega los microservicios
5. ✅ Verifica que todo esté funcionando

## 🛠️ Comandos Manuales

### Construcción
```bash
# Construir ms-autenticacion
cd ../Downloads/ms-autenticacion
./gradlew clean build -x test

# Construir ms-solicitudes
cd ../ms-solicitudes
./gradlew clean build -x test
```

### Docker Compose
```bash
# Levantar todos los servicios
docker-compose up --build -d

# Ver logs
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f ms-solicitudes

# Detener servicios
docker-compose down

# Detener y eliminar volúmenes
docker-compose down -v
```

## 🌐 Endpoints Disponibles

### Microservicio de Autenticación (Puerto 8080)
- **Health Check**: http://localhost:8080/actuator/health
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Base**: http://localhost:8080/api/v1/auth

### Microservicio de Solicitudes (Puerto 8082)
- **Health Check**: http://localhost:8082/actuator/health
- **Swagger UI**: http://localhost:8082/swagger-ui.html
- **API Base**: http://localhost:8082/api/v1/loan-requests

## 🗄️ Bases de Datos

### Base de Datos de Autenticación
- **Host**: localhost
- **Puerto**: 5433
- **Base de datos**: crediya_autenticacion
- **Usuario**: crediya_user
- **Contraseña**: crediya_pass

### Base de Datos de Solicitudes
- **Host**: localhost
- **Puerto**: 5434
- **Base de datos**: crediya_solicitudes
- **Usuario**: crediya_user
- **Contraseña**: crediya_pass

## 🔧 Configuración

### Variables de Entorno

Los microservicios utilizan las siguientes variables de entorno:

#### ms-autenticacion
```env
SERVER_PORT=8080
DB_HOST=db-autenticacion
DB_PORT=5432
DB_NAME=crediya_autenticacion
DB_USERNAME=crediya_user
DB_PASSWORD=crediya_pass
```

#### ms-solicitudes
```env
SERVER_PORT=8082
DB_HOST=db-solicitudes
DB_PORT=5432
DB_NAME=crediya_solicitudes
DB_USERNAME=crediya_user
DB_PASSWORD=crediya_pass
AUTH_SERVICE_URL=http://ms-autenticacion:8080
```

## 🧪 Pruebas

### Verificar Comunicación entre Microservicios
```bash
# Desde el contenedor de solicitudes, verificar conectividad con autenticación
docker-compose exec ms-solicitudes wget -qO- http://ms-autenticacion:8080/actuator/health
```

### Usuarios de Prueba
El sistema incluye usuarios predefinidos:

- **Admin**: admin@crediya.com / admin123
- **Asesor**: asesor@crediya.com / asesor123

## 🔍 Troubleshooting

### Ver logs detallados
```bash
# Logs de todos los servicios
docker-compose logs -f

# Logs de un servicio específico
docker-compose logs -f ms-solicitudes
docker-compose logs -f db-solicitudes
```

### Reiniciar un servicio específico
```bash
docker-compose restart ms-solicitudes
```

### Acceder a la base de datos
```bash
# Conectar a base de datos de autenticación
docker-compose exec db-autenticacion psql -U crediya_user -d crediya_autenticacion

# Conectar a base de datos de solicitudes
docker-compose exec db-solicitudes psql -U crediya_user -d crediya_solicitudes
```

### Limpiar todo y empezar de nuevo
```bash
docker-compose down -v --remove-orphans
docker system prune -f
./deploy.sh
```

## 📊 Monitoreo

### Health Checks
Todos los servicios incluyen health checks automáticos:
- Bases de datos: Verificación de conectividad PostgreSQL
- Microservicios: Endpoint `/actuator/health`

### Métricas
Los microservicios exponen métricas de Prometheus en:
- http://localhost:8080/actuator/prometheus
- http://localhost:8082/actuator/prometheus

## 🔒 Seguridad

- ✅ Contenedores ejecutan con usuario no-root
- ✅ Bases de datos con credenciales específicas
- ✅ Red Docker aislada
- ✅ Health checks implementados
- ✅ Manejo de excepciones centralizado

## 📝 Notas Importantes

1. **Persistencia**: Los datos se mantienen en volúmenes Docker nombrados
2. **Red**: Todos los servicios están en la red `crediya-network`
3. **Comunicación**: Los microservicios se comunican usando nombres de servicio Docker
4. **Escalabilidad**: Configuración lista para escalamiento horizontal