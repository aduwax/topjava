package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND_ID = 1;
    public static final int NEW_MEAL_ID = START_SEQ + 9;
    // User meal
    public static final int MEAL_BREAKFAST_ID = START_SEQ + 2;
    public static final int MEAL_LUNCH_ID = START_SEQ + 3;
    public static final int MEAL_DINNER_ID = START_SEQ + 4;
    public static final int MEAL_NIGHT_DINNER_ID = START_SEQ + 5;
    public static final Meal MEAL_BREAKFAST = new Meal(MEAL_BREAKFAST_ID, LocalDateTime.of(2020, 1, 10, 10, 0), "Завтрак", 400);
    public static final Meal MEAL_LUNCH = new Meal(MEAL_LUNCH_ID, LocalDateTime.of(2020, 1, 10, 13, 0), "Обед", 500);
    public static final Meal MEAL_DINNER = new Meal(MEAL_DINNER_ID, LocalDateTime.of(2020, 1, 12, 19, 0), "Ужин", 600);
    public static final Meal MEAL_NIGHT_DINNER = new Meal(MEAL_NIGHT_DINNER_ID, LocalDateTime.of(2020, 1, 13, 0, 0), "Поздний перекус", 300);
    // Admin meal
    public static final int MEAL_ADMIN_BREAKFAST_ID = START_SEQ + 6;
    public static final int MEAL_ADMIN_LUNCH_ID = START_SEQ + 7;
    public static final int MEAL_ADMIN_DINNER_ID = START_SEQ + 8;
    public static final Meal MEAL_ADMIN_BREAKFAST = new Meal(MEAL_ADMIN_BREAKFAST_ID, LocalDateTime.of(2020, 1, 11, 11, 0), "Завтрак", 450);
    public static final Meal MEAL_ADMIN_LUNCH = new Meal(MEAL_ADMIN_LUNCH_ID, LocalDateTime.of(2020, 1, 11, 14, 0), "Обед", 550);
    public static final Meal MEAL_ADMIN_DINNER = new Meal(MEAL_ADMIN_DINNER_ID, LocalDateTime.of(2020, 1, 12, 19, 0), "Ужин", 650);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, 1, 1, 1, 0), "New meal", 100);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_BREAKFAST);
        updated.setDescription("Обновленный завтрак");
        updated.setDateTime(LocalDateTime.of(2020, 1, 10, 11, 30));
        updated.setCalories(200);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
    
    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
