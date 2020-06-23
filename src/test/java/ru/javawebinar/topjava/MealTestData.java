package ru.javawebinar.topjava;

import org.assertj.core.api.ObjectAssert;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int NEW_MEAL_ID = 100009;
    // User meal
    public static final int MEAL_BREAKFAST_ID = 100002;
    public static final int MEAL_LUNCH_ID = 100003;
    public static final int MEAL_DINNER_ID = 100004;
    public static final int MEAL_NIGHT_DINNER_ID = 100005;
    public static final Meal MEAL_BREAKFAST = new Meal(MEAL_BREAKFAST_ID, LocalDateTime.of(2020, 1, 10, 10, 0), "Завтрак", 400);
    public static final Meal MEAL_LUNCH = new Meal(MEAL_LUNCH_ID, LocalDateTime.of(2020, 1, 10, 13, 0), "Обед", 500);
    public static final Meal MEAL_DINNER = new Meal(MEAL_DINNER_ID, LocalDateTime.of(2020, 1, 12, 19, 0), "Ужин", 600);
    public static final Meal MEAL_NIGHT_DINNER = new Meal(MEAL_NIGHT_DINNER_ID, LocalDateTime.of(2020, 1, 13, 0, 0), "Поздний перекус", 300);
    // Admin meal
    public static final int MEAL_ADMIN_BREAKFAST_ID = 100006;
    public static final int MEAL_ADMIN_LUNCH_ID = 100007;
    public static final int MEAL_ADMIN_DINNER_ID = 100008;
    public static final Meal MEAL_ADMIN_BREAKFAST = new Meal(MEAL_ADMIN_BREAKFAST_ID, LocalDateTime.of(2020, 1, 11, 11, 0), "Завтрак", 450);
    public static final Meal MEAL_ADMIN_LUNCH = new Meal(MEAL_ADMIN_LUNCH_ID, LocalDateTime.of(2020, 1, 11, 14, 0), "Обед", 550);
    public static final Meal MEAL_ADMIN_DINNER = new Meal(MEAL_ADMIN_DINNER_ID, LocalDateTime.of(2020, 1, 12, 19, 0), "Ужин", 650);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 1, 1, 1, 0), "New meal", 100);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_BREAKFAST);
        updated.setDescription("Обновленный завтрак");
        updated.setCalories(200);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertMatch(actual, expected, false);
    }

    public static void assertMatch(Meal actual, Meal expected, Boolean ignoreId) {
        ObjectAssert<Meal> mealObjectAssert = assertThat(actual);
        if (ignoreId) {
            mealObjectAssert.isEqualToIgnoringGivenFields(expected, "id");
        } else {
            mealObjectAssert.isEqualTo(expected);
        }
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
