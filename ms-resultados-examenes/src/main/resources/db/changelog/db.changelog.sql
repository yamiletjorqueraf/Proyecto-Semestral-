CREATE TABLE IF NOT EXISTS resultados_examen (
    idResultado     BIGINT        AUTO_INCREMENT PRIMARY KEY,
    idMascota       BIGINT        NOT NULL,
    tipoExamen      VARCHAR(255)  NOT NULL,
    fechaExamen     DATE          NOT NULL,
    resultado       VARCHAR(1000) NOT NULL,
    idPersonal      BIGINT        NOT NULL,
    personalCargo   VARCHAR(255)  NOT NULL
);