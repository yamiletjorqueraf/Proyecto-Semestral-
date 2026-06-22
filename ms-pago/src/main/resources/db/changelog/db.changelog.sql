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