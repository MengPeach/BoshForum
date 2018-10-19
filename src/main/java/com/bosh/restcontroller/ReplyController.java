/**
 * 
 */
package com.bosh.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosh.base.BaseController;
import com.bosh.dto.QuarkResult;
import com.bosh.entity.Reply;
import com.bosh.entity.User;
import com.bosh.service.ReplyService;
import com.bosh.service.UserService;

/**
 * 回复接口，对帖子进行回复,点赞回复等服务
 * 
 * @author wangmt
 * @date 2018年10月19日
 */
@RestController
@RequestMapping("/reply")
public class ReplyController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 发布回复接口
	 * 
	 * @param reply   回复内容
	 * @param postsId 帖子ID
	 * @param token   用户令牌
	 * @return
	 */
	@Autowired
	private ReplyService replyService;

	@PostMapping
	public QuarkResult CreateReply(Reply reply, Integer postsId, String token) {
		QuarkResult result = restProcessor(() -> {
			if (token == null)
				return QuarkResult.warn("请先登录！");

			User userbytoken = userService.getUserByToken(token);
			if (userbytoken == null)
				return QuarkResult.warn("用户不存在,请先登录！");

			User user = userService.findOne(userbytoken.getId());
			if (user.getEnable() != 1)
				return QuarkResult.warn("用户处于封禁状态！");

			replyService.saveReply(reply, postsId, user);
			return QuarkResult.ok();
		});
		return result;
	}

}
