/**
 * 
 */
package com.bosh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bosh.entity.Reply;

/**
 * @author wangmt
 * @date 2018年9月21日
 */
@SuppressWarnings("rawtypes")
@Repository
public interface ReplyDao extends JpaRepository<Reply, Integer>, JpaSpecificationExecutor {
	
	List<Reply> findAll();

}
