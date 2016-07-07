package com.demo.system.bean;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统角色
 * 
 * @author sdyang
 * @date 2016年1月23日 下午5:37:58
 */
public class Role {

	private Long pk_role; // 主键


	private String role; // 角色标识 程序中判断使用,如"admin"


	private String description; // 角色描述


	private String resourceIds;


	private Set<Long> resourceList; // 拥有的资源


	private Boolean status = Boolean.FALSE; // 是否可用

	public Role() {
	}

	public Role(String role, String description, Boolean available) {
		this.role = role;
		this.description = description;
		this.status = available;
	}

	public Long getPk_role() {
		return pk_role;
	}

	public void setPk_role(Long pk_role) {
		this.pk_role = pk_role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public Set<Long> getResourceList() {
		if (resourceList == null) {
			resourceList = new HashSet<Long>();
		}
		if (!StringUtils.isEmpty(getResourceIds())) {
			String[] array = getResourceIds().split(",");
			for (String resourceIdStr : array) {
				if (StringUtils.isEmpty(resourceIdStr)) {
					continue;
				}
				resourceList.add(Long.valueOf(resourceIdStr));
			}
		}
		return resourceList;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
