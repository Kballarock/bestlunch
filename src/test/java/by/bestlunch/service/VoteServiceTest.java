package by.bestlunch.service;

import by.bestlunch.persistence.model.Vote;
import by.bestlunch.util.DateTimeUtil;
import by.bestlunch.util.exception.MenuException;
import by.bestlunch.util.exception.VoteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.NoSuchElementException;

import static by.bestlunch.RestaurantTestData.RESTAURANT_BURGER_KING;
import static by.bestlunch.RestaurantTestData.RESTAURANT_RENAISSANCE;
import static by.bestlunch.UserTestData.ADMIN;
import static by.bestlunch.UserTestData.USER;
import static by.bestlunch.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    @Transactional
    void getAllByDate() {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 16, 15, 0));
        List<Vote> votes = service.getAllByDate(DateTimeUtil.now());
        VOTE_MATCHERS.assertMatch(votes, VOTE1, VOTE2);
    }

    @Test
    @Transactional
    void getBetween() {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 17, 15, 0));
        LocalDate startDate = LocalDate.of(2019, Month.DECEMBER, 16);
        LocalDate endDate = LocalDate.of(2019, Month.DECEMBER, 17);
        List<Vote> votes = service.getBetween(startDate, endDate);
        VOTE_MATCHERS.assertMatch(votes, VOTE1, VOTE2, VOTE3, VOTE4);
    }

    @Test
    @Transactional
    void getBetweenWithNullParameters() {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 17, 15, 0));
        List<Vote> votes = service.getBetween(null, null);
        VOTE_MATCHERS.assertMatch(votes, VOTE1, VOTE2, VOTE3, VOTE4);
    }

    @Test
    void createNewVoteBefore1100() {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 18, 10, 0));
        Vote newVote = getNewUser();
        Vote created = service.createOrUpdate(USER.getId(), RESTAURANT_BURGER_KING.getId());
        Integer newId = created.getId();
        newVote.setId(newId);
        VOTE_MATCHERS.assertMatch(created, newVote);
    }

    @Test
    void createNewVoteAfter1100() {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 18, 11, 1));
        assertThrows(VoteException.class, () ->
                service.createOrUpdate(USER.getId(), RESTAURANT_BURGER_KING.getId()));
    }

    @Test
    void createWithNullRestaurantMenu() {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 20, 10, 0));
        assertThrows(MenuException.class, () ->
                service.createOrUpdate(USER.getId(), RESTAURANT_BURGER_KING.getId()));
    }

    @Test
    void updateVoteBefore1100() {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 17, 10, 0));
        Vote updated = getUpdated();
        Vote created = service.createOrUpdate(ADMIN.getId(), RESTAURANT_RENAISSANCE.getId());
        Integer newId = created.getId();
        updated.setId(newId);
        VOTE_MATCHERS.assertMatch(created, updated);
    }

    @Test
    void updateVoteAfter1100() {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 18, 11, 1));
        assertThrows(VoteException.class, () ->
                service.createOrUpdate(USER.getId(), RESTAURANT_RENAISSANCE.getId()));
    }

    @Test
    void deleteVoteBefore1100() {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 17, 10, 0));
        service.delete(RESTAURANT_BURGER_KING.getId(), ADMIN.getId());
        assertThrows(NoSuchElementException.class, () ->
                service.get(ADMIN.getId(), DateTimeUtil.now()));
    }

    @Test
    void deleteVoteAfter1100() {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 17, 11, 15));
        assertThrows(VoteException.class, () ->
                service.delete(RESTAURANT_BURGER_KING.getId(), ADMIN.getId()));
    }
}