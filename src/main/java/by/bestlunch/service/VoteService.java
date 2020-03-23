package by.bestlunch.service;

import by.bestlunch.persistence.model.Menu;
import by.bestlunch.persistence.model.Vote;
import by.bestlunch.persistence.repository.MenuRepository;
import by.bestlunch.persistence.repository.RestaurantRepository;
import by.bestlunch.persistence.repository.UserRepository;
import by.bestlunch.persistence.repository.VoteRepository;
import by.bestlunch.util.exception.MenuException;
import by.bestlunch.util.exception.VoteException;
import by.bestlunch.web.dto.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static by.bestlunch.util.DateTimeUtil.DEFAULT_EXPIRED_TIME;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository, MenuRepository menuRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    public List<Vote> getAllByDate(LocalDate dateVoting) {
        return voteRepository.getAllByVotingDate(dateVoting);
    }

    public List<Vote> getBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        return voteRepository.getBetweenInclusive(startDate, endDate);
    }

    @Transactional
    public void createOrUpdate(int userId, int restaurantId) {
        Vote newVote = new Vote(userRepository.get(userId), restaurantRepository.get(restaurantId), LocalDate.now());

        VoteDto todayVote = voteRepository.getUserVote(userId, LocalDate.now())
                .map(v -> {
                    v.setRestaurant(restaurantRepository.get(restaurantId));
                    return new VoteDto(v, false);
                })
                .orElseGet(() -> new VoteDto(newVote, true));

        if (LocalTime.now().isAfter(DEFAULT_EXPIRED_TIME)) {
            throw new VoteException("");
        }

        List<Menu> dailyMenu = menuRepository.getAllByDate(restaurantId, LocalDate.now());
        if (dailyMenu.size() == 0) {
            throw new MenuException("");
        }

        voteRepository.save(todayVote.getVote());
    }

    public void delete(int restaurantId, int userId) {
        Optional<Vote> userVote = voteRepository.getUserVote(userId, LocalDate.now());
        userVote.ifPresent(vote -> voteRepository.delete(userVote.get().id(), restaurantId, userId));
    }
}