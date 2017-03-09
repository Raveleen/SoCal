package com.raveleen.services.impl;

import com.raveleen.entities.Comment;
import com.raveleen.repositories.CommentRepository;
import com.raveleen.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Святослав on 23.01.2017.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public void delete(long id) {
        commentRepository.delete(id);
    }

    @Override
    public Comment getById(long id) {
        return commentRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByPostIdOrderByCreateDate(long id, int from) {
        int a;
        if (from == 0) {
            a = 0;
        } else {
            a = from / 10;
        }
        Pageable temp = new PageRequest(a, 10);
        return commentRepository.findByPostIdOrderByCreateDateDesc(id, temp);
    }
}
