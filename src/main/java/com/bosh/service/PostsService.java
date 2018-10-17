/**
 * 
 */
package com.bosh.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bosh.base.BaseService;
import com.bosh.entity.Label;
import com.bosh.entity.Posts;
import com.bosh.entity.User;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
public interface PostsService extends BaseService<Posts> {
	
	/**
     * 保存帖子
     * @param posts 帖子
     * @param labelId 标签id
     */
    void savePosts(Posts posts,Integer labelId,User user);
    /**
     * 翻页查询帖子
     * @param type
     * @param search
     * @param pageNo
     * @param length
     * @return
     */
    Page<Posts> getPostsByPage(String type ,String search, int pageNo,int length);

    /**
     * 获取用户最近发布的10个POSTS
     * @param user
     * @return
     */
    List<Posts> getPostsByUser(User user);


    /**
     * 根据标签分页获取获取Posts
     * @param label
     * @return
     */
    Page<Posts> getPostsByLabel(Label label,int pageNo,int lenght);

}
