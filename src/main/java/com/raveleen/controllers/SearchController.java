package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Святослав on 31.12.2016.
 */
@Controller
public class SearchController {
    @Autowired
    private UserService userService;

    @RequestMapping("/search")
    public String index(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);

        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(dbUser.getId()));
        model.addAttribute("following", userService.getNumberOfFollowings(dbUser.getId()));
        return "search";
    }

    @RequestMapping("/search/{pattern}/{number}")
    @ResponseBody
    public String[][] search(@PathVariable("pattern") String pattern,
                             @PathVariable("number") int number) {
        List<CustomUser> list = userService.findByLoginStartingWith(pattern, number);
        System.out.println(list.toString());
        String[][] storage = new String[10][3];
        int counter = 0;
        for (CustomUser a : list) {
            storage[counter][0] = a.getLogin();
            storage[counter][1] = "" + a.getId();
            if (a.getProfileImage() != null) {
                storage[counter][2] = "" + a.getProfileImage().getId();
            } else {
                storage[counter][2] = "" + "-1";
            }
            System.out.println(storage[counter][0] + " "
                    + storage[counter][1] + " "
                    + storage[counter][2]);
            counter += 1;
        }

        return storage;
    }
}
