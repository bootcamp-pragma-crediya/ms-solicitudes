-- Agregar columna customer_document para separar del user_id
ALTER TABLE loan_requests 
ADD COLUMN IF NOT EXISTS customer_document VARCHAR(50);

-- Migrar datos existentes: copiar user_id a customer_document
UPDATE loan_requests 
SET customer_document = user_id 
WHERE customer_document IS NULL;

-- Crear índice para mejorar performance en consultas por documento
CREATE INDEX IF NOT EXISTS idx_loan_requests_customer_document 
ON loan_requests(customer_document);