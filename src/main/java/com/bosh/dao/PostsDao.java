/**
 * 
 */
package com.bosh.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bosh.entity.Label;
import com.bosh.entity.Posts;
import com.bosh.entity.User;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
@SuppressWarnings("rawtypes")
@Repository
public interface PostsDao extends JpaRepository<Posts,Integer>, JpaSpecificationExecutor {
	
	List<Posts> findAll();

    @Query(value = "select p.id, p.title , p.reply_count from quark_posts p where DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=DATE(p.init_time) ORDER by reply_count desc limit 10" ,nativeQuery = true)
    List<Object> findHot();

    Page<Posts> findByUser(User user, Pageable pageable);

    Page<Posts> findByLabel(Label label,Pageable pageable);

}
