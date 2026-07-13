--liquibase formatted sql

--changeset autor:1
CREATE TABLE IF NOT EXISTS dueno (
    idDueno   BIGINT       AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    apellido  VARCHAR(100) NOT NULL,
    rut       VARCHAR(12)  NOT NULL UNIQUE,
    email     VARCHAR(100) NOT NULL UNIQUE,
    telefono  BIGINT       NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    idMascota BIGINT       NOT NULL,
    idUsuario BIGINT       NOT NULL
);
--rollback DROP TABLE dueno;

--changeset autor:2
INSERT INTO dueno (nombre, apellido, rut, email, telefono, direccion, idMascota, idUsuario)
VALUES ('Camila', 'Rojas', '12345678-9', 'camila.rojas@mail.cl', 987654321, 'Av. Principal 123, Santiago', 1, 2);
INSERT INTO dueno (nombre, apellido, rut, email, telefono, direccion, idMascota, idUsuario)
VALUES ('Pedro', 'Fernandez', '98765432-1', 'pedro.fernandez@mail.cl', 911223344, 'Los Alamos 456, Maipu', 2, 2);
INSERT INTO dueno (nombre, apellido, rut, email, telefono, direccion, idMascota, idUsuario)
VALUES ('Maria', 'Gonzalez', '11222333-4', 'maria.gonzalez@mail.cl', 933445566, 'Camino Real 789, Puente Alto', 3, 2);
--rollback DELETE FROM dueno WHERE idDueno IN (1,2,3);
