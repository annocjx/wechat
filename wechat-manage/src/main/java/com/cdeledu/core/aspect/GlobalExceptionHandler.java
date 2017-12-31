package com.cdeledu.core.aspect;

import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cdeledu.common.base.AjaxJson;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 * @创建者: 皇族灬战狼
 * @创建时间: 2017年12月9日 下午2:36:42
 * @版本: V1.0
 * @since: JDK 1.7
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	/** ----------------------------------------------------- Fields start */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** ----------------------------------------------------- Fields end */
	/**
	 * 
	 * @方法描述 : 用户未登录
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void unAuth(AuthenticationException e) {
		if (logger.isDebugEnabled()) {
			logger.error("用户未登陆：", e);
		}
	}

	/**
	 * @方法描述 : 账号被冻结
	 * @param e
	 */
	@ExceptionHandler(DisabledAccountException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void accountLocked(AuthenticationException e) {
		if (logger.isDebugEnabled()) {
			logger.error("账号被冻结：", e);
		}
	}

	/**
	 * @方法描述 : 账号密码错误
	 * @param e
	 */
	@ExceptionHandler(CredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void credentials(AuthenticationException e) {
		if (logger.isDebugEnabled()) {
			logger.error("账号密码错误：", e);
		}
	}

	/**
	 * @方法描述 : 无权访问该资源
	 * @param e
	 */
	@ExceptionHandler(UndeclaredThrowableException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public void credentials(UndeclaredThrowableException e) {
		if (logger.isDebugEnabled()) {
			logger.error("无权访问该资源：", e);
		}
	}

	/**
	 * @方法描述 : 服务器未知运行时异常
	 * @param e
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public void notFount(RuntimeException e) {
		if (logger.isDebugEnabled()) {
			logger.error("服务器未知运行时异常:", e);
		}
	}

	/**
	 * @方法描述 : session失效的异常拦截
	 */
	@ExceptionHandler(InvalidSessionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void sessionTimeout(InvalidSessionException e, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.error("session失效的异常拦截:", e);
		}
	}

	/**
	 * @方法描述 : session异常
	 * @param e
	 */
	@ExceptionHandler(UnknownSessionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void sessionTimeout(UnknownSessionException e, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.error("session异常拦截:", e);
		}
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public void handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
		if (logger.isDebugEnabled()) {
			logger.error("HttpMessageNotReadableException", e);
		}
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public void handleValidationException(final ValidationException e) {
		if (logger.isDebugEnabled()) {
			logger.error("ValidationException", e);
		}
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public AjaxJson handleBindException(final BindException e) {
		AjaxJson result = new AjaxJson();
		if (logger.isDebugEnabled()) {
			logger.error("BindException", e);
		}
		result.setSuccess(false);
		result.setMsg(e.getMessage());
		result.setResultCode(400);
		return result;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SQLException.class)
	public AjaxJson handleSQLException(final SQLException e) {

		AjaxJson result = new AjaxJson();
		if (logger.isDebugEnabled()) {
			logger.error("SQLException", e);
		}
		result.setSuccess(false);
		result.setMsg(e.getMessage());
		result.setResultCode(400);
		return result;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public AjaxJson handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
		AjaxJson result = new AjaxJson();
		if (logger.isDebugEnabled()) {
			logger.error("MethodArgumentNotValidException", e);
		}
		result.setSuccess(false);
		result.setMsg(e.getMessage());
		result.setResultCode(400);
		return result;
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public AjaxJson handleHttpRequestMethodNotSupportedException(
			final HttpRequestMethodNotSupportedException e) {
		AjaxJson result = new AjaxJson();
		if (logger.isDebugEnabled()) {
			logger.error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), e);
		}
		result.setSuccess(false);
		result.setMsg(e.getMessage());
		result.setResultCode(405);
		return result;
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public AjaxJson handleHttpMediaTypeNotSupportedException(final Exception e) {
		AjaxJson result = new AjaxJson();
		if (logger.isDebugEnabled()) {
			logger.error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), e);
		}
		result.setSuccess(false);
		result.setMsg(e.getMessage());
		result.setResultCode(415);
		return result;
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public AjaxJson handleException(final Exception e) {
		AjaxJson result = new AjaxJson();
		if (logger.isDebugEnabled()) {
			logger.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
		}
		result.setSuccess(false);
		result.setMsg(e.getMessage());
		result.setResultCode(500);
		return result;
	}
}
