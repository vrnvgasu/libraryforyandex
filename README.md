# Тестовое задание 

### Getting started
#### package project
```bash
mvn clean package

```
#### run docker `./docker_libraryforyandex/docker_libraryforyandex.yml` with postgresql and spring application
- postgres starts on port 10905
- spring starts on port 10906
- database will be created automatically with test data
```bash
docker-compose -f ./docker_libraryforyandex/docker_libraryforyandex.yml up
```
 
#### open swagger ui
<a href="http://localhost:10906/swagger-ui/index.html" target="_blank">http://localhost:10906/swagger-ui/index.html</a>

#### That's all!

-----------
#### Also, you can run integration tests
Tests start with testcontainers on docker
```bash
mvn test

```
