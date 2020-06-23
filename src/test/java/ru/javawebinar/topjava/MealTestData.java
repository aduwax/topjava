package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_BREAKFAST_ID = 100002;
    public static final int MEAL_LUNCH_ID = 100003;
    public static final int MEAL_DINNER_ID = 100004;
    public static final Meal MEAL_BREAKFAST = new Meal(MEAL_BREAKFAST_ID, LocalDateTime.of(2020, 1, 10, 10, 0), "Завтрак", 400);
    public static final Meal MEAL_LUNCH = new Meal(MEAL_LUNCH_ID, LocalDateTime.of(2020, 1, 10, 13, 0), "Обед", 500);
    public static final Meal MEAL_DINNER = new Meal(MEAL_DINNER_ID, LocalDateTime.of(2020, 1, 12, 19, 0), "Ужин", 600);

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "New meal", 100);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_BREAKFAST);
        updated.setDescription("Обновленный завтрак");
        updated.setCalories(200);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
