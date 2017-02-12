package com.raveleen.services;

import com.raveleen.entities.ProfileImage;

/**
 * Created by Святослав on 15.01.2017.
 */
public interface ProfileImageService {
    ProfileImage getProfileImage(long id);
    ProfileImage addProfileImage(ProfileImage profileImage);
}
