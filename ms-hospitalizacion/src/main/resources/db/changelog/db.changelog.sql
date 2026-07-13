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
--rollback DROP TABLE hospitalizacion;

--changeset autor:2
INSERT INTO hospitalizacion (idMascota, idDueno, fecha_inicio, fecha_alta, diagnostico)
VALUES (1, 1, '2026-06-01', '2026-06-04', 'Gastroenteritis aguda');
INSERT INTO hospitalizacion (idMascota, idDueno, fecha_inicio, fecha_alta, diagnostico)
VALUES (2, 2, '2026-06-05', '2026-06-06', 'Deshidratacion moderada');
INSERT INTO hospitalizacion (idMascota, idDueno, fecha_inicio, fecha_alta, diagnostico)
VALUES (3, 3, '2026-06-10', '2026-06-15', 'Post operatorio fractura de pata');
--rollback DELETE FROM hospitalizacion WHERE idHospitalizacion IN (1,2,3);
