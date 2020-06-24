package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealServiceTest extends AbstractServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_BREAKFAST_ID, UserTestData.USER_ID);
        assertMatch(meal, MEAL_BREAKFAST);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_BREAKFAST_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void getNotFoundBecauseNotExists() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_ID, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_BREAKFAST_ID, USER_ID);
        assertNull(repository.get(MEAL_BREAKFAST_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_BREAKFAST_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> mealsBetweenInclusive = service.getBetweenInclusive(
                LocalDate.of(2020, 1, 10),
                LocalDate.of(2020, 1, 12),
                USER_ID);
        assertMatch(mealsBetweenInclusive, MEAL_DINNER, MEAL_LUNCH, MEAL_BREAKFAST);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL_NIGHT_DINNER, MEAL_DINNER, MEAL_LUNCH, MEAL_BREAKFAST);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_BREAKFAST_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal newMeal = service.create(getNew(), USER_ID);
        Meal expectedMeal = getNew();
        expectedMeal.setId(NEW_MEAL_ID);
        assertMatch(newMeal, expectedMeal);
        assertMatch(service.get(newMeal.getId(), USER_ID), expectedMeal);
    }
}