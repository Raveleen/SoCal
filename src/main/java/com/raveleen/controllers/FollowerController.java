package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.services.UserService;
import com.raveleen.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Святослав on 31.12.2016.
 */
@Controller
public class FollowerController {
    @Autowired
    private UserService userService;

    @Autowired
    private UtilsService utilsService;

    @RequestMapping("/followers/{user-id}")
    public String followers(Model model, @PathVariable("user-id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserById(id);

        if (login.equals(dbUser.getLogin())) {
            model.addAttribute("user", dbUser);
            model.addAttribute("followers", userService.getNumberOfFollowers(id));
            model.addAttribute("following", userService.getNumberOfFollowings(id));
            return "followers-acc";
        }

        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(id));
        model.addAttribute("following", userService.getNumberOfFollowings(id));
        return "followers";
    }

    @RequestMapping("/following/{user-id}")
    public String following(Model model, @PathVariable("user-id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserById(id);

        if (login.equals(dbUser.getLogin())) {
            model.addAttribute("user", dbUser);
            model.addAttribute("followers", userService.getNumberOfFollowers(id));
            model.addAttribute("following", userService.getNumberOfFollowings(id));
            return "following-acc";
        }

        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(id));
        model.addAttribute("following", userService.getNumberOfFollowings(id));
        return "following";
    }

    @RequestMapping(value = "/user-list/followers/{user-id}/{from}")
    @ResponseBody
    public String[] getUsersFollowers(@PathVariable("user-id") long userId, @PathVariable("from") int from) {
        List<CustomUser> users = userService.followersByFollowedId(userId, from);
        String[] response = new String[users.size()];

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        int i = 0;
        for (CustomUser customUser1 : users) {
            response[i] = utilsService.createFragmentUser(customUser1, customUser);
            i++;
        }

        return response;
    }

    @RequestMapping(value = "/user-list/following/{user-id}/{from}")
    @ResponseBody
    public String[] getUsersFollowing(@PathVariable("user-id") long userId, @PathVariable("from") int from) {
        List<CustomUser> users = userService.followingByFollowedId(userId, from);
        String[] response = new String[users.size()];

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        int i = 0;
        for (CustomUser customUser1 : users) {
            response[i] = utilsService.createFragmentUser(customUser1, customUser);
            i++;
        }

        return response;
    }
}
