package by.bestlunch.persistence.repository.datajpa;

import by.bestlunch.persistence.model.Vote;
import by.bestlunch.persistence.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static by.bestlunch.util.DateTimeUtil.*;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private final CrudVoteRepository crudVoteRepository;

    @Autowired
    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository) {
        this.crudVoteRepository = crudVoteRepository;
    }

    @Override
    public Vote save(Vote vote) {
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

    public Optional<Vote> getUserVote(int userId, LocalDate date) {
        return crudVoteRepository.getVoteByUserIdAndVotingDate(userId, date);
    }

    @Override
    public boolean delete(int id, int restaurantId, int userId) {
        return crudVoteRepository.delete(id, restaurantId, userId) != 0;
    }
}