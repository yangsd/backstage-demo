package com.demo.system.action;
import com.demo.system.bean.User;
import com.demo.system.service.ResourceService;
import com.demo.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录Controller
 * 
 * @author sdyang
 * @date 2016年2月16日 下午2:57:17
 */
@Controller
public class LoginAction {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ResourceService resourceService;

	// 登录页面
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String getLoginPage(Model model) {
		return "login";
	}
	
	//首页
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public ModelAndView getIndexPage(Model model) throws Exception {
		String menuHtml = resourceService.getMenuByRole();
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("menu", menuHtml);
		return mav;
	}
	
	//登录
	@RequestMapping(value = "/toLogin", method = { RequestMethod.POST })
	public String toLogin(User user) {
		
		String url = "redirect:/index";
		
		SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
		// 登录后存放进shiro token
		UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginid(),
				user.getPassword());
		Subject subject = SecurityUtils.getSubject();
		String msg = null;
		try {
			subject.login(token);
		} catch (UnknownAccountException ue) {
			msg = "用户名/密码错误";
		} catch (IncorrectCredentialsException ie) {
			msg = "用户名/密码错误";
		}catch  (LockedAccountException le){
			msg = "用户被锁定";
		}catch(ExcessiveAttemptsException ee){
			msg = "您尝试登录的次数过多，请10分钟后再试！";
		}
		
		if(msg != null){
			//model.addAttribute("msg", msg);
			url= "login";
		}else{
			logger.info(msg);
		}
		
		return url;		
	}
	
	//注册用户
	@RequestMapping(value = "/toRegister", method = { RequestMethod.POST })
	public String toRegister(User user) {
		service.save(user);		
		return "login";		
	}
	
	//找回密码
	@RequestMapping(value = "/toFindPassword", method = { RequestMethod.GET })
	public String toFindPassword(@RequestParam String email) {
		return "login";
	}
	
}
