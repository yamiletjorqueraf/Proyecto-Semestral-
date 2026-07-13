--liquibase formatted sql

--changeset autor:1
CREATE TABLE IF NOT EXISTS resultados_examen (
    idResultado     BIGINT        AUTO_INCREMENT PRIMARY KEY,
    idMascota       BIGINT        NOT NULL,
    tipoExamen      VARCHAR(255)  NOT NULL,
    fechaExamen     DATE          NOT NULL,
    resultado       VARCHAR(1000) NOT NULL,
    idPersonal      BIGINT        NOT NULL,
    personalCargo   VARCHAR(255)  NOT NULL
);
--rollback DROP TABLE resultados_examen;

--changeset autor:2
INSERT INTO resultados_examen (idMascota, tipoExamen, fechaExamen, resultado, idPersonal, personalCargo)
VALUES (1, 'Hemograma completo', '2026-06-10', 'Valores dentro de rango normal', 1, 'Veterinario');
INSERT INTO resultados_examen (idMascota, tipoExamen, fechaExamen, resultado, idPersonal, personalCargo)
VALUES (2, 'Radiografia torax', '2026-06-12', 'Sin hallazgos patologicos evidentes', 1, 'Veterinario');
INSERT INTO resultados_examen (idMascota, tipoExamen, fechaExamen, resultado, idPersonal, personalCargo)
VALUES (3, 'Perfil bioquimico', '2026-06-15', 'Leve elevacion de enzimas hepaticas', 3, 'Veterinario Jefe');
--rollback DELETE FROM resultados_examen WHERE idResultado IN (1,2,3);