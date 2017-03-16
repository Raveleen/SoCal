package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Image;
import com.raveleen.entities.Post;
import com.raveleen.services.ImageService;
import com.raveleen.services.PostService;
import com.raveleen.services.UserService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

/**
 * Created by Святослав on 31.12.2016.
 */
@Controller
public class PostController {
    @Autowired
    private UtilsService utilsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/post-create", method = RequestMethod.POST)
    @ResponseBody
    public String[][] createPost(@RequestParam("text") String text,
                             @RequestParam(value = "photo") MultipartFile body) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        Image image = new Image(body.getBytes());
        imageService.addImage(image);
        Post post = new Post();
        post.setAuthor(customUser);
        post.setText(text);
        post.setImage(image);
        postService.addPost(post);

        post = postService.addPost(post);
        List<Post> posts = new ArrayList<>();
        posts.add(post);

        return utilsService.arrayFill(posts, customUser);
    }

    @RequestMapping(value = "/post-delete/{post-id}", method = RequestMethod.POST)
    @ResponseBody
    public String deletePost(@PathVariable("post-id") long postId) throws IOException {
        postService.deletePostById(postId);

        return "deleted";
    }

    @RequestMapping(value = "/get-posts/{user-id}/{from}")
    @ResponseBody
    public String[][] getPosts(@PathVariable("user-id") long userId, @PathVariable("from") int from) {
        List<Post> posts = postService.findByAuthorIdOrderByCreateDateDesc(userId, from);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        String[][] storage = utilsService.arrayFill(posts, customUser);
        return storage;
    }
}
