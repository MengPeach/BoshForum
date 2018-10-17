/**
 * 
 */
package com.bosh.restcontroller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bosh.base.BaseController;
import com.bosh.dto.QuarkResult;
import com.bosh.entity.Label;
import com.bosh.entity.Posts;
import com.bosh.entity.Reply;
import com.bosh.entity.User;
import com.bosh.service.LabelService;
import com.bosh.service.PostsService;
import com.bosh.service.ReplyService;
import com.bosh.service.UserService;

/**
 * 
 * @author wangmt
 * @date 2018年9月19日
 */
@RestController
@RequestMapping("/posts")
public class PostsController extends BaseController { 
	
	@Autowired
    private UserService userService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private PostsService postsService;

    @Autowired
    private ReplyService replyService;

        @PostMapping
    public QuarkResult CreatePosts(final Posts posts, final String token, final Integer labelId) {
        QuarkResult result = restProcessor(() -> {

            if (token == null) return QuarkResult.warn("请先登录！");

            User userbytoken = userService.getUserByToken(token);
            if (userbytoken == null) return QuarkResult.warn("用户不存在,请先登录！");

            User user = userService.findOne(userbytoken.getId());
            if (user.getEnable() != 1) return QuarkResult.warn("用户处于封禁状态！");

            postsService.savePosts(posts, labelId, user);
            return QuarkResult.ok();
        });

        return result;
    }

    @GetMapping()
    public QuarkResult GetPosts(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int length) {
        QuarkResult result = restProcessor(() -> {
            if (!type.equals("good") && !type.equals("top") && !type.equals(""))
                return QuarkResult.error("类型错误!");
            Page<Posts> page = postsService.getPostsByPage(type, search, pageNo - 1, length);
            return QuarkResult.ok(page.getContent(), page.getTotalElements(), page.getNumberOfElements());

        });

        return result;

    }


    @GetMapping("/detail/{postsid}")
    public QuarkResult GetPostsDetail(
            @PathVariable("postsid") Integer postsid,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int length) {
        QuarkResult result = restProcessor(() -> {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Posts posts = postsService.findOne(postsid);
            if (posts == null) return QuarkResult.error("帖子不存在");
            map.put("posts", posts);

            Page<Reply> page = replyService.getReplyByPage(postsid, pageNo - 1, length);
            map.put("replys", page.getContent());

            return QuarkResult.ok(map, page.getTotalElements(), page.getNumberOfElements());
        });
        return result;

    }

    @GetMapping("/label/{labelid}")
    public QuarkResult GetPostsByLabel(
            @PathVariable("labelid") Integer labelid,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int length) {

        QuarkResult result = restProcessor(() -> {
            Label label = labelService.findOne(labelid);
            if (label == null) return QuarkResult.error("标签不存在");
            Page<Posts> page = postsService.getPostsByLabel(label, pageNo - 1, length);
            return QuarkResult.ok(page.getContent(), page.getTotalElements(), page.getNumberOfElements());

        });

        return result;

    }

}
