package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static DateTimeFormatter dateTimeFormatter;
    private static MealDao meals;
    private static final int CALORIES_PER_DAY = 2000;

    @Override
    public void init() {
        meals = new MealMemoryDao();
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (Objects.equals(action, "delete")) {
            doDelete(request, response);
            response.sendRedirect("meals");
        } else {
            log.info("Get meals list");
            request.setAttribute("localDateTimeFormat", dateTimeFormatter);
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(meals.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("meals", mealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        long id = Long.parseLong(request.getParameter("id"));
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        if (id >= 0) {
            log.info("Update meal {} with id {}", meal.toString(), id);
            meal.setId(id);
            meals.update(meal);
        } else {
            log.info("Add new meal: {}", meal.toString());
            meals.create(meal);
        }

        response.sendRedirect("meals");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        log.info("Delete meal with id {}", id);
        meals.delete(id);
    }
}
