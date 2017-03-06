package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Святослав on 03.01.2017.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * ADMIN PAGE.
     * Redirects admin to his control panel.
     */
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    /**
     * UNAUTHORIZED.
     * Controls not authenticated users.
     */
    @RequestMapping("/unauthorized")
    public String unauthorized(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    /**
     * USER ACCOUNT.
     * Redirects user to him account immediately after registration/login and
     * after click on "My page" link.
     */
    @RequestMapping("/")
    public String index(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);
        if (dbUser.getProfileImage() != null) {
            System.out.println(dbUser.getProfileImage().getId());
        }
        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(dbUser.getId()));
        model.addAttribute("following", userService.getNumberOfFollowings(dbUser.getId()));
        return "user-acc";
    }

    @RequestMapping("/user/{id}")
    public String userAccount(Model model, @PathVariable("id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserById(id);
        if (login.equals(dbUser.getLogin())) {
            return "redirect:/";
        }

        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(id));
        model.addAttribute("following", userService.getNumberOfFollowings(id));
        return "user";
    }

    @RequestMapping(value = "/follow/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String follow(Model model, @PathVariable("id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser userFollows = userService.getUserByLogin(login);
        CustomUser userToFollow = userService.getUserById(id);
        userService.addFollower(userFollows, userToFollow);
        return "followed";
    }

    @RequestMapping(value = "/unfollow/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String unFollow(Model model, @PathVariable("id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser userFollows = userService.getUserByLogin(login);
        CustomUser userToUnFollow = userService.getUserById(id);
        userService.deleteFollower(userFollows, userToUnFollow);
        return "unfollowed";
    }

    @RequestMapping(value = "/is-following/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean isFollowing(Model model, @PathVariable("id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser userIsFollow = userService.getUserByLogin(login);
        CustomUser userIsFollowed = userService.getUserById(id);
        System.out.println(login);
        System.out.println(userIsFollowed.getId());
        boolean flag = userService.isFollowing(userIsFollow, userIsFollowed);
        return flag;
    }

    @RequestMapping(value = "/user/followers-number/{id}")
    @ResponseBody
    public String followersNumber(@PathVariable("id") long id) {
        String response = new String();
        response = "" + userService.getNumberOfFollowers(id);
        return response;
    }

    @RequestMapping(value = "/user/following-number/{id}")
    @ResponseBody
    public String followingNumber(@PathVariable("id") long id) {
        String response = new String();
        response = "" + userService.getNumberOfFollowings(id);
        return response;
    }

}
