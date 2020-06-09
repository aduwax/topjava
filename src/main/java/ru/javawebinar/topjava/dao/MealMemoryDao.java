package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MealMemoryDao implements MealDao {
    private final Map<Long, Meal> meals = new HashMap<>();
    private static final AtomicLong id = new AtomicLong(0);

    public MealMemoryDao(Meal ... meals){
        for (Meal meal: meals) {
            save(meal);
        }
    }

    @Override
    public Meal get(long id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void save(Meal meal) {
        meal.setId(id.getAndIncrement());
        meals.put(meal.getId(), meal);

    }

    @Override
    public void update(long id, Meal meal) {
        meals.put(id, meal);
    }

    @Override
    public void delete(long id) {
        meals.remove(id);
    }
}
