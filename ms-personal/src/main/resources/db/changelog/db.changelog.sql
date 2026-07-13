--liquibase formatted sql

--changeset autor:1
CREATE TABLE IF NOT EXISTS personal  (
    idPersonal BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    cargo VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    activo TINYINT(1) DEFAULT 1
);
--rollback DROP TABLE personal;

--changeset autor:2
INSERT INTO personal (nombre, apellido, cargo, correo, activo)
VALUES ('Javier', 'Soto', 'Veterinario', 'javier.soto@vetsys.cl', 1);
INSERT INTO personal (nombre, apellido, cargo, correo, activo)
VALUES ('Fernanda', 'Diaz', 'Recepcionista', 'fernanda.diaz@vetsys.cl', 1);
INSERT INTO personal (nombre, apellido, cargo, correo, activo)
VALUES ('Ignacio', 'Perez', 'Veterinario Jefe', 'ignacio.perez@vetsys.cl', 1);
--rollback DELETE FROM personal WHERE idPersonal IN (1,2,3);