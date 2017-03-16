package com.raveleen.controllers;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Event;
import com.raveleen.entities.Image;
import com.raveleen.entities.Post;
import com.raveleen.entities.UserRate;
import com.raveleen.services.AddressService;
import com.raveleen.services.EventService;
import com.raveleen.services.UserRateService;
import com.raveleen.services.UserService;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Святослав on 11.03.2017.
 */
@Controller
public class EventsController {
    @Autowired
    private UtilsService utilsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRateService userRateService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/get-following-events/{user-id}/{from}")
    @ResponseBody
    public String[][] getFollowingEvents(@PathVariable("user-id") long userId,
                                         @PathVariable("from") int from) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        List<Event> events = eventService.getFollowingsEvents(userId, from);
        String[][] storage = utilsService.arrayEventFill(events, customUser);
        return storage;
    }

    @RequestMapping(value = "/get-future-events/{user-id}/{from}")
    @ResponseBody
    public String[][] getFutureEvents(@PathVariable("user-id") long userId,
                                      @PathVariable("from") int from) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        List<Event> events = new ArrayList<>();
        //TODO: set up "get-future-events" response
        String[][] storage = utilsService.arrayEventFill(events, customUser);

        return storage;
    }

    @RequestMapping(value = "/get-past-events/{user-id}/{from}")
    @ResponseBody
    public String[][] getPastEvents(@PathVariable("user-id") long userId,
                                    @PathVariable("from") int from) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        List<Event> events = new ArrayList<>();
        //TODO: set up "get-past-events" response
        String[][] storage = utilsService.arrayEventFill(events, customUser);

        return storage;
    }


    @RequestMapping(value = "/event-create", method = RequestMethod.POST)
    @ResponseBody
    public String[][] createEvent(@RequestParam("title") String title,
                                  @RequestParam("info") String info,
                                  @RequestParam(value = "photo") MultipartFile body) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        //TODO: set up event creation
        String[][] storage = utilsService.arrayEventFill(new ArrayList<Event>(), customUser);

        return storage;
    }

    @RequestMapping(value = "/event-delete/event-{event-id}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteEvent(@PathVariable("event-id") long eventId) throws IOException {
        //TODO: set up event delete function

        return "deleted";
    }

    @RequestMapping(value = "/event-visit/event-{event-id}")
    @ResponseBody
    public String visitEvent(@PathVariable("event-id") long eventId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        //TODO: set up "event-visit" function

        return "confirmed";
    }

    @RequestMapping(value = "/event-rate/event-{event-id}/{rate}")
    @ResponseBody
    public String rateEvent(@PathVariable("event-id") long eventId,
                                    @PathVariable("rate") int from) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        //TODO: set up "event-rate" function

        return "rated";
    }
}
