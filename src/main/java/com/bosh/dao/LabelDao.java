/**
 * 
 */
package com.bosh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bosh.entity.Label;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
@Repository
public interface LabelDao extends JpaRepository<Label, Integer> {

}
