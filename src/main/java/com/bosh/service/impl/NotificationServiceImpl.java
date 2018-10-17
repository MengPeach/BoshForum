/**
 * 
 */
package com.bosh.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.bosh.base.BaseServiceImpl;
import com.bosh.dao.NotificationDao;
import com.bosh.entity.Notification;
import com.bosh.entity.User;
import com.bosh.service.NotificationService;

/**
 * @author wangmt
 * @date 2018年9月21日
 */
@Service
@Transactional
public class NotificationServiceImpl extends BaseServiceImpl<NotificationDao, Notification>
		implements NotificationService {

	/* (non-Javadoc)
	 * @see com.bosh.service.NotificationService#getNotificationCount(int)
	 */
	public long getNotificationCount(int uid) {
		return repository.getNotificationCount(uid);
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.NotificationService#findByUser(com.bosh.entity.User)
	 */
	public List<Notification> findByUser(User user) {
		List<Notification> list = repository.getByTouserOrderByInitTimeDesc(user);
        repository.updateByIsRead(user);
        return list;
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.NotificationService#deleteByUser(com.bosh.entity.User)
	 */
	public void deleteByUser(User user) {
		List<Notification> list = repository.getByTouserOrderByInitTimeDesc(user);
        repository.deleteInBatch(list);

	}

}
