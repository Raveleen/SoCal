package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Post;
import com.raveleen.services.PostService;
import com.raveleen.services.UserService;
import com.raveleen.services.UtilsService;
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
 * Created by Святослав on 01.02.2017.
 */
@Controller
public class LikesController {
    @Autowired
    private UserService userService;

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private PostService postService;

    @RequestMapping("/like/{post_id}")
    @ResponseBody
    public String likePost(@PathVariable("post_id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        Post post = postService.getById(id);
        if (!postService.isLiked(customUser.getId(), id)) {
            System.out.println("ISN'T LIKED " + id);
            post.addLike(customUser);
            postService.updatePost(post);
            return "liked";
        } else {
            return null;
        }
    }

    @RequestMapping("/unlike/{post_id}")
    @ResponseBody
    public String unLikePost(@PathVariable("post_id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        Post post = postService.getById(id);
        post.removeLike(customUser);
        postService.updatePost(post);

        return "unliked";
    }

    @RequestMapping("/number-of-likes/{post_id}")
    @ResponseBody
    public String numberOfLikes(@PathVariable("post_id") long id) {
        return "" + postService.getNumberOfLikes(id);
    }

    @RequestMapping(value = "/post/likes-number/{id}")
    @ResponseBody
    public String likesNumber(@PathVariable("id") long id) {
        String response = new String();
        response = "" + postService.getNumberOfLikes(id);
        return response;
    }

    @RequestMapping("/post-likes/{post-id}")
    public String userAccount(Model model, @PathVariable("post-id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = postService.getById(id).getAuthor();

        if (login.equals(dbUser.getLogin())) {
            model.addAttribute("user", dbUser);
            model.addAttribute("followers", userService.getNumberOfFollowers(id));
            model.addAttribute("following", userService.getNumberOfFollowings(id));
            model.addAttribute("postId", id);
            return "post-likes-acc";
        }

        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(id));
        model.addAttribute("following", userService.getNumberOfFollowings(id));
        model.addAttribute("postId", id);
        return "post-likes";
    }

    @RequestMapping(value = "/user-list/likes/{post-id}/{from}")
    @ResponseBody
    public String[] getUsers(@PathVariable("post-id") long postId, @PathVariable("from") int from) {
        List<CustomUser> users = userService.byLikedPosts(postId, from);
        String[] response = new String[users.size()];

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser self = userService.getUserByLogin(login);

        int counter = 0;
        for (CustomUser temp : users) {
            response[counter] = utilsService.createFragmentUser(temp, self);
            counter++;
        }

        return response;
    }
}
