-- Agregar nuevos campos requeridos para HU4
ALTER TABLE loan_requests 
ADD COLUMN IF NOT EXISTS email VARCHAR(255),
ADD COLUMN IF NOT EXISTS customer_name VARCHAR(255),
ADD COLUMN IF NOT EXISTS interest_rate DECIMAL(5,2),
ADD COLUMN IF NOT EXISTS base_salary DECIMAL(15,2),
ADD COLUMN IF NOT EXISTS monthly_payment DECIMAL(15,2);

-- Actualizar enum de estados para incluir MANUAL_REVIEW
ALTER TYPE loan_status ADD VALUE IF NOT EXISTS 'MANUAL_REVIEW';

-- Crear índice para mejorar performance en consultas por estado
CREATE INDEX IF NOT EXISTS idx_loan_requests_status_created_at 
ON loan_requests(status, created_at DESC);