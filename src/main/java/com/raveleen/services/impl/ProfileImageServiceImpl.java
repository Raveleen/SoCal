package com.raveleen.services.impl;

import com.raveleen.entities.ProfileImage;
import com.raveleen.repositories.ProfileImageRepository;
import com.raveleen.services.ProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Святослав on 15.01.2017.
 */
public class ProfileImageServiceImpl implements ProfileImageService {
    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Override
    @Transactional(readOnly = true)
    public ProfileImage getProfileImage(long id) {
        return profileImageRepository.findById(id);
    }

    @Override
    @Transactional
    public ProfileImage addProfileImage(ProfileImage profileImage) {
        ProfileImage saved = profileImageRepository.saveAndFlush(profileImage);
        return saved;
    }
}
