package com.cdeledu.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.cdeledu.common.base.BaseClass;
import com.cdeledu.common.constants.FilterHelper;
import com.cdeledu.common.constants.GlobalConstants;
import com.cdeledu.util.WebUtilHelper;

/**
 * @类描述: 登录过滤器,验证是否登录
 * @创建者: 皇族灬战狼
 * @创建时间: 2016年5月26日 下午2:28:49
 * @版本: V2.0
 * @since: JDK 1.7
 */
//@WebFilter(filterName = "LoginFilter", urlPatterns = { "*.shtml" })
public class AjaxAuthenticationFilter extends BaseClass implements Filter {
	/** ----------------------------------------------------- Fields start */
	private static final long serialVersionUID = 1L;

	/** ----------------------------------------------------- Fields end */
	@Override
	public void destroy() {

	}

	// ② 执行过滤
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		logger.info("用于检查用户是否登录了系统，如果未登录，则重定向到指的登录页面。");
		// ②-1 保证该过滤器在一次请求中只被调用一次
		if (null != request && null != request.getAttribute(GlobalConstants.FILTERED_REQUEST)) {
			
		} else {
			// ②-2 设置过滤标识，防止一次请求多次过滤
			request.setAttribute(GlobalConstants.FILTERED_REQUEST, Boolean.TRUE);
			// ②-3 用户未登录, 且当前URI资源需要登录才能访问
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			HttpSession session = WebUtilHelper.getSession();
			
			if ((null == session || null == WebUtilHelper.getCurrenLoginUser())
					&& FilterHelper.isURILogin( httpRequest)) {
				// ②-4将用户的请求URL保存在session中，用于登录成功之后，跳到目标URL
				String toUrl = httpRequest.getRequestURL().toString();
				if (!StringUtils.isEmpty(httpRequest.getQueryString())) {
					toUrl += "?" + httpRequest.getQueryString();
				}
				logger.info("登录跳转页面" + toUrl);
				// ②-5转发到登录页面
				request.getRequestDispatcher(httpRequest.getContextPath() + FilterHelper.LOGIN).forward(request,
						response);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
