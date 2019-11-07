package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.fail;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal userMeal = service.get(USER_MEAL_ID, USER_ID);
        assertMatch(userMeal, USER_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void get_notBelongingMeal_notFoundException() {
        service.get(ADMIN_MEAL_ID, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(service.getAll(USER_MEAL_ID), USER_MEALS);
    }

    @Test(expected = NotFoundException.class)
    public void delete_notBelongingMeal_notFoundException() {
        service.delete(ADMIN_MEAL_ID, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> adminMealsBetweenDates = service.getBetweenDates(null, null, ADMIN_ID);
        assertMatch(adminMealsBetweenDates,ADMIN_MEAL);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_MEAL_ID);
        assertMatch(all, USER_MEALS);
    }

    @Test
    public void update() {
        Meal beforeUpdate = new Meal(USER_MEAL_ID, LocalDateTime.now(), "Breakfast", 320);
        beforeUpdate.setDescription("sec");
        beforeUpdate.setCalories(297);
        service.update(beforeUpdate, USER_ID);
        assertMatch(service.get(USER_MEAL_ID, USER_ID),beforeUpdate);
    }

    @Test(expected = NotFoundException.class)
    public void update_notBelongingMeal_notFoundException() {
        Meal userMeal = service.get(USER_MEAL_ID, USER_ID);
        service.update(userMeal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal createdMeal = new Meal(LocalDateTime.now(), "myMeal", 2000);
        Meal savedMeal = service.create(createdMeal, USER_ID);
        createdMeal.setId(savedMeal.getId());
        assertMatch(service.getAll(USER_ID), savedMeal, USER_MEAL, USER_MEAL_2);
    }
}