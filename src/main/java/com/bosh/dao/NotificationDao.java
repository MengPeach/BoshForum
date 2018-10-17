/**
 * 
 */
package com.bosh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bosh.entity.Notification;
import com.bosh.entity.User;

/**
 * @author wangmt
 * @date 2018年9月21日
 */
@Repository
public interface NotificationDao extends JpaRepository<Notification, Integer> {
	
	@Query(value = "SELECT count(id) FROM quark_notification n WHERE n.touser_id = ?1 AND n.is_read = 0", nativeQuery = true)
    long getNotificationCount(Integer id);

    List<Notification> getByTouserOrderByInitTimeDesc(User user);

    @Modifying
    @Query("update Notification n set n.isRead = true where n.touser = ?1")
    void updateByIsRead(User user);

}
