package com.demo.system.bean;

/**
 * 系统资源
 * 
 * @author sdyang
 * @date 2016年2月16日 下午4:34:49
 */
public class Resource {


	private Long pk_resource; // 编号


	private String name; // 资源名称


	private ResourceType type = ResourceType.menu; // 资源类型


	private String url; // 资源路径


	private String permission; // 权限字符串


	private Long _parentId;//父节点


	private Long parentId;


	private Boolean status = Boolean.FALSE;//状态

	public static enum ResourceType {
		menu("菜单"), button("按钮"), root("根节点");

		private final String info;

		private ResourceType(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}

	/**
	 * 父编号
	 */
	public static Long parentCode = (long) 0;

	public Long getPk_resource() {
		return pk_resource;
	}

	public void setPk_resource(Long pk_resource) {
		this.pk_resource = pk_resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Long get_parentId() {
		return _parentId;
	}

	public void set_parentId(Long _parentId) {
		this._parentId = _parentId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public boolean isRootNode() {
		return this.pk_resource == Resource.parentCode;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
