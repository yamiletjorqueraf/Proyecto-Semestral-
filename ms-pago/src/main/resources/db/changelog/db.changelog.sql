--liquibase formatted sql
 
--changeset dev:1
CREATE TABLE IF NOT EXISTS pagos (
    idPago       BIGINT AUTO_INCREMENT PRIMARY KEY,
    idVenta      BIGINT       NOT NULL,
    idDueno      BIGINT       NOT NULL,
    valorNeto    INT          NOT NULL,
    iva          INT          NOT NULL DEFAULT 0,
    descuento    INT          NOT NULL DEFAULT 0,
    total_Pagar  INT          NOT NULL,
    medio_Pago   VARCHAR(50)  NOT NULL,
    estado       VARCHAR(20)  NOT NULL,
    fecha        DATETIME     NOT NULL
);
--rollback DROP TABLE pagos;

--changeset dev:2
INSERT INTO pagos (idVenta, idDueno, valorNeto, iva, descuento, total_Pagar, medio_Pago, estado, fecha)
VALUES (1, 1, 21008, 3992, 0, 25000, 'DEBITO', 'APROBADO', '2026-07-15 09:35:00');
INSERT INTO pagos (idVenta, idDueno, valorNeto, iva, descuento, total_Pagar, medio_Pago, estado, fecha)
VALUES (2, 2, 100840, 19160, 0, 120000, 'TRANSFERENCIA', 'PENDIENTE', '2026-06-05 10:05:00');
INSERT INTO pagos (idVenta, idDueno, valorNeto, iva, descuento, total_Pagar, medio_Pago, estado, fecha)
VALUES (3, 3, 294118, 55882, 20000, 330000, 'CREDITO', 'APROBADO', '2026-06-10 08:20:00');
--rollback DELETE FROM pagos WHERE idPago IN (1,2,3);