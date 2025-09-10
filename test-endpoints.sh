#!/bin/bash

echo "=== Testing Loan Application Endpoints ==="

# Test 1: POST - Create loan application
echo "1. Testing POST /api/v1/solicitud"
curl -X POST http://localhost:8082/api/v1/solicitud \
  -H "Content-Type: application/json" \
  -d '{
    "user_id": "1234567",
    "amount": 1000000.00,
    "term_months": 12,
    "loan_type": "BUSINESS"
  }' | jq .

echo -e "\n"

# Test 2: GET - List loan applications (requires JWT)
echo "2. Testing GET /api/v1/solicitud (without JWT - should fail)"
curl -X GET http://localhost:8082/api/v1/solicitud | jq .

echo -e "\n"

# Test 3: Health check
echo "3. Testing Health endpoint"
curl -s http://localhost:8082/actuator/health | jq .

echo -e "\n"

# Test 4: Swagger UI
echo "4. Swagger UI available at: http://localhost:8082/swagger-ui.html"