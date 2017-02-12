package com.raveleen.repositories;

import com.raveleen.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Святослав on 14.01.2017.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
