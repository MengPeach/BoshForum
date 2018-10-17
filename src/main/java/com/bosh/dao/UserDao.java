/**
 * 
 */
package com.bosh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bosh.entity.User;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
@SuppressWarnings("rawtypes")
@Repository
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor {
	
	User findByUsername(String username);

    User findByEmail(String email);

    @Query(value = "select u.id, u.username , u.icon from quark_user u where DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=DATE(u.init_time) ORDER BY u.id DESC limit 12" ,nativeQuery = true)
    List<Object> findNewUser();

}
