package com.raveleen.services;

import com.raveleen.entities.Image;

/**
 * Created by Святослав on 15.01.2017.
 */
public interface ImageService {
    Image getImage(long id);

    Image addImage(Image image);
}
