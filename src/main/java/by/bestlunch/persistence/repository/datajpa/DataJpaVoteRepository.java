package by.bestlunch.persistence.repository.datajpa;

import by.bestlunch.persistence.model.Vote;
import by.bestlunch.persistence.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static by.bestlunch.util.DateTimeUtil.*;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private final CrudVoteRepository crudVoteRepository;

    @Autowired
    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository) {
        this.crudVoteRepository = crudVoteRepository;
    }

    @Override
    public Vote vote(Vote vote) {
        return crudVoteRepository.save(vote);
    }

    @Override
    public List<Vote> getAllByVotingDate(LocalDate votingDate) {
        return crudVoteRepository.findAllByVotingDate(votingDate);
    }

    @Override
    public List<Vote> getBetweenInclusive(LocalDate startDate, LocalDate endDate) {
        return crudVoteRepository.getAllVotesBetween(getStartInclusive(startDate), getEndInclusive(endDate));
    }
}