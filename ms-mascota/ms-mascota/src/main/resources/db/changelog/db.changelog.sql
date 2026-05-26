CREATE TABLE IF NOT EXISTS mascota(
    idMascota  BIGINT       AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    especie VARCHAR(20) NOT NULL,
    raza    VARCHAR(20)  NOT NULL ,
    edad    INT NOT NULL,
    idDueno BIGINT NOT NULL
  
);