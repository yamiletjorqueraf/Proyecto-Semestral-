--liquibase formatted sql

--changeset autor:1


CREATE TABLE IF NOT EXISTS cita (
    idCita    BIGINT       AUTO_INCREMENT PRIMARY KEY,
    idMascota BIGINT       NOT NULL,
    idPersonal BIGINT      NOT NULL,
    fechaHora DATETIME     NOT NULL,
    motivo    VARCHAR(255) NOT NULL,
    estado    VARCHAR(50)  NOT NULL
);
