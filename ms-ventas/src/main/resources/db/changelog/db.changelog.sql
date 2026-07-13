--liquibase formatted sql
 
--changeset dev:1
CREATE TABLE IF NOT EXISTS ventas (
    idVenta       BIGINT AUTO_INCREMENT PRIMARY KEY,
    idDueno       BIGINT          NOT NULL,
    idMascota     BIGINT          NOT NULL,
    tipoServicio  VARCHAR(50)     NOT NULL,
    descripcion   VARCHAR(255)    NOT NULL,
    monto         INT             NOT NULL,
    estado        VARCHAR(20)     NOT NULL,
    fecha         DATETIME        NOT NULL
);
--rollback DROP TABLE ventas;

--changeset dev:2
INSERT INTO ventas (idDueno, idMascota, tipoServicio, descripcion, monto, estado, fecha)
VALUES (1, 1, 'Consulta', 'Control anual y vacunas', 25000, 'PAGADA', '2026-07-15 09:30:00');
INSERT INTO ventas (idDueno, idMascota, tipoServicio, descripcion, monto, estado, fecha)
VALUES (2, 2, 'Hospitalizacion', 'Tratamiento por deshidratacion', 120000, 'PENDIENTE', '2026-06-05 10:00:00');
INSERT INTO ventas (idDueno, idMascota, tipoServicio, descripcion, monto, estado, fecha)
VALUES (3, 3, 'Cirugia', 'Cirugia por fractura de pata', 350000, 'PAGADA', '2026-06-10 08:15:00');
--rollback DELETE FROM ventas WHERE idVenta IN (1,2,3);
