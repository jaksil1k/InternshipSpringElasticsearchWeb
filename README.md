# InternshipSpringElasticsearchCli
ЗАДАНИЕ #3. Spring Core:
- CLI-приложение: принимает произвольный текст как аргумент командной строки (-s "some text") и команду, которую необходимо выполнить (-e add или -e search). 
Команда add должна сохранять переданный текст в БД Elasticsearch как документ с помощью HTTP-запроса. 
Команда search должна выполнять поиск в БД Elasticsearch и выводить найденные документы в консоль. 
Elasticsearch необходимо запустить в docker-контейнере с помощью Docker Compose (в git должен быть добавлен файл docker-compose.yml).

При реализации приложения необходимо использовать Spring Boot, библиотеки Apache Commons CLI & Spring Data Elasticsearch. 
Для сборки проекта необходимо использовать Maven. Сервис для выполнения запросов должен быть реализован в отдельном классе с аннтоацией @Service. 
Будет лучше начать с изучения Spring Core, изучить понятия Dependency Injection, IoC container, Application Context, Bean. 
Далее можно перейти к изучения Spring Boot, и также изучить для чего необходимы Service, Repository, application.properties.
