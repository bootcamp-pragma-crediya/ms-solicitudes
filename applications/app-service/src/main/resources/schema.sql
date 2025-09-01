-- Esquema de base de datos para el microservicio de solicitudes

CREATE TABLE IF NOT EXISTS loan_types (
    code VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS loan_applications (
    id VARCHAR(36) PRIMARY KEY,
    customer_document VARCHAR(50) NOT NULL,
    amount DECIMAL(15,2) NOT NULL CHECK (amount > 0),
    term_months INTEGER NOT NULL CHECK (term_months > 0),
    loan_type VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (loan_type) REFERENCES loan_types(code)
);

CREATE INDEX IF NOT EXISTS idx_loan_applications_customer ON loan_applications(customer_document);
CREATE INDEX IF NOT EXISTS idx_loan_applications_status ON loan_applications(status);
CREATE INDEX IF NOT EXISTS idx_loan_applications_created_at ON loan_applications(created_at);