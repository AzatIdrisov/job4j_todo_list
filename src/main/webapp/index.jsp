<%@ page import="ru.job4j.todolist.store.HibStore" %>
<%@ page import="ru.job4j.todolist.model.Category" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="js/main.js"></script>

    <title>TODO List</title>

</head>
<body>

<div class="container-fluid">
    <H1>Добавить новую задачу</H1>
    <form action="<%=request.getContextPath()%>/task.do" method="post">
        <div class="form-group">
            <label for="head">Заголовок</label>
            <input name="head" class="form-control" id="head">
        </div>
        <div class="form-group">
            <label style="vertical-align: top" for="desc">Описание</label>
            <textarea id="desc" name="desc" rows="4" cols="50"></textarea>
        </div>
        <div class="form-group">
            <label class="col-form-label col-sm-3" for="cIds" >
                Категории
            </label>
            <div class="col-sm-5">
                <select class="form-control" name="cIds" id="cIds" multiple>
                </select>
            </div>
        </div>
        <button type="submit" onclick="return validate()" class="btn btn-primary">Добавить</button>
    </form>
    <H1>Список задач</H1>
    <div class="form-check">
        <label class="form-check-label">
            <input class="form-check-input" type="checkbox" value="all" id="show_all" name="show_all" checked="checked"
                   onclick='loadTasksAndCategories()'>Показать всё
        </label>
    </div>
    <div class="container">
        <table id='table' class="table">
            <thead>
            <tr>
                <th>Заголовок</th>
                <th>Описание</th>
                <th>Категория</th>
                <th>Создал</th>
                <th>Статус</th>
            </tr>
            </thead>
            <tbody id="table_body">
            </tbody>
        </table>
    </div>
</div>

</body>
</html>