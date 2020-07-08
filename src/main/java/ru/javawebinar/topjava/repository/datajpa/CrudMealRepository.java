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
    @Query("DELETE FROM Meal m WHERE m.id=:id and m.user.id = :userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = (:id) AND m.user.id = (:user_id)")
    Meal getByIdAndUserIdAndFetchUserEagerly(@Param("id") int id, @Param("user_id") int userId);

    @Query("SELECT m FROM Meal m WHERE m.id = (:id) AND m.user.id = (:user_id)")
    Meal getByIdAndUserId(@Param("id") int id, @Param("user_id") int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id = (:user_id) ORDER BY m.dateTime DESC")
    List<Meal> getAll(@Param("user_id") int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id = (:user_id) " +
            "AND m.dateTime >= (:start_datetime) " +
            "AND m.dateTime < (:end_datetime) " +
            "ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(
            @Param("user_id") int userId,
            @Param("start_datetime") LocalDateTime startDateTime,
            @Param("end_datetime") LocalDateTime endDateTime
    );
}
