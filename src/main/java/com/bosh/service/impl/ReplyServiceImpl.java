/**
 * 
 */
package com.bosh.service.impl;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosh.base.BaseServiceImpl;
import com.bosh.dao.PostsDao;
import com.bosh.dao.ReplyDao;
import com.bosh.entity.Notification;
import com.bosh.entity.Posts;
import com.bosh.entity.Reply;
import com.bosh.entity.User;
import com.bosh.exception.ServiceProcessException;
import com.bosh.service.NotificationService;
import com.bosh.service.ReplyService;
import com.bosh.service.WebSocketService;

/**
 * @author wangmt
 * @date 2018年9月21日
 */
@Service
public class ReplyServiceImpl extends BaseServiceImpl<ReplyDao, Reply> implements ReplyService {
	
	@Autowired
    private PostsDao postsDao;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private WebSocketService webSocketService;

	/* (non-Javadoc)
	 * @see com.bosh.service.ReplyService#getReplyByPage(java.lang.Integer, int, int)
	 */
	public Page<Reply> getReplyByPage(Integer postsId, int pageNo, int length) {
		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        Sort sort = new Sort(order);
        PageRequest pageable = new PageRequest(pageNo, length, sort);

        Specification<Reply> specification = new Specification<Reply>() {

            @Override
            public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Integer> $posts = root.get("posts");
                Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal($posts, postsId));
                return predicate;
            }
        };
        Page<Reply> page = repository.findAll(specification, pageable);
        return page;
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.ReplyService#saveReply(com.bosh.entity.Reply, java.lang.Integer, com.bosh.entity.User)
	 */
	  @Transactional
	public void saveReply(Reply reply, Integer postsId, User user) {
		  try {
	            Posts posts = postsDao.findById(postsId).get();

	            if (posts == null) throw new ServiceProcessException("帖子不存在!");

	            //帖子回复数+1
	            int count = posts.getReplyCount();
	            posts.setReplyCount(++count);
	            postsDao.save(posts);

	            //添加回复
	            reply.setInitTime(new Date());
	            reply.setUser(user);
	            reply.setPosts(posts);
	            repository.save(reply);

	            //判断是否是自问自回，如果是则不通知
	            if (posts.getUser().getId()!=user.getId()) {
	                //向消息表中增加信息
	                Notification notification = new Notification();
	                notification.setPosts(posts);
	                notification.setFromuser(user);
	                notification.setTouser(posts.getUser());
	                notification.setInitTime(new Date());
	                notificationService.save(notification);
	                // 使用WebSocket进行1对1通知
	                webSocketService.sendToOne(posts.getUser().getId());
	            }
	        } catch (ServiceProcessException e) {
	            throw e;
	        } catch (Exception e) {
	            // 所有编译期异常转换为运行期异常
	            throw new ServiceProcessException("发布回复失败!");
	        }

	}

}
