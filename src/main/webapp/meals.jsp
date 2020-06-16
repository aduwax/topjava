<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function (event) {
            var searchParams = window.location.search.substr(1).split('&'); // substr(1) to remove the `#`
            console.log(searchParams);
            for (var i = 0; i < searchParams.length; i++) {
                var p = searchParams[i].split('=');
                console.log(p[0]);
                console.log(p[1]);
                console.log(document.getElementById(p[0]))
                document.getElementById(p[0]).value = decodeURIComponent(p[1]);
            }
        });
    </script>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <form>
        <p>
            <input type="date" placeholder="От даты (включая)" id="startDate" name="startDate">
            <input type="date" placeholder="До даты (включая)" id="endDate" name="endDate">
            <input type="time" placeholder="От времени (включая)" id="startTime" name="startTime">
            <input type="time" placeholder="До времени (исключая)" id="endTime" name="endTime">
        </p>
        <p>
            <input type="submit" value="Фильтровать" formaction="" formmethod="get">
            <input type="reset" onClick="window.location.replace('meals')" value="Очистить">
        </p>
    </form>
</section>
</body>

</html>