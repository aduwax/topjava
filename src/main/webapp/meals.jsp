<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
<jsp:useBean id="localDateTimeFormat" scope="request" type="java.time.format.DateTimeFormatter"/>
<table width="700" border="1">
    <thead>
    <tr>
        <th>Дата / Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan="2">Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr record_id="${meal.id}" class="${meal.excess
                ? 'meal-record_excess'
                : 'meal-record_not_excess'}">
            <td name="dateTime" rawvalue="${meal.dateTime}">${meal.dateTime.format(localDateTimeFormat)}</td>
            <td name="description">${meal.description}</td>
            <td name="calories">${meal.calories}</td>
            <td name="edit"><a href="javascript:void(0);" onclick="fillForm(${meal.id})">Ред.</a></td>
            <td name="delete"><a href="?action=delete&id=${meal.id}">Удалить</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>

<form method="POST" action="" id="meal_form">
    <input type="hidden" id="form_id" name="id" value="-1">
    <div class="field">
        <label for="form_dateTime">Дата / Время: </label>
        <input id="form_dateTime" type="datetime-local" name="dateTime">
    </div>
    <div class="field">
        <label for="form_description">Описание: </label>
        <input id="form_description" name="description">
    </div>
    <div class="field">
        <label for="form_calories">Калории: </label>
        <input type="number" id="form_calories" name="calories">
    </div>
    <div class="field">
        <label></label>
        <input id="form_send" type="submit" value="Добавить">
        <input id="form_reset" type="reset" onclick="clearForm()">
    </div>
</form>

<script type="text/javascript">
    function fillForm(id) {
        const mealRecord = document.querySelector("[record_id=" + CSS.escape(id) + "]");
        document.getElementById('form_send').value = "Изменить";
        document.getElementById('form_id').value = id;
        document.getElementById('form_dateTime').value = mealRecord.querySelector("[name='dateTime']").getAttribute("rawValue");
        document.getElementById('form_description').value = mealRecord.querySelector("[name='description']").innerHTML;
        document.getElementById('form_calories').value = mealRecord.querySelector("[name='calories']").innerHTML;
    }

    function clearForm() {
        document.getElementById('form_id').value = -1;
        document.getElementById('form_send').value = "Добавить";
    }
</script>
</body>
</html>