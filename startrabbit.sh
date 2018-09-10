#docker run -d --hostname my-rabbit -p 5672:5672 rabbitmq:3
docker run -d --hostname my-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management
