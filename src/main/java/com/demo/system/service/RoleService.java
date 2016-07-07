package com.demo.system.service;

import com.demo.system.bean.Role;
import com.demo.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统角色服务
 * 
 * @author sdyang
 * @date 2016年2月16日 下午5:25:53
 */
@Component
public class RoleService {

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private ResourceService resourceService;

	public void save(Role role) {
		if(role.getPk_role() == null){
			roleDao.insert(role);
		}else{
			roleDao.update(role);
		}
	}

	public void delete(Long roleId) {
		roleDao.delete(roleId);
	}

	public Role findOne(Long roleId) {
		return roleDao.findOne(roleId);
	}

	public List<Role> findAll() {
		return (List<Role>) roleDao.findAll();
	}

	public Set<String> findRoles(Long... roleIds) {
		Set<String> roles = new HashSet<String>();
		for (Long roleId : roleIds) {
			Role role = findOne(roleId);
			if (role != null) {
				roles.add(role.getRole());
			}
		}
		return roles;
	}

	public Set<String> findPermissions(Long[] roleIds) {
		Set<Long> resourceIds = new HashSet<Long>();
		for (Long roleId : roleIds) {
			Role role = findOne(roleId);
			if (role != null) {
				resourceIds.addAll(role.getResourceList());
			}
		}
		return resourceService.findPermissions(resourceIds);
	}
}
