--liquibase formatted sql

--changeset autor:1
CREATE TABLE IF NOT EXISTS usuario (
    idUsuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono BIGINT NOT NULL
);
--rollback DROP TABLE usuario;

--changeset autor:2
INSERT INTO usuario (nombre, apellido, rol, correo, telefono)
VALUES ('Eduardo', 'Urquieta', 'ADMIN', 'eduardo.urquieta@vetsys.cl', 912345678);
INSERT INTO usuario (nombre, apellido, rol, correo, telefono)
VALUES ('Camila', 'Rojas', 'DUENO', 'camila.rojas@mail.cl', 987654321);
INSERT INTO usuario (nombre, apellido, rol, correo, telefono)
VALUES ('Javier', 'Soto', 'VETERINARIO', 'javier.soto@vetsys.cl', 956781234);
--rollback DELETE FROM usuario WHERE idUsuario IN (1,2,3);
