package ru.javawebinar.topjava.service.datajpa;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() {
        Assertions.assertThat(service.getWithMeals(USER_ID).getMeals().size()).isEqualTo(USER_ID_MEALS_SIZE);
    }

    @Test
    public void getWithMealsEmpty() {
        Assertions.assertThat(service.getWithMeals(EMPTY_USER_ID).getMeals().size()).isEqualTo(0);
    }
}
