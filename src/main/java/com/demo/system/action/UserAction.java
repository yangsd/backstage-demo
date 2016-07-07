package com.demo.system.action;

import com.demo.system.bean.User;
import com.demo.system.service.UserService;
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
 * 用户管理
 * 
 * @author sdyang
 * @date 2016年2月23日 上午10:13:49
 */
@Controller
public class UserAction {

	@Autowired
	private UserService service;

	// 用户页面
	@RequestMapping(value = "/user", method = { RequestMethod.GET })
	public String getUserPage(Model model) {
		return "/system/user";
	}

	// 用户列表
	@RequestMapping(value = "/getUserList", method = { RequestMethod.GET })
	@ResponseBody
	public Grid getUserList(Model model) {
		Grid grid = new Grid();
		List<User> users = service.findAll();
		grid.setRows(users);
		grid.setTotal(users.size());
		return grid;
	}

	// 保存用户
	@RequestMapping(value = "/saveUser", method = { RequestMethod.POST })
	@ResponseBody
	public String saveUser(User user) {
		service.save(user);
		return "SUCCESS";
	}

	// 删除用户
	@RequestMapping(value = "/deleteUser", method = { RequestMethod.GET })
	@ResponseBody
	public String deleteUser(@RequestParam("id") Long pk_user) {
		service.delete(pk_user);
		return "SUCCESS";
	}
	
	//重置密码
	@RequestMapping(value = "/resetpwd", method = { RequestMethod.GET })
	@ResponseBody
	public String resetPassword(@RequestParam("id") Long pk_user) {		
		String password = service.resetPassword(pk_user);
		return password;
	}
}
