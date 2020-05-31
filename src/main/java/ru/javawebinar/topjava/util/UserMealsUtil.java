package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;
import ru.javawebinar.topjava.model.UserMealWithExcessAtomic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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
        System.out.println("filteredByCycles");
        mealsTo.forEach(System.out::println);

        List<UserMealWithExcessAtomic> mealsToOptional2 = filteredByCyclesOptional2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println("\nfilteredByCyclesOptional2");
        mealsToOptional2.forEach(System.out::println);

        System.out.println("\nfilteredByStreams");
        List<UserMealWithExcess> mealsToStreams = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToStreams.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // filter all UserMeal[s] by time
        List<UserMeal> filteredMeals = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalTime mealLocalTime = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(mealLocalTime, startTime, endTime)) {
                filteredMeals.add(meal);
            }
        }

        // calculate calories in dates
        Map<LocalDate, Integer> caloriesInDate = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate mealLocalDate = meal.getDateTime().toLocalDate();
            Integer calories = caloriesInDate.getOrDefault(mealLocalDate, 0) + meal.getCalories();
            caloriesInDate.put(mealLocalDate, calories);
        }

        // create list MealWithExcess (result)
        List<UserMealWithExcess> filteredMealsWithExcess = new ArrayList<>();
        for (UserMeal meal : filteredMeals) {
            filteredMealsWithExcess.add(new UserMealWithExcess(
                    meal.getDateTime(),
                    meal.getDescription(),
                    meal.getCalories(),
                    (caloriesInDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)
            ));
        }

        return filteredMealsWithExcess;
    }


    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // calculate calories in dates
        Map<LocalDate, Integer> caloriesInDate = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories))
                );

        // filter by time and return UserMealWithExcess list
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesInDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcessAtomic> filteredByCyclesOptional2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcessAtomic> filteredMealsWithExcess = new ArrayList<>();
        Map<LocalDate, AtomicBoolean> mealExcessAttributes = new HashMap<>();
        Map<LocalDate, Integer> mealDayCalories = new HashMap<>();

        for (UserMeal meal : meals) {
            LocalDate mealLocalDate = meal.getDateTime().toLocalDate();
            LocalTime mealLocalTime = meal.getDateTime().toLocalTime();

            // Get or create (if not exists) excess value
            AtomicBoolean excessForDate = mealExcessAttributes.getOrDefault(
                    mealLocalDate,
                    new AtomicBoolean(false)
            );

            // Get calories for date, add record calories and put to map
            Integer caloriesForDate = mealDayCalories.getOrDefault(mealLocalDate, 0);
            caloriesForDate += meal.getCalories();
            mealDayCalories.put(mealLocalDate, caloriesForDate);

            excessForDate.set(caloriesForDate > caloriesPerDay);
            mealExcessAttributes.put(mealLocalDate, excessForDate);

            if (TimeUtil.isBetweenHalfOpen(mealLocalTime, startTime, endTime)) {
                filteredMealsWithExcess.add(new UserMealWithExcessAtomic(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        mealExcessAttributes.get(mealLocalDate)));
            }
        }
        return filteredMealsWithExcess;
    }
}
