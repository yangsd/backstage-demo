package com.demo.system.service;


import com.demo.system.bean.Role;
import com.demo.system.bean.User;
import com.demo.system.dao.RoleDao;
import com.demo.system.dao.UserDao;
import com.demo.system.shiro.PasswordHelper;
import com.demo.util.RandomKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 用户服务
 * 
 * @author sdyang
 * @date 2016年1月24日 上午10:55:52
 */
@Component
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordHelper helper;

	// 根据登录名查找用户
	public User findUserByLoginid(String loginid) {
		return userDao.findByLoginid(loginid);
	}

	// 根据登录名查找角色
	public Set<String> findRoles(String loginid) {

		User user = userDao.findByLoginid(loginid);

		if (user == null) {
			return Collections.emptySet();
		}

		//String roleIdsStr = user.getRoleid();

		//List<Long> roleIds = getRoleIds(roleIdsStr);
		
		Long roleid = user.getRoleid();

		if (roleid == null) {
			return Collections.emptySet();
		}

		Set<String> roles = new HashSet<String>();

		//for (Long roleId : roleIds) {
			Role role = roleService.findOne(roleid);
			if (role != null) {
				roles.add(role.getRole());
			}
		//}
		return roles;
	}

	public Set<String> findPermissions(String loginid) {
		return this.findRoles(loginid);
	}

	// 将角色字符串转换为List
	private List<Long> getRoleIds(String roleIdsStr) {
		List<Long> roleIds = new ArrayList<Long>();
		if (StringUtils.isEmpty(roleIdsStr)) {
			return null;
		}
		String[] roleIdArray = roleIdsStr.split(",");
		for (String roleIdStr : roleIdArray) {
			if (StringUtils.isEmpty(roleIdStr)) {
				continue;
			}
			roleIds.add(Long.valueOf(roleIdStr));
		}
		return roleIds;
	}
	/**
	 * 保存用户
	 * @param user
	 * @author sdyang
	 * @date   2016年2月23日 上午11:45:14
	 */
	public void save(User user){
		if(StringUtils.isEmpty(user.getPk_user())){
			helper.encryptPassword(user);
			userDao.insert(user);
		}else{
			User u = userDao.findOne(user.getPk_user());
			u.setUsername(user.getUsername());
			u.setEmail(user.getEmail());
			u.setRoleid(user.getRoleid());
			u.setStatus(user.getStatus());

			userDao.update(user);
		}
	}

	// 修改密码
	public void changePassword(Long userId, String newPassword) {
		User user = userDao.findOne(userId);
		user.setPassword(newPassword);
		helper.encryptPassword(user);
		userDao.update(user);
	}
	
	//重置密码
	public String resetPassword(Long pk_user){
		User user = userDao.findOne(pk_user);
		String password = RandomKey.getRandomKey();//随机密码
		user.setPassword(password);
		helper.encryptPassword(user);
		userDao.update(user);
		return password;
	}

	// 用户列表
	public List<User> findAll() {
		List<Role> roles = (List<Role>) roleDao.findAll();
		Map<Long,Role> roleMap = new HashMap<Long,Role>();
		for(Role r:roles){
			roleMap.put(r.getPk_role(), r);
		}
		List<User> userList =  (List<User>) userDao.findAll();
		for(User u:userList){
			u.setRolename(roleMap.get(u.getRoleid()).getDescription());
		}
		return userList;
	}

	// 删除用户
	public void delete(Long pk_user) {
		userDao.delete(pk_user);
	}
	
	//根据主键查询用户
	public User findOne(Long pk_user){
		return userDao.findOne(pk_user);
	}

}
