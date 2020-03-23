package by.bestlunch.persistence.repository;

import by.bestlunch.persistence.model.Vote;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository {

    Vote save(Vote vote);

    List<Vote> getAllByVotingDate(LocalDate votingDate);

    List<Vote> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate);

    Optional<Vote> getUserVote(int userId, LocalDate date);

    boolean delete(int id, int restaurantId, int userId);
}