run: build
	docker-compose up
	#docker run -e GRPC_SERVER_PORT=7778 -p 7778:7778 trade-accounting:latest
build:
	docker-compose build --no-cache
	#docker build --build-arg version="0.0.1-SNAPSHOT" -t trade-accounting:latest . -f Dockerfile