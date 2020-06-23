package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

public class InMemoryMealTestRepository extends MealTestData {
    public static void init(InMemoryMealRepository repository) {
        InMemoryBaseRepository<Meal> mealInMemoryBaseRepository = new InMemoryBaseRepository<>();
        mealInMemoryBaseRepository.map.put(MEAL_BREAKFAST_ID, MEAL_BREAKFAST);
        mealInMemoryBaseRepository.map.put(MEAL_LUNCH_ID, MEAL_LUNCH);
        mealInMemoryBaseRepository.map.put(MEAL_DINNER_ID, MEAL_DINNER);
        repository.usersMealsMap.clear();
        repository.usersMealsMap.put(UserTestData.USER_ID, mealInMemoryBaseRepository);
    }
}
