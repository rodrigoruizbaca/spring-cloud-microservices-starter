mvn -f Configuration/ clean install
mvn -f Discovery/ clean install
mvn -f Gateway/ clean install
mvn -f AuthService/ clean install
docker build -t spring-starter-config "Configuration/docker"
docker build -t spring-starter-discovery "Discovery/docker"
docker build -t spring-starter-gateway "Gateway/docker"
docker build -t spring-starter-auth "AuthService/docker"