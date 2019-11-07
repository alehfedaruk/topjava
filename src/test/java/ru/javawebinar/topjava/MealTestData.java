package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final List<Meal> USER_MEALS = new ArrayList<>();
    public static final int MEAL_ID_COUNTER = 5000000;
    public static final int USER_MEAL_ID = MEAL_ID_COUNTER;
    public static final int ADMIN_MEAL_ID = MEAL_ID_COUNTER + 2;
    public static final Meal USER_MEAL = new Meal(USER_MEAL_ID, LocalDateTime.now(), "Breakfast", 320);
    public static final Meal USER_MEAL_2 = new Meal(USER_MEAL_ID+1, LocalDateTime.now(), "Dinner", 550);

    {
        USER_MEALS.add(USER_MEAL);
        USER_MEALS.add(USER_MEAL_2);
    }

    public static final Meal ADMIN_MEAL = new Meal(ADMIN_MEAL_ID, LocalDateTime.now(), "Midnight meal", 1200);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }
}
