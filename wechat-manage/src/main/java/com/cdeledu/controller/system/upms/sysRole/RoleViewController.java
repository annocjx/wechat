package com.cdeledu.controller.system.upms.sysRole;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cdeledu.common.base.AjaxJson;
import com.cdeledu.controller.BaseController;
import com.cdeledu.model.rbac.SysMenu;
import com.cdeledu.model.rbac.SysRole;
import com.cdeledu.service.sys.RoleService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @类描述: 角色数据
 * <pre>
 * 数据访问权限不在验证，即只要配置对应的访问权限，其数据访问权限除非特别配置，均不再验证。
 * </pre>
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建日期: 2016年4月4日 下午8:13:20
 * @版本: V2.0
 * @since: JDK 1.7
 */
@Controller
@RequestMapping("/roleView")
public class RoleViewController extends BaseController {
	private static final long serialVersionUID = 1L;
	@Autowired
	RoleService roleService;

	/**
	 * @方法:角色列表页面跳转
	 * @创建人:独泪了无痕
	 * @return
	 */
	@RequestMapping(value = "")
	public ModelAndView index(ModelMap modelMap) {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/sysRole/roleInit");
		return mv;
	}

	/**
	 * @方法描述: easyuiAJAX请求数据
	 * @创建者: 皇族灬战狼
	 * @创建时间: 2016年8月8日 下午3:15:19
	 * @param role
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "getList")
	public Map<String, Object> getList(SysRole role, ModelMap modelMap) {
		Map<String, Object> resultMap = Maps.newConcurrentMap();
		try {
			resultMap.put("rows", roleService.findForJdbcParam(role));
			resultMap.put("total", roleService.getCountForJdbcParam(role));
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("rows", null);
			resultMap.put("total", 0);
		}
		return resultMap;
	}

	/**
	 * @方法描述: 检查角色
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "checkRole")
	public AjaxJson checkRole() {
		AjaxJson ajaxJson = new AjaxJson();
		return ajaxJson;
	}

	/**
	 * @方法描述: 我的权限页面
	 * @return
	 */
	@RequestMapping(params = "mypermission", method = RequestMethod.GET)
	public ModelAndView mypermission() {
		return new ModelAndView("permission/mypermission");
	}

	/**
	 * @方法描述: 我的权限 bootstrap tree data
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "getPermissionTree", method = RequestMethod.POST)
	public List<Map<String, Object>> getPermissionTree() {
		List<Map<String, Object>> resultList = Lists.newArrayList();
		// 查询我所有的角色 ---> 权限
		// 把查询出来的roles 转换成bootstarp 的 tree数据
		return resultList;
	}

	/**
	 * @方法描述: 根据角色ID查询权限
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "selectPermissionById")
	public List<SysMenu> selectPermissionById(int roleId) {
		List<SysMenu> sysMenuList = Lists.newArrayList();
		return sysMenuList;
	}
}
