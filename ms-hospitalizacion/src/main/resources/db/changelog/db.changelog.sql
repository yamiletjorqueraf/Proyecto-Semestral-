--liquibase formatted sql

--changeset autor:1


CREATE TABLE IF NOT EXISTS hospitalizacion (
    idHospitalizacion BIGINT       AUTO_INCREMENT PRIMARY KEY,
    idMascota         BIGINT       NOT NULL,
    idDueno           BIGINT       NOT NULL,
    fecha_inicio      DATE         NOT NULL,
    fecha_alta        DATE         NOT NULL,
    diagnostico       VARCHAR(255) NOT NULL
);
