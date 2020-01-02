package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    int deleteByIdAndUserId(int id, int userId);

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.id=:id AND m.user.id=:userId")
    Meal getByIdAndUserId(@Param("id") int id, @Param("userId") int userId);

    List<Meal> getAllByUserIdOrderByIdDesc(int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :start AND m.dateTime < :end ORDER BY m.dateTime DESC")
    List<Meal> getBetweenInclusive(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("userId") int userId);

}
