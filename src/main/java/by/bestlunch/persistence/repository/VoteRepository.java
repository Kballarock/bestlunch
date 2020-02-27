package by.bestlunch.persistence.repository;

import by.bestlunch.persistence.model.Vote;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    Vote vote(Vote vote);

    List<Vote> getAllByVotingDate(LocalDate votingDate);

    List<Vote> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate);
}