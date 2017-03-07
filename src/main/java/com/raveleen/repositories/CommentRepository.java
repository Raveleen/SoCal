package com.raveleen.repositories;

import com.raveleen.entities.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Святослав on 15.01.2017.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreateDateDesc(long id, Pageable pageable);
}
