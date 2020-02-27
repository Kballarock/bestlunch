package by.bestlunch.web.controller.user;

import by.bestlunch.security.AuthorizedUser;
import by.bestlunch.security.SecurityService;
import by.bestlunch.validation.view.ErrorSequence;
import by.bestlunch.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class ProfileUIController extends AbstractUserController {

    private final SecurityService securityService;

    @Autowired
    public ProfileUIController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/profile")
    public String profile(ModelMap model, @AuthenticationPrincipal AuthorizedUser authUser) {
        model.addAttribute("userDto", authUser.getUserDto());
        model.addAttribute("profile", true);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Validated({ErrorSequence.First.class, ErrorSequence.Second.class, ErrorSequence.Third.class})
                                        UserDto userDto, BindingResult result, SessionStatus status,
                                @AuthenticationPrincipal AuthorizedUser authUser) {
        if (result.hasErrors()) {
            return "profile";
        }
        super.update(userDto, authUser.getId());
        authUser.update(userDto);
        status.setComplete();
        return "redirect:/restaurant";
    }

    @GetMapping("/registration")
    public String register(ModelMap model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("register", true);
        return "registration";
    }

    @PostMapping("/registration")
    public String saveRegister(@Validated({ErrorSequence.First.class, ErrorSequence.Second.class, ErrorSequence.Third.class})
                               @ModelAttribute("userDto") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", true);
            return "registration";
        }
        super.create(userDto);
        securityService.autoLogin(userDto.getEmail(), userDto.getMatchingPassword());
        return "redirect:/restaurant";
    }
}