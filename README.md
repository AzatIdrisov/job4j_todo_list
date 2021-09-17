# Проект "TODO-list"

[![Build Status](https://www.travis-ci.com/AzatIdrisov/job4j_todo_list.svg?branch=main)](https://www.travis-ci.com/AzatIdrisov/job4j_todo_list)

## Описание

MVC REST API - приложение, todo-список задач.\
Для хранения данных применяется Hibernate.\
Данные на главную страницу подгружаются через AJAX-запросы, которые возвращают данные в формате JSON.\

## Функционал
* Регистрация пользователя
* Аутентификация на сервлет-фильтрах
* Добавление задачи в TODO-list
* Изменение статуса задачи на выполненную
* Вывод всех задач
* Вывод только незавершённых задач

## Технологии
* Hibernate
* JDBC
* AJAX jquery
* Servlet & JSP
* HTML, CSS, BOOTSTRAP
* Apache Tomcat Server
* Junit, Mockito, Powermock
* Travis CI

## Интерфейс

![ScreenShot](images/task1.PNG)

Добавление новой задачи: при добавлении новой задачи необходимо указать заголовок, 
описание и выбрать одну или несколько категорий

![ScreenShot](images/task2.PNG)

После нажатия кнопки добавить созданная задача добавляется в список,
в котором также указывается, кто создал задачу

![ScreenShot](images/task3.PNG)

Был добавлен переключатель, позволяющий отобразить только незавершенные задачи

![ScreenShot](images/task4.PNG)

## Контакты

Идрисов Азат

Java-разработчик

a.idrisov23@yandex.ru