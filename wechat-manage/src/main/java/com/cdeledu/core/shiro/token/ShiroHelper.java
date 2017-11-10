package com.cdeledu.core.shiro.token;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;

import com.cdeledu.common.base.AjaxJson;
import com.cdeledu.model.rbac.SysUser;

public class ShiroHelper {
	/** ----------------------------------------------------- Fields start */
	/** ----------------------------------------------------- Fields end */

	public static AjaxJson login(String userName, String passWord) {
		// 用户名密码令牌
		UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
		token.setRememberMe(false);
		String logMsg = "", resultMsg = "";
		AjaxJson ajaxJson = new AjaxJson();
		boolean suc = false;

		// 获得当前登录用户对象Subject，现在状态为 “未认证”
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (UnknownAccountException uae) {
			logMsg = "对用户[" + userName + "]进行登录验证..验证未通过,未知账户";
			resultMsg = "该账号不存在!";
		} catch (IncorrectCredentialsException ice) {
			logMsg = "对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证";
			resultMsg = "用户名或密码错误,请重新登录!";
		} catch (LockedAccountException lae) {
			logMsg = "对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定";
			resultMsg = "验证未通过,账户已锁定";
		} catch (DisabledAccountException dae) {
			logMsg = "对用户[" + userName + "]进行登录验证..验证未通过,帐号已被禁用";
			resultMsg = ".验证未通过,帐号已被禁用";
		} catch (ExpiredCredentialsException ece) {
			logMsg = "对用户[" + userName + "]进行登录验证..验证未通过,帐号已过期";
			resultMsg = "验证未通过,帐号已过期";
		} catch (ExcessiveAttemptsException eae) {
			logMsg = "对用户[" + userName + "]进行登录验证..验证未通过,用户名或密码错误次数过多";
			resultMsg = "验证未通过,用户名或密码错误次数过多";
		} catch (UnauthorizedException e) {
			logMsg = "对用户[" + userName + "]进行登录验证..验证未通过,您没有得到相应的授权！";
			resultMsg = "验证未通过,您没有得到相应的授权！";
		} catch (AuthenticationException ae) {
			logMsg = "对用户[" + userName + "]进行登录验证..验证未通过," + ae.getMessage();
			resultMsg = "进行登录验证..验证未通过";
		}

		if (subject.isAuthenticated()) {
			logMsg = "对用户[" + userName + "]进行登录验证..验证通过";
			suc = true;
		} else {
			token.clear();
		}

		ajaxJson.setSuccess(suc);
		ajaxJson.setMsg(resultMsg);
		ajaxJson.setObj(logMsg);
		return ajaxJson;
	}

	/**
	 * @方法描述: 获取当前获取授权用户信息
	 * @return
	 */
	public static SysUser getPrincipal() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * @方法描述: 获取当前获取授权用户id.
	 * @return
	 */
	public static Integer getCurrentUserId() {
		SysUser user = getPrincipal();
		if (user != null) {
			return user.getId();
		} else {
			return null;
		}
	}

	/**
	 * @方法描述: 获取当前获取授权用户用户名
	 * @return
	 */
	public static String getCurrentUserName() {
		SysUser user = getPrincipal();
		if (user != null) {
			return user.getUserName();
		} else {
			return "";
		}
	}

	/**
	 * @方法描述: 获取当前获取授权用户真实姓名
	 * @return
	 */
	public static String getCurrentRealName() {
		SysUser user = getPrincipal();
		if (user != null) {
			return user.getRealName();
		} else {
			return "";
		}
	}

	/**
	 * @方法描述: 获取当前获取授权用户昵称
	 * @return
	 */
	public static String getCurrentNickName() {
		SysUser user = getPrincipal();
		if (user != null) {
			return user.getNickName();
		} else {
			return "";
		}
	}

	/**
	 * 判断是否登录
	 * 
	 * @return
	 */
	public static boolean isLogin() {
		return null != SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 退出登录
	 */
	public static void logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout();
		}
	}
	
	/**
	 * @方法描述: 是否拥有该角色
	 * @param roleCode
	 * @return
	 */
	public static boolean hasRole(String roleCode){
		Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.hasRole(roleCode);
	} 
	
	/**
	 * 是否拥有该权限
	 * @param permission  权限标识
	 * @return
	 */
	public boolean hasPermission(String permission) {
		Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.isPermitted(permission);
	}
}
