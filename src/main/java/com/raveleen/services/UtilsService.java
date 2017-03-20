package com.raveleen.services;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Dialog;
import com.raveleen.entities.Event;
import com.raveleen.entities.Message;
import com.raveleen.entities.Post;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.raveleen.entities.UserRate;
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

    @Autowired
    private UserRateService userRateService;

    @Autowired
    private EventService eventService;

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
        if (posts.size() == 0) {
            return null;
        }
        String[][] storage = new String[posts.size()][11];
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

    public String[][] arrayUserFill(List<CustomUser> users, CustomUser customUser) {
        if (users.size() == 0) {
            return null;
        }
        String[][] storage = new String[users.size()][4];
        int counter = 0;
        for (CustomUser temp : users) {
            storage[counter][0] = String.valueOf(temp.getId());
            if (temp.getProfileImage() == null) {
                storage[counter][1] = String.valueOf(-1);
            } else {
                storage[counter][1] = String.valueOf(temp.getProfileImage().getId());
            }
            storage[counter][2] = temp.getLogin();
            if (temp.getId() == customUser.getId()) {
                storage[counter][3] = String.valueOf(1);
            } else {
                storage[counter][3] = String.valueOf(-1);
            }
            counter += 1;
        }
        return storage;
    }

    public String[][] arrayMessageFill(List<Message> messages, CustomUser customUser, Dialog dialog) {
        if (messages.size() == 0) {
            return null;
        }
        String[][] storage = new String[messages.size() + 1][6];
        int counter = 1;
        storage[0][0] = String.valueOf(dialog.getLastMessageDate().getTime());
        for (Message temp : messages) {
            CustomUser second = temp.getFrom();
            if ((second.getId() != customUser.getId()) && (!temp.isread())) {
                temp.setIsread(true);
                temp = messageService.addMessage(temp);
                storage[counter][0] = String.valueOf(-1);
            } else {
                storage[counter][0] = String.valueOf(1);
            }
            storage[counter][1] = String.valueOf(temp.getId());
            storage[counter][2] = String.valueOf(temp.getFrom().getId());
            storage[counter][3] = temp.getFrom().getLogin();
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("EEE, MMMMM dd, yyyy HH:mm:ss", Locale.US);
            storage[counter][4] = String.valueOf(simpleDateFormat.format(temp.getCreateDate()));
            storage[counter][5] = temp.getText();
            counter += 1;
        }
        return storage;
    }

    public String[][] arrayEventFill(List<Event> events, CustomUser customUser) {
        if (events.size() == 0) {
            return null;
        }
        String[][] storage = new String[events.size()][9];
        int counter = 0;
        for (Event temp : events) {
            storage[counter][0] = String.valueOf(temp.getId());
            storage[counter][1] = String.valueOf(temp.getHost().getId());
            storage[counter][2] = temp.getHost().getLogin();
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("HH:mm, EEE dd MMMMM, yyyy", Locale.US);
            storage[counter][3] = String.valueOf(simpleDateFormat.format(temp.getEventDate()));
            storage[counter][4] = String.valueOf(temp.getAddress().getPlaceId());
            UserRate userRate = userRateService.getByIdAndUserId(temp.getId(), customUser.getId());
            if (customUser.getId() == temp.getHost().getId()) {
                storage[counter][5] = "0";
            } else if (userRate == null) {
                storage[counter][5] = "1";
            } else if (userRate.getMark() == 0) {
                storage[counter][5] = "2";
            } else {
                storage[counter][5] = "3";
            }
            if (temp.getHost().getProfileImage() == null) {
                storage[counter][6] = "-1";
            } else {
                storage[counter][6] = String.valueOf(temp.getHost().getProfileImage().getId());
            }
            storage[counter][7] = temp.getTitle();
            storage[counter][8] = temp.getInfo();
            counter += 1;
        }
        return storage;
    }
}
