package com.raveleen.services;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Dialog;
import com.raveleen.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Святослав on 11.01.2017.
 */
@Service
public class UtilsService {
    @Autowired
    private UserService userService;

    @Autowired
    private DialogService dialogService;

    @Autowired
    private MessageService messageService;

    public boolean isWord(String s) {
        boolean flag = true;
        for (char a : s.toCharArray()) {
            if (!((a >= 65 && a <= 90) || (a >= 97 && a <= 122))) {
                flag = false;
            }
        }
        return flag;
    }

    public String createFragmentUser(CustomUser customUser, CustomUser self) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"" + customUser.getId() + "\" class=\"search appended-result\"><div class=\"row search-result\"><div class=\"col-sm-2\"><div>");
        if (customUser.getProfileImage() == null) {
            sb.append("<img class=\"profile-userpic-small centered-and-cropped\" src=\"/images/default-user-image.png\">");
        } else {
            sb.append("<img class=\"profile-userpic-small centered-and-cropped\" src=\"/profile-image/")
                    .append(customUser.getProfileImage().getId()).append("\">");
        }
        sb.append("</div></div><div class=\"col-sm-6\"><div class=\"profile-usertitle-small\"><div class=\"profile-usertitle-name-small\"><p class=\"user-name\"><a class=\"user-name\" href=\"/user/");
        sb.append(customUser.getId()).append("\">").append(customUser.getLogin());
        sb.append("</a></p></div></div></div><div class=\"col-sm-2\"><div class=\"profile-userbuttons\"><div class=\"calendar-href\"><a href=\"/calendar/").append(customUser.getId());
        if (customUser.getId() == self.getId()) {
            sb.append("\"><button type=\"submit\" class=\"btn btn-primary btn-md btn-block button-calendar\"><span class=\"glyphicon glyphicon-th-large glyphicon\"></span></button></a></div></div></div><div class=\"col-sm-2\"><div class=\"message-href\" class=\"\"><button id=\"button-message\" type=\"submit\" class=\"btn btn-primary btn-md btn-block\" disabled><span class=\"glyphicon glyphicon-remove\"></span></button></div></div></div><hr class=\"middle\"></div>");
        } else {
            sb.append("\"><button type=\"submit\" class=\"btn btn-primary btn-md btn-block button-calendar\"><span class=\"glyphicon glyphicon-th-large glyphicon\"></span></button></a></div></div></div><div class=\"col-sm-2\"><div class=\"message-href\" class=\"\"><a href=\"/message-to/");
            sb.append(customUser.getId());
            sb.append("\"><button id=\"button-message\" type=\"submit\" class=\"btn btn-primary btn-md btn-block\"><span class=\"glyphicon glyphicon-envelope\"></span></button></a></div></div></div><hr class=\"middle\"></div>");
        }
        return sb.toString();
    }

    public String createFragmentDialog(Dialog dialog, CustomUser self, CustomUser secondUser) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMMMM dd, yyyy HH:mm:ss", Locale.US);
        StringBuilder sb = new StringBuilder();
        sb.append("<div id=\"" + dialog.getId() + "\" onclick=\"location.href='/message-to/" + secondUser.getId() + "'\" class=\"search appended-result\"><div class=\"row search-result dialogs\"><div class=\"col-sm-2\"><div>");
        if (secondUser.getProfileImage() == null) {
            sb.append("<img class=\"profile-userpic-small centered-and-cropped\" src=\"/images/default-user-image.png\">");
        } else {
            sb.append("<img class=\"profile-userpic-small centered-and-cropped\" src=\"/profile-image/")
                    .append(secondUser.getProfileImage().getId()).append("\">");
        }
        sb.append("</div></div><div class=\"col-sm-6\"><div class=\"col-sm-12\"><div class=\"profile-usertitle-small\"><div class=\"profile-usertitle-name-small-dialog\"><p class=\"user-name\"><a class=\"user-name\" href=\"/user/");
        sb.append(secondUser.getId()).append("\">").append(secondUser.getLogin());
        sb.append("</a></p></div></div></div><div class=\"col-sm-12\">");
        sb.append("<p class=\"post-body-date-dialog\">");
        if (messageService.getNumberOfUnreadMessages(dialog.getId(), secondUser.getId()) != 0) {
            sb.append("<span class=\"pink\">You have " + messageService.getNumberOfUnreadMessages(dialog.getId(), secondUser.getId()) + " unread mesages</span>");
        } else if (dialog.getLastMessageDate().getTime() == dialog.getCreateDate().getTime()) {
            sb.append("There is no messages yet");
        } else {
            sb.append("Last message " + simpleDateFormat.format(dialog.getLastMessageDate()));
        }
        sb.append("</p>");
        sb.append("</div></div><div class=\"col-sm-4\"></div><div class=\"row search-result\"></div><hr class=\"middle\"></div>");
        return sb.toString();
    }

    public String createFragmentMessage(Message message, CustomUser self) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMMMM dd, yyyy HH:mm:ss", Locale.US);
        CustomUser second = message.getFrom();

        if ((second.getId() != self.getId()) && (!message.isread())) {
            sb.append("<div class=\"unread-message comment appended-result row\" id=\"").append(message.getId()).append("\">");
            message.setIsread(true);
            message = messageService.addMessage(message);
        } else {
            sb.append("<div id=\"").append(message.getId());
            sb.append("\" class=\"comment appended-result row\">");
        }
        sb.append("<div class=\"col-sm-3 profile-usertitle-small\"><div class=\"col-sm-12\"><div class=\"profile-usertitle-name-small\"><p class=\"user-name\"><a class=\"user-name\" href=\"/user/")
                .append(message.getFrom().getId())
                .append("\">")
                .append(message.getFrom().getLogin())
                .append("</a></p></div></div><div class=\"col-sm-12\">");
        sb.append("<p class=\"post-body-date\">");
        sb.append(simpleDateFormat.format(message.getCreateDate()));
        sb.append("</p>");
        sb.append("</div></div>")
                .append("<div class=\"col-sm-9\"><p class=\"post-comment-text\">")
                .append(message.getText())
                .append("</p>")
                .append("</div>");
        sb.append("</div>");
        return sb.toString();
    }
}
