package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserMealWithExcessAtomic {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private AtomicBoolean excess;

    public UserMealWithExcessAtomic(LocalDateTime dateTime, String description, int calories, AtomicBoolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public void setExcess(AtomicBoolean excess) {
        this.excess = excess;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
