package com.raveleen.repositories;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.Dialog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Святослав on 14.01.2017.
 */
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Dialog u INNER JOIN u.user1 c INNER JOIN u.user2 b WHERE ((c.id = :id2) AND (b.id = :id1)) OR ((b.id = :id2) AND (c.id = :id1))")
    boolean isDialogExists(@Param("id1") long id1, @Param("id2") long id2);

    @Query("SELECT u FROM Dialog u INNER JOIN u.user1 c INNER JOIN u.user2 b WHERE ((c.id = :id2) AND (b.id = :id1)) OR ((b.id = :id2) AND (c.id = :id1))")
    Dialog getDialog(@Param("id1") long id1, @Param("id2") long id2);

    @Query("SELECT u FROM Dialog u INNER JOIN u.user1 c INNER JOIN u.user2 b WHERE c.id = :id1 OR b.id = :id1 ORDER BY u.lastMessageDate")
    List<Dialog> getDialogs(@Param("id1") long id1, Pageable pageable);
}
