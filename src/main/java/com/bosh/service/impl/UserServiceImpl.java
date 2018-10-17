/**
 * 
 */
package com.bosh.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.bosh.base.BaseServiceImpl;
import com.bosh.dao.UserDao;
import com.bosh.entity.QuarkResult;
import com.bosh.entity.User;
import com.bosh.exception.ServiceProcessException;
import com.bosh.service.RedisService;
import com.bosh.service.UserService;
import com.bosh.utils.HttpClientUtils;
import com.bosh.utils.JsonUtils;

/**
 * @author wangmt
 * @date 2018年9月20日
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {
	
	@Autowired
    private RedisService<Integer> redisSocketService;

    @Autowired
    private RedisService<User> redisService;

    @Value("${REDIS_USERID_KEY}")
    private String REDIS_USERID_KEY;

    @Value("${REDIS_USER_KEY}")
    private String REDIS_USER_KEY;

    @Value("${REDIS_USER_TIME}")
    private Integer REDIS_USER_TIME;	

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#checkUserName(java.lang.String)
	 */
	public boolean checkUserName(String username) {
		User user = repository.findByUsername(username);
        if (user == null) return true;
        return false;
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#checkUserEmail(java.lang.String)
	 */
	public boolean checkUserEmail(String email) {
		User user = repository.findByEmail(email);
        if (user == null) return true;
        return false;
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#findByEmail(java.lang.String)
	 */
	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#createUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void createUser(String email, String username, String password) {
		User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setInitTime(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        repository.save(user);

	}

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#LoginUser(com.bosh.entity.User)
	 */
	public String LoginUser(User user) {
		String token = UUID.randomUUID().toString();
		redisService.cacheString(REDIS_USER_KEY + token, user, REDIS_USER_TIME);
        redisSocketService.cacheSet(REDIS_USERID_KEY,user.getId());
//        loginId.add(user.getId());//维护一个登录用户的set
        return token;
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#getUserByToken(java.lang.String)
	 */
	public User getUserByToken(String token) {
		User user = redisService.getStringAndUpDate(REDIS_USER_KEY + token, REDIS_USER_TIME);
        return user;
	}

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#LogoutUser(java.lang.String)
	 */
	public void LogoutUser(String token) {
		User user = getUserByToken(token);
        redisService.deleteString(REDIS_USER_KEY + token);
        redisSocketService.deleteSet(REDIS_USERID_KEY,user.getId());
//        loginId.remove(user.getId());//维护一个登录用户的set

	}

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#updateUser(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public void updateUser(String token, String username, String signature, Integer sex) {
		User cacheuser = redisService.getString(REDIS_USER_KEY + token);
        if (cacheuser == null) throw new ServiceProcessException("session过期,请重新登录");
        User user = repository.findById(cacheuser.getId()).get();
        user.setUsername(username);
        user.setSex(sex);
        user.setSignature(signature);
        repository.save(user);
        redisService.cacheString(REDIS_USER_KEY + token, user, REDIS_USER_TIME);

	}

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#updataUserIcon(java.lang.String, java.lang.String)
	 */
	public void updataUserIcon(String token, String icon) {
		User cacheuser = redisService.getString(REDIS_USER_KEY + token);
        if (cacheuser == null)
            throw new ServiceProcessException("用户Session过期，请重新登录");
        User user = repository.findById(cacheuser.getId()).get();
        user.setIcon(icon);
        repository.save(user);
        redisService.cacheString(REDIS_USER_KEY + token, user, REDIS_USER_TIME);

	}

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#updateUserPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateUserPassword(String token, String oldpsd, String newpsd) {
		User cacheuser = redisService.getString(REDIS_USER_KEY + token);
        if (cacheuser == null)
            throw new ServiceProcessException("用户Session过期，请重新登录");
        User user = repository.findById(cacheuser.getId()).get();
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(oldpsd.getBytes())))
            throw new ServiceProcessException("原始密码错误,请重新输入");
        user.setPassword(DigestUtils.md5DigestAsHex(newpsd.getBytes()));
        repository.save(user);
        redisService.deleteString(REDIS_USER_KEY+token);

	}
	
	@Value("${api_getUserByToken}")
    private String api_getUserByToken;

	/* (non-Javadoc)
	 * @see com.bosh.service.UserService#getUserByApi(java.lang.String)
	 */
	public User getUserByApi(String token) {
		String s = HttpClientUtils.doGet(api_getUserByToken + token);
        QuarkResult quarkResult = JsonUtils.jsonToQuarkResult(s, User.class);
        User data= (User) quarkResult.getData();
        return data;
	}

}
