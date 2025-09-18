FROM eclipse-temurin:21-jre-alpine

# Instalar wget para health checks
RUN apk add --no-cache wget

# Crear usuario no-root
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Crear directorio de trabajo
WORKDIR /app

# Copiar el JAR
COPY applications/app-service/build/libs/*.jar app.jar

# Cambiar propietario
RUN chown -R appuser:appgroup /app

# Cambiar a usuario no-root
USER appuser

# Variables de entorno
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

# Exponer puerto
EXPOSE 8082

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8082/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
