/**
 * 
 */
package com.bosh.service;

import java.util.List;

import com.bosh.base.BaseService;
import com.bosh.entity.Notification;
import com.bosh.entity.User;

/**
 * @author wangmt
 * @date 2018年9月21日
 */
public interface NotificationService extends BaseService<Notification> {
	
	/**
     * 获取用户的未读通知数量
     * @param uid
     * @return
     */
    long getNotificationCount(int uid);

    /**
     * 获取用户所有通知
     * @param user
     */
    List<Notification> findByUser(User user);

    /**
     * 根据用户删除所有通知
     * @param user
     * @return
     */
    void deleteByUser(User user);

}
