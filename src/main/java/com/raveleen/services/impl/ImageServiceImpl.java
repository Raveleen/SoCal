package com.raveleen.services.impl;

import com.raveleen.entities.Image;
import com.raveleen.repositories.ImageRepository;
import com.raveleen.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Святослав on 23.01.2017.
 */
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    @Transactional(readOnly = true)
    public Image getImage(long id) {
        return imageRepository.findOne(id);
    }

    @Override
    @Transactional
    public Image addImage(Image image) {
        return imageRepository.saveAndFlush(image);
    }
}
