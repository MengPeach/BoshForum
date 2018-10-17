/**
 * 
 */
package com.bosh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosh.dao.PostsDao;
import com.bosh.dao.UserDao;
import com.bosh.service.RankService;

/**
 * @author wangmt
 * @date 2018年9月25日
 */
@Service
public class RankServiceImpl implements RankService {
	
	@Autowired
    private PostsDao postsDao;

    @Autowired
    private UserDao userDao;

	/* (non-Javadoc)
	 * @see com.bosh.service.RankService#findPostsRank()
	 */
	@Override
	public List<Object> findPostsRank() {
		return postsDao.findHot();
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.RankService#findUserRank()
	 */
	@Override
	public List<Object> findUserRank() {
		return userDao.findNewUser();
	}

}
