package com.raveleen.services.impl;

import com.raveleen.entities.Post;
import com.raveleen.repositories.PostRepository;
import com.raveleen.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Святослав on 23.01.2017.
 */
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Post> findByAuthorIdOrderByCreateDateDesc(long id, int from) {
        int a;
        if (from == 0) {
            a = 0;
        } else {
            a = from / 10;
        }
        Pageable temp = new PageRequest(a, 10);
        return postRepository.findByAuthorIdOrderByCreateDateDesc(id, temp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getFollowingsPosts(long id1, int from) {
        int a;
        if (from == 0) {
            a = 0;
        } else {
            a = from / 10;
        }
        Pageable temp = new PageRequest(a, 10);
        return postRepository.getFollowingsPosts(id1, temp);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post getById(long id) {
        return postRepository.findOne(id);
    }

    @Override
    @Transactional
    public void deletePostById(long id) {
        postRepository.delete(id);
    }

    @Override
    @Transactional
    public Post addPost(Post post) {
        return postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfLikes(long id) {
        return postRepository.getNumberOfLikes(id);
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfComments(long id) {
        return postRepository.getNumberOfComments(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLiked(long id1, long id2) {
        System.out.println(postRepository.isLiked(id1, id2));
        return postRepository.isLiked(id1, id2);
    }
}
