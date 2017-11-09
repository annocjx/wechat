package com.cdeledu.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdeledu.common.base.AjaxJson;
import com.cdeledu.common.constants.FilterHelper;
import com.cdeledu.common.constants.GlobalConstants;
import com.cdeledu.common.constants.SystemConstant.SyslogType;
import com.cdeledu.controller.BaseController;
import com.cdeledu.model.rbac.SysUser;
import com.cdeledu.model.system.SysLoginLog;
import com.cdeledu.service.sys.SystemService;
import com.cdeledu.util.ShiroHelper;
import com.cdeledu.util.WebUtilHelper;
import com.cdeledu.util.security.PasswordUtil;

/**
 * @类描述: 登陆初始化控制器
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建日期: 2016年4月4日 下午5:55:06
 * @版本: V1.0
 * @since: JDK 1.7
 */
@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController {
	private static final long serialVersionUID = 1L;
	/** ----------------------------------------------------- Fields start */
	@Autowired
	private SystemService systemService;

	private String logMsg = null;

	/** ----------------------------------------------------- Fields end */
	/**
	 * @方法:登陆验证
	 * @创建人:独泪了无痕
	 * @param user
	 * @return
	 */
	@RequestMapping(params = "checkuser")
	@ResponseBody
	public AjaxJson checkuser(HttpServletRequest request, SysUser user) {
		AjaxJson reslutMsg = new AjaxJson();
		HttpSession session = WebUtilHelper.getSession();
		String imageCaptcha = (String) session.getAttribute(GlobalConstants.IMAGECAPTCHA);
		boolean suc = true;
		SysLoginLog loginLog = new SysLoginLog();

		if (StringUtils.isEmpty(imageCaptcha)
				|| !imageCaptcha.equalsIgnoreCase(user.getImageCaptcha())) {
			logMsg = "验证码错误，请重新输入";
			suc = false;
		} else {
			try {
				String password = PasswordUtil.encrypt(user.getUserName(), user.getPassword());
				AjaxJson loginResult = ShiroHelper.login(user.getUserName(), password);
				String userCode = user.getUserName();
				String logContent = String.valueOf(loginResult.getObj());
				int loginStatus = 0,logLeavel = 0;
				String ipAdd="",browser = "",OpType ="";
				if(loginResult.isSuccess()){
					loginStatus = 1;
					logLeavel = GlobalConstants.Log_Leavel_INFO;
					OpType = SyslogType.Log_Type_LOGIN.getValue();
					session.removeAttribute(GlobalConstants.IMAGECAPTCHA);
				} else {
					
					loginStatus = 0;
				logLeavel = GlobalConstants.Log_Leavel_WARRING;
					OpType = SyslogType.Log_Type_LOGIN.getValue();
					
					logMsg = loginResult.getMsg();
					suc = false;
				}
				
				try {
					loginLog.setUserCode(userCode);
					loginLog.setLogContent(logContent);
					loginLog.setLoginStatus(loginStatus);
					loginLog.setLogLeavel(logLeavel);
					loginLog.setOpType(OpType);
					loginLog.setIpAddress(ipAdd); // 登录的IP地址
					loginLog.setBrowser(browser); // 登录的浏览器
					systemService.addLoginLog(loginLog);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} catch (Exception e) {
				logMsg = "用户名或密码错误,请重新登录!";
				suc = false;
			}
		}
		reslutMsg.setMsg(logMsg);
		reslutMsg.setSuccess(suc);
		return reslutMsg;
	}

	/***
	 * @方法:用户登录
	 * @创建人:独泪了无痕
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "doLogin")
	public String doLogin(HttpServletRequest request) {
		SysUser managerUser = ShiroHelper.getPrincipal();
		// request.getSession().getAttributeNames();
		try {
			if (null != managerUser) {
				return "main/center";
			} else {
				return FilterHelper.LOGIN_SHORT;
			}
		} catch (Exception e) {
			return FilterHelper.LOGIN_SHORT;
		}
	}

	/**
	 * @方法:退出系统
	 * @创建人:独泪了无痕
	 * @return
	 */
	@RequestMapping(params = "doLogout")
	public String doLogout(HttpServletRequest request) {
		// ModelAndView mv = this.getModelAndView();
		HttpSession session = request.getSession();
		// SysUser managerUser = WebUtilHelper.getCurrenLoginUser();
		SysUser currenLoginUser = ShiroHelper.getPrincipal();
		// 判断用户是否为空,不为空,则清空session中的用户object
		if (null != session || currenLoginUser != null) {
			// 注销该操作用户
			session.removeAttribute(GlobalConstants.USER_SESSION);
			ShiroHelper.logout();
			logMsg = "用户" + ShiroHelper.getCurrentUserName() + "已退出";
			// 添加登陆日志
		}
		// mv.setViewName(FilterHelper.LOGIN_SHORT);
		return FilterHelper.LOGIN_SHORT;
	}
}
