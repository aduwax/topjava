package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    Meal get(long id);

    List<Meal> getAll();

    void save(Meal meal);

    void update(long id, Meal meal);

    void delete(long id);
}
