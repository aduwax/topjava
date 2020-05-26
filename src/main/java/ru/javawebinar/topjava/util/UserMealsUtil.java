package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealExcessAttribute;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Пытался уложиться в сложность O(n), не делать вложенные циклы

        // Лист UserMealWithExcess, отфильтрованный по промежутку startTime - endTime (возвращаемое значение)
        List<UserMealWithExcess> filteredMealsWithExcess = new ArrayList<>();

        // Мап, содержащий excess значение для определенной даты
        Map<LocalDate, UserMealExcessAttribute> mealExcessAttributes = new HashMap<>();

        // Мап, содержащий количество каллорий, потребленных в определенную дату
        Map<LocalDate, Integer> mealDayCalories = new HashMap<>();

        // 1. Идем по листу UserMeal
        for (UserMeal meal : meals) {
            LocalDate mealLocalDate = meal.getDateTime().toLocalDate();
            LocalTime mealLocalTime = meal.getDateTime().toLocalTime();

            // 2. Получаем признак excess для даты meal (если нету - возвращает новый объект = false)
            UserMealExcessAttribute excessForDate = mealExcessAttributes.getOrDefault(
                    mealLocalDate,
                    new UserMealExcessAttribute(false)
            );

            // 3. Получаем количество калорий для даты meal (если нету, возвращает 0)
            Integer caloriesForDate = mealDayCalories.getOrDefault(mealLocalDate, 0);

            // 4. Приплюсовываем калории текущей записи к остальным дневным калориям и пишем обратно в мап
            caloriesForDate += meal.getCalories();
            mealDayCalories.put(mealLocalDate, caloriesForDate);

            // 5. Если калорий больше, чем задано в условии (caloriesPerDay) -
            // ставим признак excess для даты meal = true и пишем обратно в мап
            excessForDate.setExcess(caloriesForDate > caloriesPerDay);
            mealExcessAttributes.put(mealLocalDate, excessForDate);

            // 6. Если meal укладывается в фильтр по времени (startTime - endTime), то
            if (TimeUtil.isBetweenHalfOpen(mealLocalTime, startTime, endTime)) {
                // 7. Пишем его в лист filteredMealsWithExceed (отфильтрованный лист, возвращаемое значение)
                filteredMealsWithExcess.add(new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        mealExcessAttributes.get(mealLocalDate)));
                // Для того, чтобы excess обновился во всех записях meal после превышения кол-ва калорий, я вынес
                // это поле из примитива в отдельный объект.
                // Этот объект создается для каждой даты один раз (во время обращения к мапе - getOrDefault -> default)
                // Дальнейшая работа идет с тем же объектом, через сеттер, новый мы не создаем
                // При создании UserMealWithExcess получается так, что в каждый объект одной даты попадает одинаковая ссылка
                // на объект UserMealExcessAttribute, поэтому после превышения количества калорий для даты - он везде станет true
            }
        }
        return filteredMealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
