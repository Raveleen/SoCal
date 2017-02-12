package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.ProfileImage;
import com.raveleen.services.ProfileImageService;
import com.raveleen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Святослав on 17.01.2017.
 */
@Controller
public class SettingsController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProfileImageService profileImageService;

    @RequestMapping("/settings")
    public String settings(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);

        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(dbUser.getId()));
        model.addAttribute("following", userService.getNumberOfFollowings(dbUser.getId()));
        return "settings";
    }

    /**
     * UPDATE.
     * Updates user information.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String info) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);
        dbUser.setEmail(email);
        dbUser.setPhone(phone);
        dbUser.setInfo(info);

        userService.updateUser(dbUser);

        return "redirect:/";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadProfilePhoto(@RequestParam(value = "photo") MultipartFile body) throws IOException {
        if (body == null) {
            return "/settings";
        }
        ProfileImage profileImage = new ProfileImage(body.getBytes());
        profileImageService.addProfileImage(profileImage);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);
        dbUser.setProfileImage(profileImage);
        userService.updateUser(dbUser);
        return "redirect:/";
    }
}
