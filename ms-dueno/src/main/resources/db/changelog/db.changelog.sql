CREATE TABLE IF NOT EXISTS dueno (
    idDueno   BIGINT       AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    apellido  VARCHAR(100) NOT NULL,
    rut       VARCHAR(12)  NOT NULL UNIQUE,
    email     VARCHAR(100) NOT NULL UNIQUE,
    telefono  BIGINT       NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    idMascota BIGINT       NOT NULL,
    idUsuario BIGINT       NOT NULL
);