package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private final Comparator<Meal> mealDateComparator = Comparator.comparing(Meal::getDateTime).reversed();

    {
        MealsUtil.MEALS_U1.forEach(meal -> save(meal, 1));
        MealsUtil.MEALS_U2.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(userId, putToUserMealsMap(userId, meal));
            return meal;
        }

        // handle case: update, but not present in storage
        return get(meal.getId(), userId) != null ?
                getUserMealsMap(userId).compute(meal.getId(), (id, mealRecord) -> {
                    meal.setUserId(userId);
                    return meal;
                }) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return get(id, userId) != null && getUserMealsMap(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return getUserMealsMap(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getUserMealsMap(userId).values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(mealDateComparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getByDateTime(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return getAll(userId).stream()
                .filter(meal -> isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .filter(meal -> isBetweenHalfOpen(meal.getDateTime().toLocalDate(), startDate, endDate))
                .collect(Collectors.toList());
    }

    public Map<Integer, Meal> getUserMealsMap(int userId) {
        return repository.getOrDefault(userId, new ConcurrentHashMap<>());
    }

    private Map<Integer, Meal> putToUserMealsMap(int userId, Meal meal) {
        Map<Integer, Meal> userMealsMap = getUserMealsMap(userId);
        userMealsMap.put(meal.getId(), meal);
        return userMealsMap;
    }

}

