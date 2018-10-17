/**
 * 
 */
package com.bosh.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosh.base.BaseController;
import com.bosh.dto.QuarkResult;
import com.bosh.service.RankService;
import com.bosh.service.RedisService;

/**
 *  排行榜接口，七天内热帖排行榜,七天内新注册用户排行榜
 * @author wangmt
 * @date 2018年9月25日
 */
@RestController
@RequestMapping("/rank")
public class RankController extends BaseController {
	
	@Autowired
    private RankService rankService;

    @Autowired
    private RedisService<List<Object>> redisService;

    @Value("${REDIS_RANK_POSTS}")
    private String REDIS_RANK_POSTS;

    @Value("${REDIS_RANK_USERS}")
    private String REDIS_RANK_USERS;
    
    /**
     *  获取一个月内热帖排行榜
     * @return
     */
    @GetMapping("/topPosts")
    public QuarkResult getTotPosts() {
        QuarkResult result = restProcessor(() -> {
            List<Object> hot = redisService.getString(REDIS_RANK_POSTS);
            if (hot != null) {
                return QuarkResult.ok(hot);
            }
            hot = rankService.findPostsRank();
            redisService.cacheString(REDIS_RANK_POSTS, hot, 1);
            return QuarkResult.ok(hot);
        });
        return result;
    }

    /**
     *   获取一个月内新注册的用户
     * @return
     */
    @GetMapping("/newUsers")
    public QuarkResult getNewUser() {
        QuarkResult result = restProcessor(() -> {
            List<Object> users = redisService.getString(REDIS_RANK_USERS);
            if (users != null) return QuarkResult.ok(users);

            users = rankService.findUserRank();
            redisService.cacheString(REDIS_RANK_USERS, users, 1);
            return QuarkResult.ok(users);
        });
        return result;
    }

}
