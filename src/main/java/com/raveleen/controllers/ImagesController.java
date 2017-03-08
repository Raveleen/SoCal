package com.raveleen.controllers;

import com.raveleen.services.ImageService;
import com.raveleen.services.ProfileImageService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Святослав on 14.01.2017.
 */
@Controller
public class ImagesController {
    @Autowired
    private ImageService imagesService;

    @Autowired
    private ProfileImageService profileImageService;

    @RequestMapping("/image/{file_id}")
    public void getImage(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable("file_id") long fileId) {
        try {
            byte[] content = imagesService.getImage(fileId).getBody();
            response.setContentType("image/png");
            response.getOutputStream().write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/profile-image/{file_id}")
    public void getProfileImage(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable("file_id") long fileId) {
        try {
            byte[] content = profileImageService.getProfileImage(fileId).getBody();
            response.setContentType("image/png");
            response.getOutputStream().write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
