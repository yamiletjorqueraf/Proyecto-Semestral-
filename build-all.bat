rem Eliminar contenedores e imagenes existentes
FOR /f %%i IN ('docker ps -aq') DO docker rm -f %%i
FOR /f %%i IN ('docker images -aq') DO docker rmi -f %%i

rem Compilar todos los microservicios
cd api-gateway
call .\mvnw clean package -DskipTests

cd ../ms-usuario
call .\mvnw clean package -DskipTests

cd ../ms-personal
call .\mvnw clean package -DskipTests

cd ../ms-dueno
call .\mvnw clean package -DskipTests

cd ../ms-mascota
call .\mvnw clean package -DskipTests

cd ../ms-cita
call .\mvnw clean package -DskipTests

cd ../ms-ventas
call .\mvnw clean package -DskipTests

cd ../ms-pago
call .\mvnw clean package -DskipTests

rem Volver a la raiz y levantar Docker
cd ..
docker-compose up --build