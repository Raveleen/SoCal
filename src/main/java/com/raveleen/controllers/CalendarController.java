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
    public String[] getFollowingPosts(@PathVariable("user-id") long userId,
                                      @PathVariable("from") int from) {
        List<Post> posts = postService.getFollowingsPosts(userId, from);
        String[] response = new String[posts.size()];

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        int i = 0;
        for (Post temp : posts) {
            response[i] = createFragment(temp, customUser.getId(), userId);
            i++;
        }

        return response;
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

    private String createFragment(Post temp, long customUserId, long userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"")
                .append(temp.getId())
                .append("\" class=\"post row\">")
                .append("<div class=\"post-header row\">")
                .append("<div class=\"col-sm-2\">")
                .append("<div>");
        if (temp.getAuthor().getProfileImage() == null) {
            sb.append("<img class=\"profile-userpic-small centered-and-cropped\"")
                    .append(" src=\"/images/default-user-image.png\">");
        } else {
            sb.append("<img class=\"profile-userpic-small centered-and-cropped\"")
                    .append("src=\"/profile-image/")
                    .append(temp.getAuthor().getProfileImage().getId())
                    .append("\">");
        }
        sb.append("</div></div>")
                .append("<div class=\"col-sm-9\">")
                .append("<div class=\"profile-usertitle-small\">")
                .append("<div class=\"profile-usertitle-name-small\">")
                .append("<p class=\"user-name\"><a class=\"user-name\" href=\"/user/")
                .append(temp.getAuthor().getId())
                .append("\">")
                .append(temp.getAuthor().getLogin());
        if (userId == customUserId) {
            sb.append("</a></p></div></div></div>")
                    .append("<div class=\"col-sm-1\">")
                    .append("</div></div>");
        } else {
            sb.append("</a></p></div></div></div>")
                    .append("<div class=\"col-sm-1\">")
                    .append("</div></div>");
        }
        sb.append("<div class=\"post-body row\">");
        sb.append("<div class=\"col-sm-12 post-body-img-div\">");
        if (temp.getImage() != null) {
            sb.append("<img class=\"post-userpic centered-and-cropped\" src=\"/image/")
                    .append(temp.getImage().getId())
                    .append("\">");
        }
        sb.append("</div>")
                .append("<div class=\"col-sm-12 post-body-text-div\">")
                .append("<p class=\"post-body-text\">")
                .append(temp.getText())
                .append("</p></br>")
                .append("<p class=\"post-body-date\">");
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EEE, MMMMM dd, yyyy HH:mm:ss", Locale.US);
        sb.append(simpleDateFormat.format(temp.getCreateDate()))
                .append("</p></div></div>")
                .append("<div class=\"post-footer row\">");
        if (!postService.isLiked(customUserId, temp.getId())) {
            sb.append("<div class=\"col-sm-6\"><div class=\"like-button-div\">")
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
                    .append("</span></a><a href=\"/post-likes/")
                    .append(temp.getId())
                    .append("\">")
                    .append("<span class=\"likes-number\">");
        }
        sb.append(postService.getNumberOfLikes(temp.getId()))
                .append("</span></a></div></div>");
        sb.append("<div class=\"col-sm-6\">");
        sb.append("<div class=\"comment-quantity-div\">")
                .append("<a class=\"comment-button\">")
                .append("<p class=\"\">")
                .append("<span class=\"comments-number\">")
                .append(postService.getNumberOfComments(temp.getId()))
                .append("</span> comments.<p></a></div></div>");
        sb.append("<div class=\"post-comments row hidden\">")
                .append("<div class=\"comment-container\">").append("</div>")
                .append("<form id=\"form-")
                .append(temp.getId())
                .append("\" enctype=\"multipart/form-data\" class=\"create-comment-form\" method=\"POST\">")
                .append("<div class=\"form-group\">")
                .append("<div class=\"col-sm-12\">")
                .append("<textarea class=\"form-control comment-text\" minlength=\"20\" maxlength=\"500\" rows=\"2\" name=\"comment-text\">")
                .append("</textarea></div></div>")
                .append("<div class=\"form-group\">")
                .append("<div class=\"col-sm-12\">")
                .append("<button type=\"button\" \" class=\"create-comment-button btn btn-primary btn-md btn-block\">Comment it</button>")
                .append("</div></div></form></div></div></div>");
        return sb.toString();
    }
}
