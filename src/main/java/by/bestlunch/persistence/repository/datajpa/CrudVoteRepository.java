package by.bestlunch.persistence.repository.datajpa;

import by.bestlunch.persistence.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.votingDate = :votingDate")
    List<Vote> findAllByVotingDate(@Param("votingDate") LocalDate votingDate);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.votingDate >= :startDate AND v.votingDate <= :endDate")
    List<Vote> getAllVotesBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}