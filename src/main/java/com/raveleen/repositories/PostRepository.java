package com.raveleen.repositories;

import com.raveleen.entities.Post;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Святослав on 15.01.2017.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorIdOrderByCreateDateDesc(long id, Pageable pageable);

    @Query("SELECT u FROM Post u "
            + "INNER JOIN u.author x "
            + "WHERE x.id IN (SELECT d.id FROM CustomUser d "
            + "INNER JOIN d.followers c WHERE c.id = :id1) ORDER BY u.createDate DESC")
    List<Post> getFollowingsPosts(@Param("id1") long id1, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Post u "
            + "INNER JOIN u.likedBy c "
            + "WHERE u.id = :id1")
    int getNumberOfLikes(@Param("id1") long id1);

    @Query("SELECT COUNT(c) FROM Comment u "
            + "INNER JOIN u.post c "
            + "WHERE c.id = :id1")
    int getNumberOfComments(@Param("id1") long id1);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Post u "
            + "INNER JOIN u.likedBy c "
            + "WHERE (u.id = :id2) AND (c.id = :id1)")
    boolean isLiked(@Param("id1") long id1, @Param("id2") long id2);
}
