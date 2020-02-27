package by.bestlunch.persistence.repository;

import by.bestlunch.persistence.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id =: restaurantId AND m.date =: date")
    List<Menu> findAllByDate(@Param("restaurantId") Integer restaurantId, @Param("date") LocalDate date);
}