package by.bestlunch.web.controller.restaurant;

import by.bestlunch.VoteTestData;
import by.bestlunch.persistence.model.Restaurant;
import by.bestlunch.persistence.model.Vote;
import by.bestlunch.service.VoteService;
import by.bestlunch.util.DateTimeUtil;
import by.bestlunch.web.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static by.bestlunch.RestaurantTestData.*;
import static by.bestlunch.TestUtil.readFromJson;
import static by.bestlunch.TestUtil.readFromJsonMvcResult;
import static by.bestlunch.UserTestData.ADMIN;
import static by.bestlunch.UserTestData.USER;
import static by.bestlunch.VoteTestData.*;
import static by.bestlunch.util.RestaurantUtil.getDtoWithCount;
import static by.bestlunch.util.RestaurantUtil.getRestaurantDto;
import static by.bestlunch.web.controller.restaurant.RestaurantUserRestController.RESTAURANT_USER_REST_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantUserRestControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteService voteService;

    RestaurantUserRestControllerTest() {
        super(RESTAURANT_USER_REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet(RESTAURANT_BURGER_KING.id()).basicAuth(USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHERS.contentJson(RESTAURANT_BURGER_KING))
                .andExpect(result -> RESTAURANT_MATCHERS
                        .assertMatch(readFromJsonMvcResult(result, Restaurant.class), RESTAURANT_BURGER_KING));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet(RESTAURANT_RENAISSANCE.id()))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(121234).basicAuth(USER))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAllWithCountPerDate() throws Exception {
        List<Vote> votes = voteService.getAllByDate(LocalDate.now());
        perform(doGet().basicAuth(USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_DTO_MATCHERS.contentJson(getDtoWithCount(USER.getId(), RESTAURANTS, votes)));
    }

    @Test
    void getBetween() throws Exception {
        perform(doGet("filter").basicAuth(USER).unwrap()
                .param("startDate", "2019-12-16")
                .param("endDate", "2019-12-16"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESTAURANT_DTO_MATCHERS.contentJson(
                        getRestaurantDto(RESTAURANT_BURGER_KING, 1L, false),
                        getRestaurantDto(RESTAURANT_RENAISSANCE, 1L, false)));
    }

    @Test
    void getBetweenForAllDates() throws Exception {
        List<Vote> votes = voteService.getBetween(null, null);
        perform(doGet("filter?startDate=&endTime=").basicAuth(USER))
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_DTO_MATCHERS
                        .contentJson(getDtoWithCount(USER.getId(), RESTAURANTS, votes)));
    }

    @Test
    void createNewVoteBefore1100() throws Exception {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 18, 10, 0));

        Vote newVote = VoteTestData.getNewUser();
        ResultActions action =
                perform(doPost(newVote.getRestaurant().id() + "/vote").jsonBody(newVote).basicAuth(USER))
                        .andDo(print());

        Vote created = readFromJson(action, Vote.class);
        Integer newId = created.getId();
        newVote.setId(newId);
        VOTE_MATCHERS.assertMatch(created, newVote);
        VOTE_MATCHERS.assertMatch(voteService.createOrUpdate(USER.id(), newVote.getRestaurant().id()), newVote);
    }

    @Test
    void createNewVoteAfter1100() throws Exception {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 18, 15, 0));

        Vote newVote = VoteTestData.getNewUser();
        perform(doPost(newVote.getRestaurant().id() + "/vote").jsonBody(newVote).basicAuth(USER))
                .andDo(print()).andExpect(status().isConflict());
    }

    @Test
    void updateVoteBefore1100() throws Exception {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 17, 10, 0));

        Vote updated = VoteTestData.getUpdated();
        ResultActions action =
                perform(doPost(updated.getRestaurant().id() + "/vote").jsonBody(updated).basicAuth(ADMIN))
                        .andDo(print());

        Vote created = readFromJson(action, Vote.class);
        Integer newId = created.getId();
        updated.setId(newId);
        VOTE_MATCHERS.assertMatch(created, updated);
        VOTE_MATCHERS.assertMatch(voteService.createOrUpdate(ADMIN.id(), updated.getRestaurant().id()), created);
    }

    @Test
    void updateVoteAfter1100() throws Exception {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 17, 15, 0));

        Vote updated = VoteTestData.getUpdated();
        perform(doPost(updated.getRestaurant().id() + "/vote").jsonBody(updated).basicAuth(ADMIN))
                .andDo(print()).andDo(print()).andExpect(status().isConflict());
    }

    @Test
    void deleteVoteBefore1100() throws Exception {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 16, 10, 0));

        perform(doDelete(RESTAURANT_RENAISSANCE.id(), "/vote").basicAuth(USER))
                .andExpect(status().isNoContent());
        assertThrows(NoSuchElementException.class, () -> voteService.get(USER.id(), LocalDate.of(2019, 12, 16)));
    }

    @Test
    void deleteVoteAfter1100() throws Exception {
        DateTimeUtil.useFixedClockAt(LocalDateTime.of(2019, 12, 16, 15, 0));

        perform(doDelete(RESTAURANT_RENAISSANCE.id(), "/vote").basicAuth(USER))
                .andExpect(status().isConflict());
    }
}