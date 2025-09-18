-- Script de inicialización para base de datos de autenticación
-- Habilitar extensión para UUID y crypt
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Crear tabla users (sin contraseñas)
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    date_of_birth DATE,
    address TEXT,
    phone VARCHAR(20),
    email VARCHAR(255) UNIQUE NOT NULL,
    user_document VARCHAR(50) NOT NULL,
    base_salary DECIMAL(15,2),
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de credenciales separada
CREATE TABLE IF NOT EXISTS user_credentials (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id)
);

-- Insertar usuarios iniciales para bootstrap del sistema
INSERT INTO users (id, name, last_name, email, user_document, base_salary, role, created_at, updated_at) VALUES 
('550e8400-e29b-41d4-a716-446655440001', 'Super', 'Admin', 'admin@crediya.com', '12345678', 10000000.00, 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('550e8400-e29b-41d4-a716-446655440002', 'Asesor', 'Principal', 'asesor@crediya.com', '87654321', 5000000.00, 'ASESOR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

-- Insertar credenciales iniciales (password: admin123 y asesor123)
INSERT INTO user_credentials (user_id, password_hash, created_at, updated_at) VALUES
('550e8400-e29b-41d4-a716-446655440001', crypt('admin123', gen_salt('bf')), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('550e8400-e29b-41d4-a716-446655440002', crypt('asesor123', gen_salt('bf')), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (user_id) DO NOTHING;