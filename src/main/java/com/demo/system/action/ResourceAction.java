package com.demo.system.action;

import com.demo.system.bean.Resource;
import com.demo.system.service.ResourceService;
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
 * 资源管理
 * 
 * @author sdyang
 * @date 2016年2月19日 上午11:14:45
 */
@Controller
public class ResourceAction {
	
	@Autowired
	private ResourceService service;

	// 资源页面
	@RequestMapping(value = "/resource", method = { RequestMethod.GET })
	public String getLoginPage(Model model) {
		return "/system/resource";
	}

	//资源列表
	@RequestMapping(value = "/getResourceList", method = { RequestMethod.GET })
	@ResponseBody
	public Grid getResourceList(Model model) {
		
		Grid grid = new Grid();
		List<Resource> resourceList = service.findAll();
		grid.setRows(resourceList);
		grid.setTotal(resourceList.size());
		return grid;
	}
	
	//保存资源
	@RequestMapping(value = "/saveResource", method = { RequestMethod.POST })
	@ResponseBody
	public String saveResource(Resource resource) {
		resource.set_parentId(resource.getParentId());
		service.save(resource);
		return "SUCCESS";
	}
	
	//根据主键获取资源
//	@RequestMapping(value = "/getResourceById", method = { RequestMethod.GET })
//	@ResponseBody
//	public Resource getResourceById(@RequestParam("id") Long pk_resource) {
//		Resource resource = service.findOne(pk_resource);
//		return resource;
//	}
	
	//删除资源
	@RequestMapping(value = "/deleteResource", method = { RequestMethod.GET })
	@ResponseBody
	public String deleteResource(@RequestParam("id") Long pk_resource) {
		service.delete(pk_resource);
		return "SUCCESS";
	}

}
