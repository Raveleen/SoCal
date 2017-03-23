package com.raveleen.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Dialog;
import com.raveleen.entities.Event;
import com.raveleen.entities.Message;
import com.raveleen.entities.Post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.raveleen.entities.UserRate;
import com.raveleen.services.map.EventLocation;
import com.raveleen.services.map.MapInfo;
import com.raveleen.services.map.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Created by Святослав on 11.01.2017.
 */
@PropertySource("classpath:google-map-api.properties")
@Service
public class UtilsService {
    @Value("${api-key}")
    private String googleApiKey;

    @Value("${static-map-key}")
    private String staticMapKey;

    @Value("${map-zoom}")
    private String zoom;

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
        String[][] storage = new String[events.size()][13];
        int counter = 0;
        for (Event temp : events) {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("HH:mm, EEE dd MMMMM, yyyy", Locale.US);
            String placeId = String.valueOf(temp.getAddress().getPlaceId());
            try {
                MapInfo mapInfo = new MapInfo();
                System.out.println(placeId);
                System.out.println(temp.getId());
                ResultJson resultJson;
                EventLocation eventLocation = new EventLocation();
                if(placeId != null) {
                    resultJson = parserForGoogleMapApi(placeId);
                    mapInfo = resultJson.getMapInfo();
                    eventLocation = resultJson.getMapInfo().getGeometry().getLocation();
                }

                UserRate userRate = userRateService.getByIdAndUserId(temp.getId(), customUser.getId());

                storage[counter][0] = String.valueOf(temp.getId());
                storage[counter][1] = String.valueOf(temp.getHost().getId());
                storage[counter][2] = temp.getHost().getLogin();
                storage[counter][3] = String.valueOf(simpleDateFormat.format(temp.getEventDate()));
                storage[counter][4] = mapInfo.getUrl();

                if ((customUser.getId() == temp.getHost().getId())
                        && (temp.getEventDate().getTime() > new Date().getTime())) {
                    storage[counter][5] = "0";
                } else if ((userRate == null) && !(customUser.getId() == temp.getHost().getId())) {
                    storage[counter][5] = "1";
                } else if (!(customUser.getId() == temp.getHost().getId())
                        && (temp.getEventDate().getTime() < new Date().getTime())
                        && !(userRateService.isUserRated(temp.getId(), customUser.getId()))) {
                    storage[counter][5] = "2";
                    System.out.println(userRateService.isUserRated(temp.getId(), customUser.getId()));
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
                storage[counter][9] = String.valueOf(userRateService.getNumberOfRateForEvent(temp.getId()));

                if (temp.getEventDate().getTime() < new Date().getTime()) {
                    if (userRateService.isThereMarks(temp.getId())) {
                        storage[counter][10] = String.valueOf(userRateService.getAverageMark(temp.getId()));
                    } else {
                        storage[counter][10] = "N/A";
                    }
                } else {
                    storage[counter][10] = "-1";
                }
                StringBuilder mapImageUrl = new StringBuilder();
                mapImageUrl.append("https://maps.googleapis.com/maps/api/staticmap?")
                        .append("center=")
                        .append(eventLocation.getLat())
                        .append(",")
                        .append(eventLocation.getLng())
                        .append("&zoom=")
                        .append(zoom)
                        .append("&size=800x400")
                        .append("&markers=color:0xe981ed%7Clabel:S%7C")
                        .append(eventLocation.getLat())
                        .append(",")
                        .append(eventLocation.getLng())
                        .append("&key=")
                        .append(staticMapKey);
                storage[counter][11] = mapImageUrl.toString();
                storage[counter][12] = mapInfo.getRating() == null ? "N/A" : mapInfo.getRating();
                counter += 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return storage;
    }

    private ResultJson parserForGoogleMapApi(String placeId) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        StringBuilder template = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?placeid=");
        template.append(placeId)
                .append("&key=")
                .append(googleApiKey);

        String json = readJsonFromUrl(template.toString());

        ResultJson resultJson = gson.fromJson(json, ResultJson.class);

        return resultJson;
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private String readJsonFromUrl(String url) throws IOException {
        System.out.println(url);
        InputStream inputStream = new URL(url).openStream();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String json = readAll(bufferedReader);
            return json;
        } finally {
            inputStream.close();
        }
    }
}
