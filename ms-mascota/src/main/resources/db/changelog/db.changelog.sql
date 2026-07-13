--liquibase formatted sql

--changeset autor:1
CREATE TABLE IF NOT EXISTS mascota(
    idMascota  BIGINT       AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    especie VARCHAR(20) NOT NULL,
    raza    VARCHAR(20)  NOT NULL ,
    edad    INT NOT NULL,
    idDueno BIGINT NOT NULL
);
--rollback DROP TABLE mascota;

--changeset autor:2
INSERT INTO mascota (nombre, especie, raza, edad, idDueno)
VALUES ('Firulais', 'Perro', 'Labrador', 3, 1);
INSERT INTO mascota (nombre, especie, raza, edad, idDueno)
VALUES ('Michi', 'Gato', 'Siames', 2, 2);
INSERT INTO mascota (nombre, especie, raza, edad, idDueno)
VALUES ('Rocky', 'Perro', 'Bulldog', 5, 3);
--rollback DELETE FROM mascota WHERE idMascota IN (1,2,3);