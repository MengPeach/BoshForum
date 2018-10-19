/**
 * 
 */
package com.bosh.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosh.base.BaseController;
import com.bosh.dto.QuarkResult;
import com.bosh.entity.Notification;
import com.bosh.entity.User;
import com.bosh.service.NotificationService;
import com.bosh.service.UserService;

/**
 * 通知消息接口
 * @author wangmt
 * @date 2018年10月19日
 */
@RequestMapping("/notification")
@RestController
public class NotificationController extends BaseController {
	
	@Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    /**
     *  获取用户的通知消息
     * @param uid 用户的id
     * @return
     */
    @GetMapping("/{uid}")
    public QuarkResult getAllNotification(@PathVariable("uid") Integer uid) {
        QuarkResult result = restProcessor(() -> {
            User user = userService.findOne(uid);
            if (user==null) return QuarkResult.warn("用户不存在！");
            List<Notification> list = notificationService.findByUser(user);
            return QuarkResult.ok(list);
        });
        return result;
    }

    /**
     *  删除用户的通知消息
     * @param uid 用户的id
     * @return
     */
    @DeleteMapping("/{uid}")
    public QuarkResult deleteAllNotification(@PathVariable("uid") Integer uid){
        QuarkResult result = restProcessor(() -> {
            User user = userService.findOne(uid);
            if (user == null) return QuarkResult.warn("用户不存在！");
            notificationService.deleteByUser(user);
            return QuarkResult.ok();
        });

        return result;
    }

}
