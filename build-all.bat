rem $env:DOCKER_BUILDKIT=0
rem docker rm -f $(docker ps -aq)
FOR /f %%i IN ('docker ps -aq') DO docker rm -f %%i
FOR /f %%i IN ('docker images -aq') DO docker rmi -f %%i
cd api-gateway
call .\mvnw clean package -DskipTests

cd ../ms-cita
call .\mvnw clean package -DskipTests

cd ../ms-dueno
call .\mvnw clean package -DskipTests

cd ../ms-farmacia
call  .\mvnw clean package -DskipTests

cd ../ms-hospitalizacion
call .\mvnw clean package -DskipTests

cd ../ms-mascota
call .\mvnw clean package -DskipTests

cd ../ms-pago
call .\mvnw clean package -DskipTests

cd ../ms-personal
call .\mvnw clean package -DskipTests

cd ../ms-resultados-examenes
call .\mvnw clean package -DskipTests

cd ../ms-usuario
call .\mvnw clean package -DskipTests

cd ../ms-ventas
call .\mvnw clean package -DskipTests