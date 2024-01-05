# QuestBot

## Описание: 
Телеграм бот квестов на микросервисной архитектуре. Находится на промежуточном этапе разработки. На мой взгляд очень интересный пет-проект, который в совокупности является проектом дипломной работы в вузе. Постоянно додорабатывается по мере преобретения каких-либо новых знаний или возникновения идей относительно его развития. Идея в том, что любой желающий мождет зарегистрироваться в и создавать \ загружать \ использовать готовые квесты для развлечения одному или в компании. Представляет интерес, поскольку сочетает в себе множество интересных технологий и большой потенциал для дальнейшего развития.


## Проект включает следующие сервисы: 
- bot
- data
- entity
- orchestrator
- quest


## Взаимодействие: 
Пока что сервисы общаются между собой посредством простых REST Json запросов через контроллеры. В будущем планировалось сделать отдельную реализацию интерфейса обмена сообщениями для Socket и менеджера сообщений типа RabbitMQ 


## Использованные библиотеки

В проекте используются следующие зависимости:
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- mapstruct
- jUnit
