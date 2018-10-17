/**
 * 
 */
package com.bosh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosh.base.BaseServiceImpl;
import com.bosh.dao.LabelDao;
import com.bosh.dao.PostsDao;
import com.bosh.entity.Label;
import com.bosh.entity.Posts;
import com.bosh.entity.User;
import com.bosh.exception.ServiceProcessException;
import com.bosh.service.PostsService;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
@Service
public class PostsServiceImpl extends BaseServiceImpl<PostsDao, Posts> implements PostsService {
	
	@Autowired
    private LabelDao labelDao;

    @Transactional
    public void savePosts(Posts posts, Integer labelId, User user) {
		try {
			Label label = labelDao.findById(labelId).get();
            //Label label = labelDao.findOne(labelId);

            if (label == null) throw new ServiceProcessException("标签不存在!");
            //标签的帖子数量+1
            Integer postsCount = label.getPostsCount();
            label.setPostsCount(++postsCount);
            labelDao.save(label);

            //添加帖子
            posts.setLabel(label);
            posts.setInitTime(new Date());
            posts.setUser(user);
            repository.save(posts);
        } catch (ServiceProcessException e) {
            throw e;
        } catch (Exception e) {
            // 所有编译期异常转换为运行期异常
            throw new ServiceProcessException("发布帖子失败!");
        }

	}

	/* (non-Javadoc)
	 * @see com.bosh.service.PostsService#getPostsByPage(java.lang.String, java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	public Page<Posts> getPostsByPage(final String type, final String search, int pageNo, int length) {
		List<Sort.Order> orders = new ArrayList<Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "top"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));


        Sort sort = new Sort(orders);
        PageRequest pageable = new PageRequest(pageNo, length, sort);

        Specification<Posts> specification = new Specification<Posts>() {
            public Predicate toPredicate(Root<Posts> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Boolean> $top = root.get("top");
                Path<Boolean> $good = root.get("good");
                Path<String> $title = root.get("title");
                ArrayList<Predicate> list = new ArrayList<Predicate>(); 
                if (type != null && type.equals("good")) list.add(criteriaBuilder.equal($good, true));
                if (type != null && type.equals("top")) list.add(criteriaBuilder.equal($top, true));
                if (search != null && search != "") list.add(criteriaBuilder.like($title, "%" + search + "%"));

                Predicate predicate = criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                return predicate;
            }
        };
        Page<Posts> page = repository.findAll(specification, pageable);

        return page;
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.PostsService#getPostsByUser(com.bosh.entity.User)
	 */
	@SuppressWarnings("deprecation")
	public List<Posts> getPostsByUser(User user) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "initTime"));
        Pageable pageable = new PageRequest(0, 10, sort);
        Page<Posts> page = repository.findByUser(user, pageable);
        return page.getContent();
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.PostsService#getPostsByLabel(com.bosh.entity.Label, int, int)
	 */
	@SuppressWarnings("deprecation")
	public Page<Posts> getPostsByLabel(Label label, int pageNo, int lenght) {
		List<Sort.Order> orders = new ArrayList<Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "top"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sort = new Sort(orders);
        Pageable pageable = new PageRequest(pageNo, lenght, sort);
        Page<Posts> postss = repository.findByLabel(label, pageable);
        return postss;
	}

}
