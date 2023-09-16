sudo docker-compose down

sudo docker rmi 0hoon5661/spring-board-app:latest
sudo docker rmi 0hoon5661/spring-board-nginx:latest

sudo docker pull 0hoon5661/spring-board-app:latest
sudo docker pull 0hoon5661/spring-board-nginx:latest

sudo docker-compose up --build

sudo docker rmi -f $(docker images -f "dangling=true" -q) || true