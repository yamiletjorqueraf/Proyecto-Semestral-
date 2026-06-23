--liquibase formatted sql

--changeset autor:1


CREATE TABLE IF NOT EXISTS medicamentos (
    idMedicamento BIGINT       AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(255) NOT NULL,
    descripcion   VARCHAR(255) NOT NULL,
    precio        DOUBLE       NOT NULL,
    stock         INT          NOT NULL
);