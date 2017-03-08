package com.raveleen.repositories;

import com.raveleen.entities.Message;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Святослав on 15.01.2017.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByDialogIdOrderByCreateDateDesc(long id, Pageable pageable);

    @Query("SELECT COUNT(u) FROM Message u "
            + "INNER JOIN u.from c "
            + "INNER JOIN u.dialog b "
            + "WHERE c.id = :user_id AND b.id = :dialog AND u.isread = 0")
    int numberOfUnreadMessages(@Param("dialog") long id, @Param("user_id") long userId);

    @Query("SELECT u FROM Message u "
            + "INNER JOIN u.dialog b "
            + "WHERE b.id = :dialog AND u.createDate > :lsdate")
    List<Message> getNewMessages(@Param("dialog") long id, @Param("lsdate") Date time);
}
