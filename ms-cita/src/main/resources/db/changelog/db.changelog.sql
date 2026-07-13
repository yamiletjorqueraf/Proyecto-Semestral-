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
--rollback DROP TABLE cita;

--changeset autor:2
INSERT INTO cita (idMascota, idPersonal, fechaHora, motivo, estado)
VALUES (1, 1, '2026-07-15 09:30:00', 'Control anual y vacunas', 'CONFIRMADA');
INSERT INTO cita (idMascota, idPersonal, fechaHora, motivo, estado)
VALUES (2, 1, '2026-07-16 11:00:00', 'Consulta por decaimiento', 'PENDIENTE');
INSERT INTO cita (idMascota, idPersonal, fechaHora, motivo, estado)
VALUES (3, 3, '2026-07-17 15:45:00', 'Revision post cirugia', 'CONFIRMADA');
--rollback DELETE FROM cita WHERE idCita IN (1,2,3);
