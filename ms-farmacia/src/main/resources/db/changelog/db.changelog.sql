--liquibase formatted sql

--changeset autor:1


CREATE TABLE IF NOT EXISTS medicamentos (
    idMedicamento BIGINT       AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(255) NOT NULL,
    descripcion   VARCHAR(255) NOT NULL,
    precio        DOUBLE       NOT NULL,
    stock         INT          NOT NULL
);
--rollback DROP TABLE medicamentos;

--changeset autor:2
INSERT INTO medicamentos (nombre, descripcion, precio, stock)
VALUES ('Amoxicilina 250mg', 'Antibiotico de amplio espectro', 5990, 120);
INSERT INTO medicamentos (nombre, descripcion, precio, stock)
VALUES ('Meloxicam 1.5mg/ml', 'Antiinflamatorio para perros y gatos', 8990, 60);
INSERT INTO medicamentos (nombre, descripcion, precio, stock)
VALUES ('Shampoo antipulgas', 'Uso topico, elimina pulgas y garrapatas', 6490, 40);
--rollback DELETE FROM medicamentos WHERE idMedicamento IN (1,2,3);