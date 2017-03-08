package com.raveleen.services;

import com.raveleen.entities.Post;
import java.util.List;

/**
 * Created by Святослав on 15.01.2017.
 */
public interface PostService {
    List<Post> findByAuthorIdOrderByCreateDateDesc(long id, int from);

    List<Post> getFollowingsPosts(long id1, int from);

    Post getById(long id);

    void updatePost(Post post);

    void deletePostById(long id);

    Post addPost(Post post);

    int getNumberOfLikes(long id);

    int getNumberOfComments(long id);

    boolean isLiked(long id1, long id2);
}
