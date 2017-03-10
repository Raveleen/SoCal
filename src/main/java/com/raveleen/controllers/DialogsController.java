package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Dialog;
import com.raveleen.entities.Message;
import com.raveleen.services.DialogService;
import com.raveleen.services.MessageService;
import com.raveleen.services.UserService;
import com.raveleen.services.UtilsService;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Created by Святослав on 31.12.2016.
 */
@Controller
public class DialogsController {
    @Autowired
    private UserService userService;

    @Autowired
    private DialogService dialogService;

    @Autowired
    private UtilsService utilsService;

    @Autowired
    private MessageService messageService;

    @RequestMapping("/dialogs")
    public String dialogsList(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserByLogin(login);

        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(dbUser.getId()));
        model.addAttribute("following", userService.getNumberOfFollowings(dbUser.getId()));
        return "dialogs";
    }

    @RequestMapping(value = "/message-to/{user-id}")
    public String redirectOrCreateDialog(@PathVariable("user-id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        CustomUser customUserSecond = userService.getUserById(id);

        if (dialogService.isDialogExists(customUser.getId(), customUserSecond.getId())) {
            System.out.println("dialog " + customUser.getId()
                    + customUserSecond.getId() + "exists(true)");
            Dialog temp = dialogService.getDialog(customUser.getId(), customUserSecond.getId());
            return "redirect:/dialog/" + temp.getId();
        } else {
            System.out.println("dialog " + customUser.getId()
                    + customUserSecond.getId() + "don't exists(false)");
            Dialog dialog = new Dialog(customUser, customUserSecond);
            dialogService.addDialog(dialog);
            Dialog temp = dialogService.getDialog(customUser.getId(), customUserSecond.getId());
            temp.setLastMessageDate(temp.getCreateDate());
            dialogService.addDialog(temp);
            return "redirect:/dialog/" + temp.getId();
        }
    }

    @RequestMapping("/dialog/{dialog-id}")
    public String userAccount(Model model, @PathVariable("dialog-id") long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserByLogin(login);
        Dialog dialog = dialogService.getById(id);
        System.out.println(dialog);
        CustomUser us1 = dialog.getUser1();
        CustomUser us2 = dialog.getUser2();
        long us1Id = us1.getId();
        long us2Id = us2.getId();

        if (us1Id == dbUser.getId()) {
            model.addAttribute("dialogUser", us2);
            model.addAttribute("dialogUserFollowers", userService.getNumberOfFollowers(us2Id));
            model.addAttribute("dialogUserFollowing", userService.getNumberOfFollowings(us2Id));
        } else {
            model.addAttribute("dialogUser", us1);
            model.addAttribute("dialogUserFollowers", userService.getNumberOfFollowers(us1Id));
            model.addAttribute("dialogUserFollowing", userService.getNumberOfFollowings(us1Id));
        }
        model.addAttribute("dialog", dialog);
        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(dbUser.getId()));
        model.addAttribute("following", userService.getNumberOfFollowings(dbUser.getId()));
        return "dialog";
    }

    @RequestMapping(value = "/dialogs/{from}")
    @ResponseBody
    public String[][] getDialogsList(@PathVariable("from") int from) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        List<Dialog> dialogs = dialogService.dialogsOrderByDate(customUser.getId(), from);
        String[][] storage = new String[10][6];

        int counter = 0;
        for (Dialog temp : dialogs) {
            CustomUser us1 = temp.getUser1();
            CustomUser us2 = temp.getUser2();
            if (us1.getId() == customUser.getId()) {
                storage[counter] = arrayFill(temp, us2);
            } else {
                storage[counter] = arrayFill(temp, us1);
            }
            counter++;
        }

        return storage;
    }

    private String[] arrayFill(Dialog temp, CustomUser user) {
        String[] storage = new String[6];
        storage[0] = String.valueOf(temp.getId());
        storage[1] = String.valueOf(user.getId());
        if (user.getProfileImage() == null) {
            storage[2] = String.valueOf(-1);
        } else {
            storage[2] = String.valueOf(user.getProfileImage().getId());
        }
        storage[3] = String.valueOf(user.getLogin());
        if (messageService.getNumberOfUnreadMessages(temp.getId(), user.getId()) != 0) {
            storage[4] = String.valueOf(
                    messageService.getNumberOfUnreadMessages(temp.getId(), user.getId()));
        } else if (temp.getLastMessageDate().getTime() == temp.getCreateDate().getTime()) {
            storage[4] = String.valueOf(-1);
        } else {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("EEE, MMMMM dd, yyyy HH:mm:ss", Locale.US);
            storage[5] =
                    String.valueOf(simpleDateFormat.format(temp.getLastMessageDate()));
        }
        return storage;
    }

    @RequestMapping(value = "/messages/{dialog-id}/{from}")
    @ResponseBody
    public String[] getMessagesList(@PathVariable("dialog-id") long dialogId,
                                    @PathVariable("from") int from) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        List<Message> messages = messageService.findByDialogIdOrderByCreateDateDesc(dialogId, from);
        String[] response = new String[messages.size()];

        System.out.println(from);
        int counter = 0;
        for (Message message : messages) {
            System.out.println(message);
            response[counter] = utilsService.createFragmentMessage(message, customUser);
            counter++;
        }

        return response;
    }

    @RequestMapping(value = "/messages-get-unread/{dialog-id}/{time}")
    @ResponseBody
    public String[] getMessagesList(@PathVariable("dialog-id") long dialogId,
                                    @PathVariable("time") long time) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        Dialog dialog = dialogService.getById(dialogId);

        List<Message> messages = messageService.getNewMessages(dialogId, time);
        String[] response = new String[messages.size() + 1];
        response[0] = "" + dialog.getLastMessageDate().getTime();

        int counter = 1;
        for (Message message : messages) {
            response[counter] = utilsService.createFragmentMessage(message, customUser);
            counter++;
        }

        return response;
    }

    @RequestMapping(value = "/message-create/{dialog-id}", method = RequestMethod.POST)
    @ResponseBody
    public String createMessage(@RequestParam("message-text") String text,
                                @PathVariable("dialog-id") long dialogId) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        Dialog dialog = dialogService.getById(dialogId);
        System.out.println(dialog);
        Message message = new Message();
        message.setFrom(customUser);
        message.setText(text);
        message.setIsread(false);
        message.setDialog(dialog);
        System.out.println(message);
        Message temp = messageService.addMessage(message);
        System.out.println(temp + " " + temp.getCreateDate());
        dialog.setLastMessageDate(temp.getCreateDate());
        dialogService.updateDialog(dialog);

        return "added";
    }
}
