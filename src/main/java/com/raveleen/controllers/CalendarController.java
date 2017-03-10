package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Post;
import com.raveleen.services.PostService;
import com.raveleen.services.UserService;
import com.raveleen.services.UtilsService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Святослав on 17.01.2017.
 */
@Controller
public class CalendarController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private UtilsService utilsService;

    @RequestMapping("/calendar")
    public String calendarSelf(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);

        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(dbUser.getId()));
        model.addAttribute("following", userService.getNumberOfFollowings(dbUser.getId()));
        return "calendar-acc";
    }

    @RequestMapping("/calendar/{id}")
    public String calendarAnotherUser(Model model, @PathVariable("id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserById(id);
        if (login.equals(dbUser.getLogin())) {
            return "redirect:/calendar";
        }

        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(id));
        model.addAttribute("following", userService.getNumberOfFollowings(id));
        return "calendar";
    }

    @RequestMapping(value = "/get-following-posts/{user-id}/{from}")
    @ResponseBody
    public String[][] getFollowingPosts(@PathVariable("user-id") long userId,
                                        @PathVariable("from") int from) {
        List<Post> posts = postService.getFollowingsPosts(userId, from);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        String[][] storage = utilsService.arrayFill(posts, customUser);
        return storage;
    }

    @RequestMapping(value = "/user-list/recs/{user-id}/{from}")
    @ResponseBody
    public String[] getUsersFollowers(@PathVariable("user-id") long userId,
                                      @PathVariable("from") int from) {
        List<CustomUser> users = userService.recFollowingByFollowedId(userId, from);
        String[] response = new String[users.size()];

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        int counter = 0;
        for (CustomUser customUser1 : users) {
            response[counter] = utilsService.createFragmentUser(customUser1, customUser);
            counter++;
        }

        return response;
    }
}
