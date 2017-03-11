package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Image;
import com.raveleen.entities.Post;
import com.raveleen.services.UserService;
import com.raveleen.services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Святослав on 11.03.2017.
 */
@Controller
public class EventsController {
    @Autowired
    private UtilsService utilsService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get-events/{user-id}/{from}")
    @ResponseBody
    public String[][] getEvents(@PathVariable("user-id") long userId,
                                        @PathVariable("from") int from) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        String[][] storage = new String[1][1];
        return storage;
    }

    @RequestMapping(value = "/get-following-events/{user-id}/{from}")
    @ResponseBody
    public String[][] getFollowingEvents(@PathVariable("user-id") long userId,
                                        @PathVariable("from") int from) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        String[][] storage = new String[1][1];
        return storage;
    }

    @RequestMapping(value = "/event-create", method = RequestMethod.POST)
    @ResponseBody
    public String[][] createEvent(@RequestParam("text") String text,
                                 @RequestParam(value = "photo") MultipartFile body) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        String[][] storage = new String[1][1];

        return storage;
    }
}
