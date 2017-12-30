package com.cdeledu.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cdeledu.common.base.BaseClass;
import com.cdeledu.common.constants.GlobalConstants;
import com.cdeledu.core.interceptors.DateConvertEditor;
import com.cdeledu.util.network.IpUtilHelper;

import nl.bitwalker.useragentutils.UserAgent;

/**
 * @类描述:
 * 
 *       <pre>
 * 基础控制器
 * 其他控制器需集成此控制器获得initBinder自动转换的功能以及防止XSS攻击的功能
 *       </pre>
 * 
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建日期: 2016年4月4日 下午2:28:19
 * @版本: V1.2
 * @since: JDK 1.7
 */
@Controller
public class BaseController extends BaseClass {
	/** ----------------------------------------------------- Fields start */
	private static final long serialVersionUID = 1L;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/** 浏览器的地址栏会变 */
	protected static String REDIRECT = "redirect:";
	/** 浏览器的地址栏不会变,但是有视图返回来 */
	protected static String FORWARD = "forward:";

	/** ----------------------------------------------------- Fields end */
	/**
	 * @方法:将前台传递过来的日期格式的字符串,自动转化为Date类型
	 * @创建人:独泪了无痕
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * @方法描述 : 404页面跳转
	 * @return
	 */
	public ModelAndView redirect404() {
		return new ModelAndView(new RedirectView(GlobalConstants.ERROR_PAGE_404));
	}

	/**
	 * @方法描述 : 跳转
	 * @param redirectUrl
	 * @param parament
	 * @return
	 */
	public ModelAndView redirect(String redirectUrl, Map<String, Object> parament) {
		ModelAndView view = new ModelAndView(new RedirectView(redirectUrl));
		if (null != parament && parament.size() > 0) {
			view.addAllObjects(parament);
		}
		return view;
	}

	/**
	 * @方法描述 : 获取用户IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		return IpUtilHelper.getClientIP(request);
	}

	/**
	 * @方法描述 : 获取用户浏览器
	 * @param request
	 * @return
	 */
	public static String getBrowser(HttpServletRequest request) {
		return UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser()
				.getName();
	}
}
