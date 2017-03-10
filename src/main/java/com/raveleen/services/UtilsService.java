package com.raveleen.services;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Message;
import com.raveleen.entities.Post;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Святослав on 11.01.2017.
 */
@Service
public class UtilsService {
    @Autowired
    private PostService postService;

    @Autowired
    private MessageService messageService;

    public boolean isWord(String temp) {
        boolean flag = true;
        for (char a : temp.toCharArray()) {
            if (!((a >= 65 && a <= 90) || (a >= 97 && a <= 122))) {
                flag = false;
            }
        }
        return flag;
    }

    public String[][] arrayFill(List<Post> posts, CustomUser customUser) {
        String[][] storage = new String[10][11];
        int counter = 0;
        for (Post temp : posts) {
            storage[counter][0] = String.valueOf(temp.getId());
            if (temp.getAuthor().getProfileImage() == null) {
                storage[counter][1] = "-1";
            } else {
                storage[counter][1] = String.valueOf(temp.getAuthor().getProfileImage().getId());
            }
            storage[counter][2] = String.valueOf(temp.getAuthor().getId());
            storage[counter][3] = String.valueOf(temp.getAuthor().getLogin());
            storage[counter][4] = String.valueOf(temp.getImage().getId());
            storage[counter][5] = String.valueOf(temp.getText());
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("EEE, MMMMM dd, yyyy HH:mm:ss", Locale.US);
            storage[counter][6] = String.valueOf(simpleDateFormat.format(temp.getCreateDate()));
            storage[counter][7] =
                    String.valueOf(postService.isLiked(customUser.getId(), temp.getId()));
            storage[counter][8] = String.valueOf(postService.getNumberOfLikes(temp.getId()));
            storage[counter][9] = String.valueOf(postService.getNumberOfComments(temp.getId()));
            if (customUser.getId() == temp.getAuthor().getId()) {
                storage[counter][10] = String.valueOf(1);
            } else {
                storage[counter][10] = String.valueOf(-1);
            }
            counter += 1;
        }
        return storage;
    }

    public String createFragmentUser(CustomUser customUser, CustomUser self) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"")
                .append(customUser.getId())
                .append("\" class=\"search appended-result\">")
                .append("<div class=\"row search-result\">")
                .append("<div class=\"col-sm-2\"><div>");
        if (customUser.getProfileImage() == null) {
            sb.append("<img class=\"profile-userpic-small centered-and-cropped\" ")
                    .append("src=\"/images/default-user-image.png\">");
        } else {
            sb.append("<img class=\"profile-userpic-small centered-and-cropped\" ")
                    .append("src=\"/profile-image/")
                    .append(customUser.getProfileImage().getId())
                    .append("\">");
        }
        sb.append("</div></div>")
                .append("<div class=\"col-sm-6\">")
                .append("<div class=\"profile-usertitle-small\">")
                .append("<div class=\"profile-usertitle-name-small\">")
                .append("<p class=\"user-name\">")
                .append("<a class=\"user-name\" href=\"/user/")
                .append(customUser.getId())
                .append("\">")
                .append(customUser.getLogin())
                .append("</a></p></div></div></div>")
                .append("<div class=\"col-sm-2\">")
                .append("<div class=\"profile-userbuttons\">")
                .append("<div class=\"calendar-href\">")
                .append("<a href=\"/calendar/")
                .append(customUser.getId());
        if (customUser.getId() == self.getId()) {
            sb.append("\">")
                    .append("<button type=\"submit\" ")
                    .append("class=\"btn btn-primary btn-md btn-block button-calendar\">")
                    .append("<span class=\"glyphicon glyphicon-th-large glyphicon\">")
                    .append("</span></button></a></div></div></div>")
                    .append("<div class=\"col-sm-2\">")
                    .append("<div class=\"message-href\" class=\"\">")
                    .append("<button id=\"button-message\" type=\"submit\" ")
                    .append("class=\"btn btn-primary btn-md btn-block\" disabled>")
                    .append("<span class=\"glyphicon glyphicon-remove\">")
                    .append("</span></button></div></div></div>")
                    .append("<hr class=\"middle\">")
                    .append("</div>");
        } else {
            sb.append("\">")
                    .append("<button type=\"submit\" ")
                    .append("class=\"btn btn-primary btn-md btn-block button-calendar\">")
                    .append("<span class=\"glyphicon glyphicon-th-large glyphicon\">")
                    .append("</span></button></a></div></div></div>")
                    .append("<div class=\"col-sm-2\">")
                    .append("<div class=\"message-href\" class=\"\">")
                    .append("<a href=\"/message-to/")
                    .append(customUser.getId())
                    .append("\">")
                    .append("<button id=\"button-message\" type=\"submit\" ")
                    .append("class=\"btn btn-primary btn-md btn-block\">")
                    .append("<span class=\"glyphicon glyphicon-envelope\">")
                    .append("</span></button></a></div></div></div>")
                    .append("<hr class=\"middle\">")
                    .append("</div>");
        }
        return sb.toString();
    }

    public String createFragmentMessage(Message message, CustomUser self) {
        StringBuilder sb = new StringBuilder();
        CustomUser second = message.getFrom();

        if ((second.getId() != self.getId()) && (!message.isread())) {
            sb.append("<div class=\"unread-message comment appended-result row\" id=\"")
                    .append(message.getId())
                    .append("\">");
            message.setIsread(true);
            message = messageService.addMessage(message);
        } else {
            sb.append("<div id=\"")
                    .append(message.getId())
                    .append("\" class=\"comment appended-result row\">");
        }
        sb.append("<div class=\"col-sm-3 profile-usertitle-small\">")
                .append("<div class=\"col-sm-12\">")
                .append("<div class=\"profile-usertitle-name-small\">")
                .append("<p class=\"user-name\">")
                .append("<a class=\"user-name\" href=\"/user/")
                .append(message.getFrom().getId())
                .append("\">")
                .append(message.getFrom().getLogin())
                .append("</a></p></div></div>")
                .append("<div class=\"col-sm-12\">")
                .append("<p class=\"post-body-date\">");
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EEE, MMMMM dd, yyyy HH:mm:ss", Locale.US);
        sb.append(simpleDateFormat.format(message.getCreateDate()))
                .append("</p></div></div>")
                .append("<div class=\"col-sm-9\">")
                .append("<p class=\"post-comment-text\">")
                .append(message.getText())
                .append("</p></div></div>");
        return sb.toString();
    }
}
