package com.cdeledu.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cdeledu.common.constants.FilterHelper;
import com.cdeledu.common.constants.GlobalConstants;
import com.cdeledu.core.factory.ConstantFactory;
import com.cdeledu.core.shiro.token.ShiroHelper;
import com.cdeledu.model.rbac.SysMenu;
import com.cdeledu.model.rbac.SysUser;
import com.cdeledu.model.rbac.SysUserRole;
import com.cdeledu.service.sys.ManagerUserService;
import com.cdeledu.service.sys.RoleService;
import com.cdeledu.service.sys.SysMenuService;

/**
 * @类描述: 上下文工具类:Web常用工具集，用于获取当前登录用户，请求信息等
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建日期: 2016年4月4日 下午6:31:51
 * @版本: V1.2
 * @since: JDK 1.7
 */
public class WebUtilHelper {
	/** ----------------------------------------------------- Fields start */
	private static ManagerUserService userService = ConstantFactory.userService;
	private static SysMenuService sysMenuService = ConstantFactory.sysMenuService;
	private static RoleService sysRoleService= ConstantFactory.roleService;

	/** ----------------------------------------------------- Fields end */

	/**
	 * @方法:SpringMvc下获取request,尝试获取当前请求的HttpServletRequest实例
	 * @创建人:独泪了无痕
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		try {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @方法:SpringMvc下获取session
	 * @创建人:独泪了无痕
	 * @return
	 */
	public static HttpSession getSession() {
		return getHttpServletRequest().getSession();
	}

	/**
	 * @方法:获取session里的用户对象
	 * @创建人:独泪了无痕
	 * @return
	 */
	public static final SysUser getCurrenLoginUser() {
		HttpSession session = getSession();
		SysUser sysUser = null;
		if (session.getAttributeNames().hasMoreElements()) {
			sysUser = (SysUser) session.getAttribute(GlobalConstants.USER_SESSION);
			if (sysUser != null) {
				return sysUser;
			}
		}
		return sysUser;
	}

	public static void setSessionAttribute(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(String key) {
		return getSession().getAttribute(key);
	}

	/**
	 * @方法描述: 在HttpSession中设置当前登录的用户
	 * @param user
	 * @return 当前登录的用户
	 */
	public static void setCurrentLoginUser(SysUser user) {
		HttpSession session = getSession();
		session.setMaxInactiveInterval(60 * 30);
		session.setAttribute(GlobalConstants.USER_SESSION, user);
	}

	/**
	 * 获取当前用户角色列表
	 */
	public static List<SysUserRole> getRoleList() {
		List<SysUserRole> roleList = null;
		try {
			roleList = userService.getUserRole(getCurrenLoginUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roleList;
	}

	/**
	 * @方法描述: 获取当前获取授权用户id.
	 * @return
	 */
	public static Integer getCurrentUserId() {
		return ShiroHelper.getCurrentUserId();
	}

	/**
	 * @方法描述: 获取当前获取授权用户用户名
	 * @return
	 */
	public static String getCurrentUserName() {
		return ShiroHelper.getCurrentUserName();
	}

	/**
	 * @方法描述: 获取当前获取授权用户真实姓名
	 * @return
	 */
	public static String getCurrentRealName() {
		return ShiroHelper.getCurrentRealName();
	}

	/**
	 * @方法描述: 获取当前获取授权用户昵称
	 * @return
	 */
	public static String getCurrentNickName() {
		return ShiroHelper.getCurrentNickName();
	}

	/**
	 * @方法描述:验证当前用户是否拥有该角色
	 * @param roleCode
	 * @return
	 */
	public static boolean hasRole(String roleCode) {
		return ShiroHelper.hasRole(roleCode);
	}

	/**
	 * 是否拥有该权限
	 * 
	 * @param permission
	 *            权限标识
	 * @return
	 */
	public boolean hasPermission(String permission) {
		return ShiroHelper.hasPermission(permission);
	}

	/**
	 * @方法描述 : 已认证通过的用户。
	 * @return
	 */
	public static boolean isAuthenticated() {
		return ShiroHelper.isAuthenticated();
	}

	/**
	 * @方法描述 : 未认证通过用户
	 * @return
	 */
	public static boolean notAuthenticated() {
		return ShiroHelper.notAuthenticated();
	}

	/**
	 * @方法描述 : 判断当前用户是否是超级管理员
	 * @return
	 */
	public static boolean isAdmin() {
		List<SysUserRole> roleList = getRoleList();
		for (SysUserRole sysUserRole : roleList) {
			if (FilterHelper.ADMIN_ROLE_CODE.equals(sysUserRole.getRoleCode())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前用户授权菜单
	 */
	public static List<SysMenu> getMenuList() {
		try {
			return sysMenuService.getUserMenu(getCurrenLoginUser());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @方法:按照类型获取菜单
	 * @创建人:独泪了无痕
	 * @param key
	 * @return
	 */
	public static List<SysMenu> menuList(Integer key) {
		try {
			if (key == null) {
				key = -1;
			}
			SysMenu sysMenu = new SysMenu();
			sysMenu.setIfVisible(1);
			sysMenu.setType(key);
			sysMenu.setPage(-1);
			return sysMenuService.findForJdbcParam(sysMenu);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 按角色统计用户
	 */
	public static List<SysUserRole> countRoleUser() {
		try {
			return sysRoleService.countRoleUser();
		} catch (Exception e) {
			return null;
		}
	}
}
