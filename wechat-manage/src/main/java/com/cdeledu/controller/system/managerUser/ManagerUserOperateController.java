package com.cdeledu.controller.system.managerUser;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cdeledu.common.base.AjaxJson;
import com.cdeledu.controller.BaseController;
import com.cdeledu.model.rbac.SysUser;
import com.cdeledu.model.rbac.SysUserRole;
import com.cdeledu.service.sys.ManagerUserService;

/**
 * @类描述:管理员
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建日期: 2016年4月4日 下午9:02:29
 * @版本: V1.0
 * @since: JDK 1.7
 */
@Controller
@RequestMapping("/managerUserOperate")
@SessionAttributes("managerUser")
public class ManagerUserOperateController extends BaseController {
	private static final long serialVersionUID = 1L;
	/** ----------------------------------------------------- Fields start */
	@Autowired
	private ManagerUserService manageruserService;
	private String logMsg = null;

	/** ----------------------------------------------------- Fields end */
	/**
	 * @方法描述: 用户注册
	 * @param managerUser
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "singUp")
	public AjaxJson singUp(SysUser managerUser, HttpServletRequest request) {
		AjaxJson resultMsg = new AjaxJson();
		logMsg = "用户: " + managerUser.getUserName();
		try {
			manageruserService.insert(managerUser);
			logMsg += ",注册(创建)成功";

		} catch (Exception e) {
			e.printStackTrace();
			logMsg += ",在被创建之时出现异常,其原因是" + e.getMessage();
			resultMsg.setSuccess(false);
		}
		resultMsg.setMsg(logMsg);
		return resultMsg;
	}

	/**
	 * @方法描述: 用户-角色录入
	 * @创建者: 皇族灬战狼
	 * @创建时间: 2016年9月27日 下午4:51:27
	 * @param managerUser
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "saveRoleUser")
	public AjaxJson saveRoleUser(SysUser managerUser, HttpServletRequest request,
			@RequestParam(value = "roleID", defaultValue = "1", required = false) int roleID) {
		AjaxJson resultMsg = new AjaxJson();
		SysUser user = new SysUser();
		user.setUserName(managerUser.getUserName());
		logMsg = "用户: " + managerUser.getUserName();
		try {
			SysUser TSUser = manageruserService.findOneForJdbc(user);
			if (null != TSUser) {
				logMsg += " 不存在";
				resultMsg.setSuccess(false);
			} else {
				int userId = manageruserService.insert(managerUser);
				logMsg += " 添加成功";

				if (StringUtils.isNotEmpty(String.valueOf(roleID))) {
					SysUserRole managerUserRole = new SysUserRole();
					managerUserRole.setUserId(userId);
					managerUserRole.setRoleId(roleID);
					manageruserService.saveRoleUser(managerUserRole);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logMsg += " 分配角色时出现异常,其异常原因是" + e.getMessage();
			resultMsg.setSuccess(false);
		}
		resultMsg.setMsg(logMsg);
		return resultMsg;
	}

	/**
	 * @方法描述: 用户更新
	 * @创建者: 皇族灬战狼
	 * @创建时间: 2016年9月27日 下午4:51:23
	 * @param managerUser
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "updateUser")
	public AjaxJson updateUser(SysUser managerUser, HttpServletRequest request) {
		AjaxJson resultMsg = new AjaxJson();
		logMsg = "用户: " + managerUser.getUserName();
		try {
			managerUser.setUserName(null);// 不能更新username
			manageruserService.update(managerUser);
			logMsg += "更新成功";
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg.setSuccess(false);
			logMsg += "更新时出现异常,其异常原因时:" + e.getMessage();
		}
		resultMsg.setMsg(logMsg);
		return resultMsg;
	}

	/**
	 * @方法描述: 用户删除
	 * @创建者: 皇族灬战狼
	 * @创建时间: 2016年9月27日 下午4:51:19
	 * @param managerUser
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "deleteUser")
	public AjaxJson delUser(@RequestParam(value = "id", required = true) Integer id) {
		AjaxJson resultMsg = new AjaxJson();
		logMsg = "删除用户ID: " + id + " 的用户";
		try {
			SysUser managerUser = new SysUser();
			managerUser.setId(id);
			manageruserService.delete(managerUser);
			logMsg += ",删除成功";
		} catch (Exception e) {
			e.printStackTrace();
			resultMsg.setSuccess(false);
			logMsg += ",删除失败，其异常原因是:" + e.getMessage();
		}
		return resultMsg;
	}

	/**
	 * @方法描述: 用户删除(批量)
	 * @创建者: 皇族灬战狼
	 * @创建时间: 2016年9月27日 下午4:51:19
	 * @param delIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "bathDelUser")
	public AjaxJson BathDelUser(@RequestParam(value = "delIds", required = true) String delIds) {
		AjaxJson reslutMsg = new AjaxJson();
		if (StringUtils.isNotBlank(delIds)) {
			List<Integer> ids = Arrays.asList();
			for (String id : delIds.split(",")) {
				try {
					ids.add(Integer.parseInt(id));
				} catch (Exception e) {
					continue;
				}
			}
			try {
				// manageruserService.deleteByPrimaryKey(ids);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return reslutMsg;
	}
}
