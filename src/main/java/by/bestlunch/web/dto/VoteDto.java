package by.bestlunch.web.dto;

import by.bestlunch.persistence.model.Vote;

public class VoteDto {

    private final Vote vote;
    private final boolean created;

    public VoteDto(Vote vote, boolean created) {
        this.vote = vote;
        this.created = created;
    }

    public Vote getVote() {
        return vote;
    }

    public boolean isCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "VoteDto{" +
                "vote=" + vote +
                ", created=" + created +
                '}';
    }
}