package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.UserTestData;

public class InMemoryUserTestRepository extends UserTestData {
    public static void init(InMemoryUserRepository repository) {
        repository.map.clear();
        repository.map.put(USER_ID, UserTestData.USER);
        repository.map.put(ADMIN_ID, UserTestData.ADMIN);
    }
}
