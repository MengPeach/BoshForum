/**
 * 
 */
package com.bosh.service;

import java.util.List;

/**
 * @author wangmt
 * @date 2018年9月25日
 */
public interface RankService {
	
	/**
     * 获取最近一周热帖排行榜
     * @return
     */
   List<Object> findPostsRank();

    /**
     * 获取最近一周的新注册用户
     * @return
     */
   List<Object> findUserRank();

}
