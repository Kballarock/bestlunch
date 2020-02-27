package by.bestlunch.service;

import by.bestlunch.persistence.model.Vote;
import by.bestlunch.persistence.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> getAllByDate(LocalDate dateVoting) {
        return voteRepository.getAllByVotingDate(dateVoting);
    }

    public List<Vote> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        return voteRepository.getBetweenInclusive(startDate, endDate);
    }
}