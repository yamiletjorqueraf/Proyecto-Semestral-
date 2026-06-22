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