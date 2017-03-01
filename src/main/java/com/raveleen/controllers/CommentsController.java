package com.raveleen.controllers;

import com.raveleen.entities.Comment;
import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Post;
import com.raveleen.services.CommentService;
import com.raveleen.services.PostService;
import com.raveleen.services.UserService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Святослав on 01.02.2017.
 */
@Controller
public class CommentsController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/post/comments-number/{id}")
    @ResponseBody
    public String commentsNumber(@PathVariable("id") long id) {
        String response;
        response = "" + postService.getNumberOfComments(id);
        return response;
    }

    @RequestMapping(value = "/comment-create/{post-id}", method = RequestMethod.POST)
    @ResponseBody
    public String createComment(@RequestParam("comment-text") String text,
                                @PathVariable("post-id") long postId) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        Post post = postService.getById(postId);
        Comment comment = new Comment(customUser, text, post);
        post.addComment(comment);
        commentService.addComment(comment);
        postService.addPost(post);

        return createCommentFragment(comment, customUser.getId());
    }

    @RequestMapping(value = "/get-comments/{post-id}/{from}")
    @ResponseBody
    public String[] getComments(@PathVariable("post-id") long postId, @PathVariable("from") int from) {
        List<Comment> comments = commentService.findByPostIdOrderByCreateDate(postId, from);
        String[] response = new String[comments.size()];

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        int counter = 0;
        //If user gets his own comment, he gets possibility to delete this post.
        for (Comment temp : comments) {
            response[counter] = createCommentFragment(temp, customUser.getId());
            counter++;
        }

        return response;
    }

    @RequestMapping(value = "/comment-delete/comment-{comment-id}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteComment(@PathVariable("comment-id") long commentId) throws IOException {
        commentService.delete(commentId);

        return "deleted";
    }

    private String createCommentFragment(Comment comment, long customUserId) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"comment-").append(comment.getId());
        sb.append("\" class=\"comment appended-result row\">")
                .append("<div class=\"col-sm-3 profile-usertitle-small\">")
                .append("<div class=\"col-sm-12\">")
                .append("<div class=\"profile-usertitle-name-small\">")
                .append("<p class=\"user-name\">")
                .append("<a class=\"user-name\" href=\"/user/")
                .append(comment.getFrom().getId())
                .append("\">")
                .append(comment.getFrom().getLogin())
                .append("</a></p></div></div>")
                .append("<div class=\"col-sm-12\">");
        sb.append("<p class=\"post-body-date\">");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd, HH:mm:ss", Locale.US);
        sb.append(simpleDateFormat.format(comment.getCreateDate()))
                .append("</p></div></div>")
                .append("<div class=\"col-sm-8\">")
                .append("<p class=\"post-comment-text\">")
                .append(comment.getText())
                .append("</p></div>");
        if (comment.getFrom().getId() == customUserId) {
            sb.append("<div class=\"col-sm-1\">")
                    .append("<div class=\"delete-button-div\">")
                    .append("<a class=\"delete-button-comment\">")
                    .append("<span class=\"glyphicon glyphicon-remove\">")
                    .append("</span></a></div></div>");
        } else {
            sb.append("<div class=\"col-sm-1\">")
                    .append("<div class=\"delete-button-div\">")
                    .append("</div></div>");
        }
        return sb.toString();
    }
}
