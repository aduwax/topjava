package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealExcessAttribute {
    private boolean excess;

    public UserMealExcessAttribute(boolean excess) {
        this.excess = excess;
    }

    public boolean isExcess() {
        return excess;
    }

    public void setExcess(boolean excess) {
        this.excess = excess;
    }

    @Override
    public String toString() {
        return "UserMealExcessAttribute{" +
                "excess=" + excess +
                '}';
    }
}
