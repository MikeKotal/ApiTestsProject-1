# API-тесты, проект №1
В данном проекте покрыты тестами ручки API для [Яндекс.Самокат](http://qa-scooter.praktikum-services.ru)
## В проекте используется:
* Java 11
* Maven
* JUnit 4
* RestAssured
* Allure
## Запуск тестов
Склонировать репозиторий
```
git clone https://github.com/MikeKotal/ApiTestsProject-1.git
```
Локально запустить тесты, лежат по следующему пути:
```
src/test/java/scooter
```
Для просмотра отчета в Allure, выполнить следующие комманды:
```
mvm clean test
mvn allure:serve
```