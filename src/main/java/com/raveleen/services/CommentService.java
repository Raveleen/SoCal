package com.raveleen.services;

import com.raveleen.entities.Comment;
import java.util.List;

/**
 * Created by Святослав on 15.01.2017.
 */
public interface CommentService {
    Comment addComment(Comment comment);

    void delete(long id);

    Comment getById(long id);

    List<Comment> findByPostIdOrderByCreateDate(long id, int from);
}
