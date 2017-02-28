package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.enums.UserRole;
import com.raveleen.services.UserService;
import com.raveleen.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Святослав on 31.12.2016.
 */
@Controller
public class AuthorizationController {
    @Autowired
    private UserService userService;

    @Autowired
    private UtilsService utilsService;

    /**
     * REGISTRATION.
     * Creates new user.
     */
    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         @RequestParam String password,
                         Model model) {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        String passHash = encoder.encodePassword(password, null);

        CustomUser dbUser = new CustomUser(login, passHash, UserRole.USER, email, phone);
        userService.addUser(dbUser);

        return "redirect:/";
    }

    /**
     * LOGIN.
     * Authentication page.
     */
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * LOGIN VALIDATION.
     * Controls if login is valid for new user or not.
     */
    @RequestMapping(value = "/login/validation", params = {"login"})
    @ResponseBody
    public String isLoginValid(@RequestParam("login") String login) {
        String response = new String();
        if (login.length() < 4) {
            response = "1";
        } else if (userService.existsByLogin(login)) {
            response = "2";
        } else if (!utilsService.isWord(login)) {
            response = "3";
        } else {
            response = "4";
        }
        return response;
    }

    @RequestMapping(value = "/settings/validation", params = {"login"})
    @ResponseBody
    public String settingsIsLoginValid(@RequestParam("login") String login) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentLogin = user.getUsername();

        String response = new String();
        if (login.length() < 4) {
            response = "1";
        } else if (login.equals(currentLogin)) {
            response = "2";
        } else if (userService.existsByLogin(login)) {
            response = "3";
        } else if (!utilsService.isWord(login)) {
            response = "4";
        } else {
            response = "5";
        }
        return response;
    }
}
