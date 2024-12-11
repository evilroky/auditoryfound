# Проект Java + MySQL в Docker

## Требования
- Docker
- Git Bash

## Как запустить впервые
1. Скачать Git Bash с официального сайта: https://git-scm.com/downloads
2. Скачать Docker с официального сайта: https://www.docker.com/products/docker-desktop/
3. Установите (Если не было) и Откройте Docker с рабочего стола
4. Создайте любую папку -> откройте -> пкм по пустому месту -> Open GIT Bash Here
5. Введите поочередно команды:
   
```
git clone https://github.com/evilroky/auditoryfound.git
cd auditoryfound
docker-compose up --build
```

### Дождитесь создания контейнеров и загрузки всех зависимостей и переходите на сайт:
http://localhost:8081/api/
##

## Для повторного запуска проекта:
1. Откройте ранее созданную папку
2. В ней найдите папку auditoryfound, откройте
3. пкм по пустому месту -> Open GIT Bash Here
4. Ввести команды:
   
```
docker-compose up
```

### Дождитесь сообщения о вкулючении и переходите на сайт:
http://localhost:8081/api/

##

