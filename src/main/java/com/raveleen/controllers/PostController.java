package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Image;
import com.raveleen.entities.Post;
import com.raveleen.services.ImageService;
import com.raveleen.services.PostService;
import com.raveleen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Святослав on 31.12.2016.
 */
@Controller
public class PostController {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/post-create", method = RequestMethod.POST)
    @ResponseBody
    public String createPost(@RequestParam("text") String text, @RequestParam(value = "photo") MultipartFile body) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        Post post = new Post();
        if (body.getBytes() != null) {
            Image image = new Image(body.getBytes());
            imageService.addImage(image);
            post.setAuthor(customUser);
            post.setText(text);
            post.setImage(image);
            postService.addPost(post);
        } else {
            post.setAuthor(customUser);
            post.setText(text);
            postService.addPost(post);
        }

        return createFragment(post, customUser.getId(), customUser.getId());
    }

    @RequestMapping(value = "/post-delete/{post-id}", method = RequestMethod.POST)
    @ResponseBody
    public String deletePost(@PathVariable("post-id") long postId) throws IOException {
        postService.deletePostById(postId);

        return "deleted";
    }

    @RequestMapping(value = "/get-posts/{user-id}/{from}")
    @ResponseBody
    public String[] getPosts(@PathVariable("user-id") long userId, @PathVariable("from") int from) {
        List<Post> posts = postService.findByAuthorIdOrderByCreateDateDesc(userId, from);
        String[] response = new String[posts.size()];

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        int i = 0;
        //If user gets his own posts, he gets possibility to delete this post.
        for (Post temp : posts) {
            response[i] = createFragment(temp, customUser.getId(), userId);
            i++;
        }

        return response;
    }

    private String createFragment(Post temp, long customUserId, long userId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMMMM dd, yyyy HH:mm:ss", Locale.US);
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"")
                .append(temp.getId())
                .append("\" class=\"post row\"><div class=\"post-header row\"><div class=\"col-sm-2\"><div>");
        if (temp.getAuthor().getProfileImage() == null) {
            sb.append("<img class=\"profile-userpic-small centered-and-cropped\" src=\"/images/default-user-image.png\">");
        } else {
            sb.append("<img class=\"profile-userpic-small centered-and-cropped\" src=\"/profile-image/")
                    .append(temp.getAuthor().getProfileImage().getId())
                    .append("\">");
        }
        sb.append("</div></div><div class=\"col-sm-9\"><div class=\"profile-usertitle-small\"><div class=\"profile-usertitle-name-small\"><p class=\"user-name\"><a class=\"user-name\" href=\"/user/")
                .append(temp.getAuthor().getId())
                .append("\">")
                .append(temp.getAuthor().getLogin());
        if (userId == customUserId) {
            sb.append("</a></p></div></div></div><div class=\"col-sm-1\"><div class=\"delete-button-div\"><a class=\"delete-button\"><span class=\"glyphicon glyphicon-remove\"></span></a></div></div></div>");
        } else {
            sb.append("</a></p></div></div></div><div class=\"col-sm-1\"><div class=\"delete-button-div\"></div></div></div>");
        }
        sb.append("<div class=\"post-body row\">");
        sb.append("<div class=\"col-sm-12 post-body-img-div\">");
        if (temp.getImage() != null) {
            sb.append("<img class=\"post-userpic centered-and-cropped\" src=\"/image/" + temp.getImage().getId() + "\">");
        }
        sb.append("</div>");
        sb.append("<div class=\"col-sm-12 post-body-text-div\">");
        sb.append("<p class=\"post-body-text\">");
        sb.append(temp.getText());
        sb.append("</p></br>");
        sb.append("<p class=\"post-body-date\">");
        sb.append(simpleDateFormat.format(temp.getCreateDate()));
        sb.append("</p>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<div class=\"post-footer row\">");
        if (!postService.isLiked(customUserId, temp.getId())) {
            sb.append("<div class=\"col-sm-6\"><div class=\"like-button-div\"><a class=\"like-button\"><span class=\"glyphicon glyphicon-heart\"></span></a><a class=\"unlike-button hidden\"><span class=\"glyphicon glyphicon-heart\"></span></a><a href=\"/post-likes/" + temp.getId() + "\">  <span class=\"likes-number\">");
        } else {
            sb.append("<div class=\"col-sm-6\"><div class=\"like-button-div\"><a class=\"like-button hidden\"><span class=\"glyphicon glyphicon-heart\"></span></a><a class=\"unlike-button\"><span class=\"glyphicon glyphicon-heart\"></span></a><a href=\"/post-likes/" + temp.getId() + "\">  <span class=\"likes-number\">");
        }
        sb.append(postService.getNumberOfLikes(temp.getId()))
                .append("</span></a></div></div>");
        sb.append("<div class=\"col-sm-6\">");
        sb.append("<div class=\"comment-quantity-div\"><a class=\"comment-button\"><p class=\"\"><span class=\"comments-number\">")
                .append(postService.getNumberOfComments(temp.getId()))
                .append("</span> comments.<p></a></div></div>");
        sb.append("<div class=\"post-comments row hidden\">")
                .append("<div class=\"comment-container\">").append("</div>")
                .append("<form id=\"form-" + temp.getId() + "\" enctype=\"multipart/form-data\" class=\"create-comment-form\" method=\"POST\">")
                .append("<div class=\"form-group\"><div class=\"col-sm-12\">")
                .append("<textarea class=\"form-control comment-text\" minlength=\"20\" maxlength=\"500\" rows=\"2\" name=\"comment-text\"></textarea>")
                .append("</div></div><div class=\"form-group\"><div class=\"col-sm-12\">")
                .append("<button disabled type=\"button\" \" class=\"create-comment-button btn btn-primary btn-md btn-block\">Comment it</button>")
                .append("</div></div></form>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        return sb.toString();
    }
}
