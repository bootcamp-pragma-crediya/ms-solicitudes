-- Datos iniciales para tipos de préstamo
INSERT INTO loan_types (code, name, active) VALUES 
('PERSONAL', 'Préstamo Personal', true),
('MORTGAGE', 'Préstamo Hipotecario', true),
('AUTO', 'Préstamo Vehicular', true),
('BUSINESS', 'Préstamo Empresarial', true),
('STUDENT', 'Préstamo Estudiantil', false)
ON CONFLICT (code) DO NOTHING;