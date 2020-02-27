package by.bestlunch.persistence.model;

import by.bestlunch.persistence.model.base.AbstractBaseEntity;
import by.bestlunch.util.DateTimeUtil;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "voting_date"},
        name = "vote_unique_user_per_date_idx")})
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "voting_date", nullable = false, columnDefinition = "date default (current_date + interval 1 year)")
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
    private LocalDate votingDate;

    public Vote() {
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate votingDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.votingDate = votingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getVotingDate() {
        return votingDate;
    }

    public void setVotingDate(LocalDate votingDate) {
        this.votingDate = votingDate;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                "user=" + user +
                ", restaurant=" + restaurant +
                ", dateVoting=" + votingDate +
                '}';
    }
}