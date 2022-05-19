VERSION=$$(mvn help:evaluate  -Dexpression=project.version -q -DforceStdout)

run: build
	docker-compose up
	#docker run -e GRPC_SERVER_PORT=7778 -p 7778:7778 trade-accounting:latest

build: build-parent
	docker-compose build --no-cache --build-arg version=$(VERSION)
	#docker build --build-arg version="0.0.1-SNAPSHOT" -t trade-accounting:latest . -f Dockerfile

build-parent:
	make -C ../ build

run-test-client:
	mvn exec:java -Dexec.mainClass=io.devmint.finance.trade.accounting.GRPCClient
