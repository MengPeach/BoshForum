/**
 * 
 */
package com.bosh.restcontroller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosh.base.BaseController;
import com.bosh.dto.QuarkResult;
import com.bosh.entity.Posts;
import com.bosh.entity.User;
import com.bosh.service.NotificationService;
import com.bosh.service.PostsService;
import com.bosh.service.UserService;

/**
 *   用户接口  用户注册，登录，登出，获取用户信息等服务
 * @author wangmt
 * @date 2018年9月19日
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
    private UserService userService;

    @Autowired
    private PostsService postsService;

    @Autowired
    private NotificationService notificationService;
	
	/**
     *  注册接口
     * @param email 用户邮箱
     * @param username 用户名称
     * @param password 用户密码
     * @return
     */
    @PostMapping
    public QuarkResult checkUserName(String email,String username,String password) {
        QuarkResult result = restProcessor(() -> {
            if (!userService.checkUserName(username))
                return QuarkResult.warn("用户名已存在，请重新输入");

            if (!userService.checkUserEmail(email))
                return QuarkResult.warn("用户邮箱已存在，请重新输入");

            else
                userService.createUser(email,username,password);

            return QuarkResult.ok();

        });
        return result;
    }

    /**
       *    登录接口
     * @param email 用户邮箱
     * @param password 用户密码
     * @return
     */
    @PostMapping("/login")
    public QuarkResult Login(String email,String password) {

        QuarkResult result = restProcessor(() -> {
            User loginUser = userService.findByEmail(email);
            if (loginUser == null)
                return QuarkResult.warn("用户邮箱不存在，请重新输入");
            if (!loginUser.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes())))
                return QuarkResult.warn("用户密码错误，请重新输入");
            String token = userService.LoginUser(loginUser);
            return QuarkResult.ok(token);
        });
        return result;
    }

    /**
     *  根据Token获取用户的信息
     * @param token 发送给用户的唯一令牌
     * @return
     */
    @GetMapping("/{token}")
    public QuarkResult getUserByToken(@PathVariable String token) {
        QuarkResult result = restProcessor(() -> {
            User user = userService.getUserByToken(token);
            if (user == null) return QuarkResult.warn("session过期,请重新登录");
            return QuarkResult.ok(user);
        });

        return result;
    }

    /**
     * 根据Token获取用户的信息与通知消息数量
     * @param token 发送给用户的唯一令牌
     * @return
     */
    @GetMapping("/message/{token}")
    public QuarkResult getUserAndMessageByToken(@PathVariable String token){
        QuarkResult result = restProcessor(() -> {
            HashMap<String, Object> map = new HashMap<>();
            User user = userService.getUserByToken(token);
            if (user == null) return QuarkResult.warn("session过期,请重新登录");
            long count = notificationService.getNotificationCount(user.getId());
            map.put("user",user);
            map.put("messagecount",count);
            return QuarkResult.ok(map);
        });

        return result;
    }

    /**
     * 根据Token修改用户的信息
     * @param token 发送给用户的唯一令牌
     * @param username 要修改的用户名
     * @param signature 用户签名 
     * @param sex 要修改的性别：数值0为男，1为女
     * @return
     */
    @PutMapping("/{token}")
    public QuarkResult updateUser(@PathVariable("token") String token,String username,String signature,Integer sex){
        QuarkResult result = restProcessor(() -> {
            if (!userService.checkUserName(username)) return QuarkResult.warn("用户名重复！");
            if (sex != 0 && sex != 1) return QuarkResult.warn("性别输入错误！");
            userService.updateUser(token, username, signature, sex);
            return QuarkResult.ok();
        });

        return result;
    }

    /**
     * 根据Token修改用户的密码
     * @param token 发送给用户的唯一令牌
     * @param newpsd 新的密码
     * @param oldpsd 新的密码
     * @return
     */
    @PutMapping("/password/{token}")
    public QuarkResult updatePassword(@PathVariable("token") String token,String newpsd,String oldpsd){

        QuarkResult result = restProcessor(() -> {
            userService.updateUserPassword(token,oldpsd,newpsd);
            return QuarkResult.ok();
        });
        return result;
    }

    /**
     * 登出用户
     * @param token 发送给用户的唯一令牌
     * @return
     */
    @PostMapping("/logout")
    public QuarkResult logout(String token) {
        QuarkResult result = restProcessor(() -> {
            userService.LogoutUser(token);
            return QuarkResult.ok();
        });

        return result;
    }

    /**
     * 根据用户ID获取用户详情与用户最近发布的十个帖子[主要用于用户主页展示]
     * @param userid 用户的id
     * @return
     */
    @GetMapping("/detail/{userid}")
    public QuarkResult getUserById(@PathVariable("userid") Integer userid){
        QuarkResult result = restProcessor(() -> {
            User user = userService.findOne(userid);
            if (user == null || userid == null) return QuarkResult.warn("用户不存在");
            List<Posts> postss = postsService.getPostsByUser(user);
            HashMap<String, Object> map = new HashMap<>();
            map.put("posts",postss);
            map.put("user",user);
            return QuarkResult.ok(map);
        });
        return result;
    }

}
