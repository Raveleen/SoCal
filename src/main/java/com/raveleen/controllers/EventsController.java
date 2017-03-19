package com.raveleen.controllers;

import com.raveleen.entities.Address;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        List<Event> events = eventService.getFollowingsEvents(userId, new Date(), from);
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
        List<Event> events = eventService.getFutureEvents(userId, new Date(), from);
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
        List<Event> events = eventService.getPastEvents(userId, new Date(), from);
        String[][] storage = utilsService.arrayEventFill(events, customUser);

        return storage;
    }


    @RequestMapping(value = "/event-create", method = RequestMethod.POST)
    @ResponseBody
    public String[][] createEvent(@RequestParam("title") String title,
                                  @RequestParam("info") String info,
                                  @RequestParam("date") String date,
                                  @RequestParam("time") String time,
                                  @RequestParam("address") String address,
                                  @RequestParam("placeId") String placeId)
            throws IOException, ParseException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        System.out.println(date + " " + time + " " + title + " " + info);
        String datePart = "yyyy-MM-dd";
        String timePart = "HH:mm:ss";
        DateFormat sdf = new SimpleDateFormat(datePart + " " + timePart);
        Event event = new Event();
        Date input = sdf.parse(date + " " + time);
        event.setEventDate(input);
        event.setTitle(title);
        event.setInfo(info);
        event.setHost(customUser);
        Address address1 = new Address();
        address1.setAddressLine(address);
        address1.setPlaceId(placeId);
        address1 = addressService.addAddress(address1);
        event.setAddress(address1);
        event = eventService.addEvent(event);
        ArrayList<Event> events = new ArrayList<>();
        events.add(event);
        String[][] storage = utilsService.arrayEventFill(events, customUser);

        return storage;
    }

    @RequestMapping(value = "/event-delete/event-{event-id}", method = RequestMethod.POST)
    @ResponseBody
    public String deleteEvent(@PathVariable("event-id") long eventId) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        Event event = eventService.getById(eventId);
        if (event.getHost().getId() == customUser.getId()) {
            eventService.deleteEventById(eventId);
        }

        return "deleted";
    }

    @RequestMapping(value = "/event-visit/event-{event-id}")
    @ResponseBody
    public String visitEvent(@PathVariable("event-id") long eventId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        UserRate userRate = new UserRate();
        Event event = eventService.getById(eventId);
        userRate.setEvent(event);
        userRate.setUser(customUser);
        userRateService.addUserRate(userRate);

        return "confirmed";
    }

    @RequestMapping(value = "/event-rate/event-{event-id}/{rate}")
    @ResponseBody
    public String rateEvent(@PathVariable("event-id") long eventId,
                            @PathVariable("rate") int mark) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser customUser = userService.getUserByLogin(login);
        Event event = eventService.getById(eventId);
        UserRate userRate = userRateService.getByIdAndUserId(eventId, customUser.getId());
        userRate.setMark(mark);
        userRateService.updateRate(userRate);

        return "rated";
    }
}
