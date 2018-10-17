/**
 * 
 */
package com.bosh.service;

import org.springframework.data.domain.Page;

import com.bosh.base.BaseService;
import com.bosh.entity.Reply;
import com.bosh.entity.User;

/**
 * @author wangmt
 * @date 2018年9月21日
 */
public interface ReplyService extends BaseService<Reply> {
	
	/**
     * 翻页获取回复
     *
     * @param postsId
     * @param pageNo
     * @param length
     * @return
     */
    Page<Reply> getReplyByPage(Integer postsId, int pageNo, int length);

    /**
     * 保存回复
     * @param reply
     * @param postsId
     * @param user
     */
    void saveReply(Reply reply,Integer postsId,User user);

}
