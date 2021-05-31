package ru.chatdemo.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.chatdemo.model.User;

@Controller
@AllArgsConstructor
@Slf4j
public class LoginController {

    private final User loggedUser;

    @GetMapping("/")
    public String root() {
        log.info("get root page");
        return "redirect:messages";
    }

    @GetMapping("/login")
    public String login() {
        log.info("Get login page");
        if (isAuthorized()) {
            return "redirect:messages";
        }
        return "login";
    }

    @GetMapping("/messages")
    public String getMessages(Model model) {
        log.info("Get messages page");
        if (!isAuthorized())
            return "redirect:login";
        model.addAttribute("user", loggedUser);
        return "messages";
    }

    @PostMapping("/logout")
    public String logout() {
        log.info("Logout");
        loggedUser.destroy();
        return "redirect:login";
    }

    @PostMapping(value = "/security-check")
    public String enterChat(User user) {
        log.info("Security check user {}", user);
        loggedUser.setName(user.getName());
        loggedUser.setStatus(user.getStatus());
        return "redirect:messages";
    }

    private boolean isAuthorized() {
        log.info("Check authorized");
        return loggedUser != null && StringUtils.hasLength(loggedUser.getName());
    }
}
