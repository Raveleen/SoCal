package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Image;
import com.raveleen.entities.Post;
import com.raveleen.services.ImageService;
import com.raveleen.services.PostService;
import com.raveleen.services.UserService;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
    public String createPost(@RequestParam("text") String text,
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

        return createFragment(post, customUser.getId(), customUser.getId());
    }

    @RequestMapping(value = "/post-delete/{post-id}", method = RequestMethod.POST)
    @ResponseBody
    public String deletePost(@PathVariable("post-id") long postId) throws IOException {
        /*User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        customUser.removePost(postService.getById(postId));
        userService.updateUser(customUser);*/
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

    private String createFragment(Post temp, long customUserId, long userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"")
                .append(temp.getId())
                .append("\" class=\"post row\">")
                .append("<div class=\"post-header row\">")
                .append("<div class=\"col-sm-2\">")
                .append("<div>");
        if (temp.getAuthor().getProfileImage() == null) {
            sb.append("<img class=\"profile-userpic-small ")
                    .append("centered-and-cropped\" src=\"/images/default-user-image.png\">");
        } else {
            sb.append("<img class=\"profile-userpic-small ")
                    .append("centered-and-cropped\" src=\"/profile-image/")
                    .append(temp.getAuthor().getProfileImage().getId())
                    .append("\">");
        }
        sb.append("</div></div>")
                .append("<div class=\"col-sm-9\">")
                .append("<div class=\"profile-usertitle-small\">")
                .append("<div class=\"profile-usertitle-name-small\">")
                .append("<p class=\"user-name\">")
                .append("<a class=\"user-name\" href=\"/user/")
                .append(temp.getAuthor().getId())
                .append("\">")
                .append(temp.getAuthor().getLogin());
        if (userId == customUserId) {
            sb.append("</a></p></div></div></div>")
                    .append("<div class=\"col-sm-1\">")
                    .append("<div class=\"delete-button-div\">")
                    .append("<a class=\"delete-button\">")
                    .append("<span class=\"glyphicon glyphicon-remove\">")
                    .append("</span></a></div></div></div>");
        } else {
            sb.append("</a></p></div></div></div>")
                    .append("<div class=\"col-sm-1\">")
                    .append("<div class=\"delete-button-div\">")
                    .append("</div></div></div>");
        }
        sb.append("<div class=\"post-body row\">")
                .append("<div class=\"col-sm-12 post-body-img-div\">");
        if (temp.getImage() != null) {
            sb.append("<img class=\"post-userpic centered-and-cropped\" src=\"/image/")
                    .append(temp.getImage().getId())
                    .append("\">");
        }
        sb.append("</div>");
        sb.append("<div class=\"col-sm-12 post-body-text-div\">");
        sb.append("<p class=\"post-body-text\">");
        sb.append(temp.getText());
        sb.append("</p></br>");
        sb.append("<p class=\"post-body-date\">");
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EEE, MMMMM dd, yyyy HH:mm:ss", Locale.US);
        sb.append(simpleDateFormat.format(temp.getCreateDate()));
        sb.append("</p>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<div class=\"post-footer row\">");
        if (!postService.isLiked(customUserId, temp.getId())) {
            sb.append("<div class=\"col-sm-6\">")
                    .append("<div class=\"like-button-div\">")
                    .append("<a class=\"like-button\">")
                    .append("<span class=\"glyphicon glyphicon-heart\">")
                    .append("</span></a>")
                    .append("<a class=\"unlike-button hidden\">")
                    .append("<span class=\"glyphicon glyphicon-heart\">")
                    .append("</span></a>")
                    .append("<a href=\"/post-likes/")
                    .append(temp.getId())
                    .append("\">")
                    .append("<span class=\"likes-number\">");
        } else {
            sb.append("<div class=\"col-sm-6\">")
                    .append("<div class=\"like-button-div\">")
                    .append("<a class=\"like-button hidden\">")
                    .append("<span class=\"glyphicon glyphicon-heart\">")
                    .append("</span></a>")
                    .append("<a class=\"unlike-button\">")
                    .append("<span class=\"glyphicon glyphicon-heart\">")
                    .append("</span></a>")
                    .append("<a href=\"/post-likes/")
                    .append(temp.getId())
                    .append("\">")
                    .append("<span class=\"likes-number\">");
        }
        sb.append(postService.getNumberOfLikes(temp.getId()))
                .append("</span></a></div></div>");
        sb.append("<div class=\"col-sm-6\">");
        sb.append("<div class=\"comment-quantity-div\">")
                .append("<a class=\"comment-button\">")
                .append("<p class=\"\"><span class=\"comments-number\">")
                .append(postService.getNumberOfComments(temp.getId()))
                .append("</span> comments.<p>")
                .append("</a></div></div>");
        sb.append("<div class=\"post-comments row hidden\">")
                .append("<div class=\"comment-container\">")
                .append("</div>")
                .append("<form id=\"form-")
                .append(temp.getId())
                .append("\" enctype=\"multipart/form-data\"")
                .append("class=\"create-comment-form\" method=\"POST\">")
                .append("<div class=\"form-group\">")
                .append("<div class=\"col-sm-12\">")
                .append("<textarea class=\"form-control comment-text\"")
                .append("minlength=\"20\" maxlength=\"500\" rows=\"2\" name=\"comment-text\">")
                .append("</textarea></div></div>")
                .append("<div class=\"form-group\">")
                .append("<div class=\"col-sm-12\">")
                .append("<button disabled type=\"button\" ")
                .append("class=\"create-comment-button btn btn-primary btn-md btn-block\">")
                .append("Comment it")
                .append("</button></div></div></form>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        return sb.toString();
    }
}
