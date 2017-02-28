package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Dialog;
import com.raveleen.entities.Message;
import com.raveleen.services.DialogService;
import com.raveleen.services.MessageService;
import com.raveleen.services.UserService;
import com.raveleen.services.UtilsService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
        long us1ID = us1.getId();
        long us2ID = us2.getId();

        if (us1ID == dbUser.getId()) {
            model.addAttribute("dialogUser", us2);
            model.addAttribute("dialogUserFollowers", userService.getNumberOfFollowers(us2ID));
            model.addAttribute("dialogUserFollowing", userService.getNumberOfFollowings(us2ID));
        } else {
            model.addAttribute("dialogUser", us1);
            model.addAttribute("dialogUserFollowers", userService.getNumberOfFollowers(us1ID));
            model.addAttribute("dialogUserFollowing", userService.getNumberOfFollowings(us1ID));
        }
        model.addAttribute("dialog", dialog);
        model.addAttribute("user", dbUser);
        model.addAttribute("followers", userService.getNumberOfFollowers(dbUser.getId()));
        model.addAttribute("following", userService.getNumberOfFollowings(dbUser.getId()));
        return "dialog";
    }

    @RequestMapping(value = "/dialogs/{from}")
    @ResponseBody
    public String[] getDialogsList(@PathVariable("from") int from) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);

        List<Dialog> dialogs = dialogService.dialogsOrderByDate(customUser.getId(), from);
        String[] response = new String[dialogs.size()];

        int counter = 0;
        for (Dialog temp : dialogs) {
            CustomUser us1 = temp.getUser1();
            CustomUser us2 = temp.getUser2();

            if (us1.getId() == customUser.getId()) {
                response[counter] = utilsService.createFragmentDialog(temp, customUser, us2);
            } else {
                response[counter] = utilsService.createFragmentDialog(temp, customUser, us1);
            }

            counter++;
        }

        return response;
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
