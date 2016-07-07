package com.demo.system.action;

import com.demo.system.bean.Role;
import com.demo.system.service.RoleService;
import com.demo.vo.Grid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 角色管理
 * 
 * @author sdyang
 * @date 2016年2月23日 下午2:54:00
 */
@Controller
public class RoleAction {

	@Autowired
	private RoleService service;

	// 角色页面
	@RequestMapping(value = "/role", method = { RequestMethod.GET })
	public String getRolePage(Model model) {
		return "/system/role";
	}

	// 角色列表
	@RequestMapping(value = "/getRoleList", method = { RequestMethod.GET })
	@ResponseBody
	public Grid getRoleList(Model model) {
		Grid grid = new Grid();
		List<Role> roles = service.findAll();
		grid.setRows(roles);
		grid.setTotal(roles.size());
		return grid;
	}

	// 保存角色
	@RequestMapping(value = "/saveRole", method = { RequestMethod.POST })
	@ResponseBody
	public String saveRole(Role role) {
		service.save(role);
		return "SUCCESS";
	}

	// 删除角色
	@RequestMapping(value = "/deleteRole", method = { RequestMethod.GET })
	@ResponseBody
	public String deleteRole(@RequestParam("id") Long pk_role) {
		service.delete(pk_role);
		return "SUCCESS";
	}

}
