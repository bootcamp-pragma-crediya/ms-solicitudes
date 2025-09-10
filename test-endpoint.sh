#!/bin/bash

echo "=== Probando endpoint GET /api/v1/solicitud ==="
echo ""

# Generar un JWT simple para testing (sin validación real)
JWT_TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0dXNlciIsInJvbGUiOiJBc2Vzb3IiLCJpYXQiOjE2MzQ2NzM2MDAsImV4cCI6MTk1MDAzMzYwMH0.example"

echo "1. Probando sin autenticación (debería fallar):"
curl -s -w "\nStatus: %{http_code}\n" \
  "http://localhost:8080/api/v1/solicitud?page=0&size=10"

echo ""
echo "2. Probando con JWT válido:"
curl -s -w "\nStatus: %{http_code}\n" \
  -H "Authorization: Bearer $JWT_TOKEN" \
  "http://localhost:8080/api/v1/solicitud?page=0&size=10"

echo ""
echo "3. Probando paginación (página 1, tamaño 5):"
curl -s -w "\nStatus: %{http_code}\n" \
  -H "Authorization: Bearer $JWT_TOKEN" \
  "http://localhost:8080/api/v1/solicitud?page=1&size=5"

echo ""
echo "=== Fin de pruebas ==="