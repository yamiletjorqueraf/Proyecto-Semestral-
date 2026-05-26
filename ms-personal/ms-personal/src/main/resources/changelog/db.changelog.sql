CREATE TABLE personal (
    idPersonal BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    cargo VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    activo boolean(1) DEFAULT 1
);