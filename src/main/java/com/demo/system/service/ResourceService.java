package com.demo.system.service;

import com.demo.system.bean.Resource;
import com.demo.system.bean.Role;
import com.demo.system.bean.User;
import com.demo.system.dao.ResourceDao;
import com.demo.system.dao.RoleDao;
import com.demo.system.dao.UserDao;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 
 * 
 * @author sdyang
 * @date 2016年2月16日 下午5:24:55
 */
@Component
public class ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserDao userDao;

	public void save(Resource resource) {
		if(resource.getPk_resource() == null){
			resourceDao.insert(resource);
		}else{
			resourceDao.update(resource);
		}
	}

	public void delete(Long pk_resource) {
		resourceDao.delete(pk_resource);
	}

	public Resource findOne(Long pk_resource) {
		return resourceDao.findOne(pk_resource);
	}

	public List<Resource> findAll() {
		return (List<Resource>) resourceDao.findAll();
	}

	

	/**
	 * 
	 * @return
	 * @author sdyang
	 * @date 2016年2月22日 上午9:05:11
	 */
	/*
	public String getResourceTree() {
		String resourceTree;
		Resource result = null;
		List<Resource> resourceList = (List<Resource>) resourceDao.findAll();
		Map<Long, List<Resource>> resourceMap = new HashMap<Long, List<Resource>>();
		List<Resource> childResource = null;
		for (Resource r : resourceList) {			
			if (resourceMap.containsKey(r.getParentId())) {
				childResource = resourceMap.get(r.getParentId());
			}else{
				childResource = new ArrayList<Resource>();
			}
			childResource.add(r);
			resourceMap.put(r.getParentId(), childResource);
		}
		for (Resource r : resourceList) {	
			r.setChildren(resourceMap.get(r.getParentId()));				
		}	
		
		for (Resource r : resourceList) {
			if(r.isRootNode()){
				result = r;
			}
		}
		resourceTree = JSON.toJSONString(result);
		
		return resourceTree;
	}
*/
	public Set<String> findPermissions(Set<Long> resourceIds) {
		Set<String> permissions = new HashSet<String>();
		for (Long resourceId : resourceIds) {
			Resource resource = findOne(resourceId);
			if (resource != null
					&& !StringUtils.isEmpty(resource.getPermission())) {
				permissions.add(resource.getPermission());
			}
		}
		return permissions;
	}

	// public List<Resource> findMenus(Set<String> permissions) {
	// List<Resource> allResources = findAll();
	// List<Resource> menus = new ArrayList<Resource>();
	// for (Resource resource : allResources) {
	// if (resource.isRootNode()) {
	// continue;
	// }
	// if (resource.getType() != Resource.ResourceType.menu) {
	// continue;
	// }
	// if (!hasPermission(permissions, resource)) {
	// continue;
	// }
	// menus.add(resource);
	// }
	// return menus;
	// }

	public List<Resource> findMenu(Set<Long> resourceIds) {
		List<Resource> resources = new ArrayList<Resource>();
		for (Long resourceId : resourceIds) {
			Resource resource = findOne(resourceId);
			if (resource != null
					&& resource.getType() == Resource.ResourceType.menu) {
				resources.add(resource);
			}
		}
		return resources;
	}

	// private boolean hasPermission(Set<String> permissions, Resource resource)
	// {
	// if (StringUtils.isEmpty(resource.getPermission())) {
	// return true;
	// }
	// for (String permission : permissions) {
	// WildcardPermission p1 = new WildcardPermission(permission);
	// WildcardPermission p2 = new WildcardPermission(
	// resource.getPermission());
	// if (p1.implies(p2) || p2.implies(p1)) {
	// return true;
	// }
	// }
	// return false;
	// }

	public String getMenuByRole() throws Exception {

		StringBuffer menu = new StringBuffer();

		String loginid = (String) SecurityUtils.getSubject().getPrincipals()
				.getPrimaryPrincipal();// 当前用户登录id

		User user = userDao.findByLoginid(loginid);// 当前用户

		if (user.getRoleid() == null) {
			throw new Exception("用户没有分配角色！");
		}

		Role role = (Role) roleDao.findOne(user.getRoleid());

		List<Resource> urls = null;

		urls = findMenu(role.getResourceList());

		if (urls == null || urls.size() == 0) {
			return null;
		}

		Map<Long, List<Resource>> urlMap = new HashMap<Long, List<Resource>>();
		for (Resource url : urls) {
			// 如果Map里已经包含了key，则增加url
			if (urlMap.containsKey(url.get_parentId())) {
				List<Resource> l = urlMap.get(url.get_parentId());
				l.add(url);
				urlMap.put(url.get_parentId(), l);
			} else {
				// 否则新增一个list,新增一个key
				List<Resource> l = new ArrayList<Resource>();
				l.add(url);
				urlMap.put(url.get_parentId(), l);
			}
		}

		menu.append("<div class=\"easyui-accordion\" id=\"treeMenu\" data-options=\"fit:true,border:false\">");

		// key为0为所有的父菜单
		List<Resource> parentUrl = urlMap.get(Resource.parentCode);
		if (parentUrl != null && parentUrl.size() > 0) {
			for (Resource p : parentUrl) {
				menu.append("<div title=\"" + p.getName()
						+ "\" style=\"padding:10px;\">");
				menu.append("<ul class=\"easyui-tree\">");
				if (urlMap.containsKey(p.getPk_resource())) {
					// 子菜单
					this.getSubMenu(p.getPk_resource(), urlMap, menu);
				}
				menu.append("</ul>");
				menu.append("</div>");
			}
		}
		menu.append("</div>");
		return menu.toString();
	}

	/**
	 * 组织子菜单
	 * 
	 * @param pk_url
	 * @param urlMap
	 * @param menu
	 * @author sdyang
	 * @date 2015年8月15日 下午12:15:08
	 */
	private void getSubMenu(Long pk_url, Map<Long, List<Resource>> urlMap,
			StringBuffer menu) {

		List<Resource> urlList = urlMap.get(pk_url);
		for (Resource url : urlList) {
			if (urlMap.containsKey(url.getPk_resource())) {
				getSubMenu(url.getPk_resource(), urlMap, menu);
			} else {
				if (url.getUrl() != null) {
					menu.append("<li>");
					menu.append("<span>");
					menu.append("<a href=\"#\" onclick=\"addTab('"
							+ url.getName()
							+ "','"
							+ url.getUrl()
							+ "')\" data-options=\"plain:true\" class=\"easyui-linkbutton\" >"
							+ url.getName() + "</a>");

					menu.append("</span>");
					menu.append("</li>");
				}
			}
		}
	}
}
