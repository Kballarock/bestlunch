package by.bestlunch.persistence.repository.datajpa;

import by.bestlunch.persistence.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.votingDate = :votingDate")
    List<Vote> findAllByVotingDate(@Param("votingDate") LocalDate votingDate);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.votingDate >= :startDate AND v.votingDate <= :endDate")
    List<Vote> getAllVotesBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Optional<Vote> getVoteByUserIdAndVotingDate(int userId, LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.restaurant.id=:restaurantId AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId, @Param("userId") int userId);
}