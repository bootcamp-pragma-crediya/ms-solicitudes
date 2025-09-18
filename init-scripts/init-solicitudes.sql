-- Script de inicialización para base de datos de solicitudes
CREATE TABLE IF NOT EXISTS loan_requests (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    customer_name VARCHAR(255),
    amount DECIMAL(15,2) NOT NULL,
    term INTEGER NOT NULL,
    loan_type VARCHAR(50) NOT NULL,
    interest_rate DECIMAL(5,2),
    base_salary DECIMAL(15,2),
    monthly_payment DECIMAL(15,2),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING_REVIEW',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS loan_types (
    code VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE
);

-- Insertar tipos de préstamo iniciales
INSERT INTO loan_types (code, name, description, active) VALUES
('PERSONAL', 'Préstamo Personal', 'Préstamo para gastos personales', true),
('VEHICULAR', 'Préstamo Vehicular', 'Préstamo para compra de vehículo', true),
('HIPOTECARIO', 'Préstamo Hipotecario', 'Préstamo para compra de vivienda', true),
('EDUCATIVO', 'Préstamo Educativo', 'Préstamo para estudios', true)
ON CONFLICT (code) DO NOTHING;