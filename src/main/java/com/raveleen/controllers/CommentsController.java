package com.raveleen.controllers;

import com.raveleen.entities.Comment;
import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Post;
import com.raveleen.services.CommentService;
import com.raveleen.services.ImageService;
import com.raveleen.services.PostService;
import com.raveleen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
        String response = new String();
        response = "" + postService.getNumberOfComments(id);
        return response;
    }

    @RequestMapping(value = "/comment-create/{post-id}", method = RequestMethod.POST)
    @ResponseBody
    public String createComment(@RequestParam("comment-text") String text, @PathVariable("post-id") long postId) throws IOException {
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

        int i = 0;
        //If user gets his own comment, he gets possibility to delete this post.
        for (Comment temp : comments) {
            response[i] = createCommentFragment(temp, customUser.getId());
            i++;
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd, HH:mm:ss", Locale.US);
        StringBuilder sb = new StringBuilder();

        sb.append("<div id=\"comment-").append(comment.getId());
        sb.append("\" class=\"comment appended-result row\">")
                .append("<div class=\"col-sm-3 profile-usertitle-small\"><div class=\"col-sm-12\"><div class=\"profile-usertitle-name-small\"><p class=\"user-name\"><a class=\"user-name\" href=\"/user/")
                .append(comment.getFrom().getId())
                .append("\">")
                .append(comment.getFrom().getLogin())
                .append("</a></p></div></div><div class=\"col-sm-12\">");
        sb.append("<p class=\"post-body-date\">");
        sb.append(simpleDateFormat.format(comment.getCreateDate()));
        sb.append("</p>");
        sb.append("</div></div>")
                .append("<div class=\"col-sm-8\"><p class=\"post-comment-text\">")
                .append(comment.getText())
                .append("</p>")
                .append("</div>");
        if (comment.getFrom().getId() == customUserId) {
            sb.append("<div class=\"col-sm-1\"><div class=\"delete-button-div\"><a class=\"delete-button-comment\"><span class=\"glyphicon glyphicon-remove\"></span></a></div>");
        } else {
            sb.append("<div class=\"col-sm-1\"><div class=\"delete-button-div\"></div>");
        }
        sb.append("</div>");
        return sb.toString();
    }
}
