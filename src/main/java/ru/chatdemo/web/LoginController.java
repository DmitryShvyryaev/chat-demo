package ru.chatdemo.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import ru.chatdemo.model.User;
import ru.chatdemo.util.UserValidator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@Slf4j
public class LoginController {

    private final SessionListener sessionListener;

    private final UserValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @GetMapping("/")
    public String root() {
        log.info("get root page");
        return "redirect:messages";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        log.info("Get login page");
        User user = (User) session.getAttribute("loggedUser");
        if (isAuthorized(user)) {
            return "redirect:messages";
        }
        return "login";
    }

    @GetMapping("/messages")
    public String getMessages(Model model, HttpSession session) {
        log.info("Get messages page");
        User user = (User) session.getAttribute("loggedUser");
        if (!isAuthorized(user))
            return "redirect:login";
        model.addAttribute("user", user);
        return "messages";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        log.info("Logout");
        session.invalidate();
        return "redirect:login";
    }

    @PostMapping(value = "/security-check")
    public String enterChat(@Valid User user, HttpSession session) {
        log.info("Security check user {}", user);
        sessionListener.add(session, user);
        session.setAttribute("loggedUser", user);
        return "redirect:messages";
    }

    private boolean isAuthorized(User user) {
        log.info("Check authorized for user {}", user);
        return user != null && StringUtils.hasLength(user.getName());
    }
}
