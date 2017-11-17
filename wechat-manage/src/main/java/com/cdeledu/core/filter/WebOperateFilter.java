package com.cdeledu.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.cdeledu.common.base.BaseClass;

/**
 * @类描述: 操作日志。存储在mongodb中
 * @创建者: 皇族灬战狼
 * @创建时间: 2017年1月18日 上午8:00:25
 * @版本: V2.0
 * @since: JDK 1.7
 */
@WebFilter(filterName = "webOperateFilter", urlPatterns = { "*.shtml" })
public class WebOperateFilter extends BaseClass implements Filter {

	private static final long serialVersionUID = 1L;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		logger.debug("Access Auth Interceptor - 进入过滤器");
		try {

			// BasicDBObject dbb = new BasicDBObject();
			// dbb.put("url", operaterUrl);
			// dbb.put("param", params.toString());
			// MongoConstants.mongoQueue.add(dbb);
		} finally {
			filterChain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {

	}

}
