package com.cdeledu.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cdeledu.common.mapper.JsonMapper;
import com.cdeledu.core.factory.ConstantFactory;
import com.cdeledu.core.listener.DictListener;
import com.cdeledu.model.system.SysArea;
import com.cdeledu.model.system.SysDict;
import com.cdeledu.service.sys.SysAreaService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @类描述: 字典工具类
 * @创建者: 皇族灬战狼
 * @创建时间: 2017年2月24日 下午12:15:38
 * @版本: V1.0
 * @since: JDK 1.7
 */
public class DictUtils {
	/** ----------------------------------------------------- Fields start */
	public static final String CACHE_DICT_MAP = "dictMap";
	private static SysAreaService sysAreaService = ConstantFactory.sysAreaService;

	/** ----------------------------------------------------- Fields end */

	/** ----------------------------------------------- [私有方法] */
	/** ----------------------------------------------- [私有方法] */

	/**
	 * @方法描述: 获取字典值
	 * @param label
	 * @param type
	 * @param defaultLabel
	 * @return
	 */
	public static String getDictValue(String code, String defaultValue) {
		if (StringUtils.isNotBlank(code)) {
			for (SysDict dict : DictListener.dictsList) {
				if (code.equals(dict.getItemCode())) {
					return dict.getItemName();
				}
			}
		}
		return defaultValue;
	}

	/**
	 * @方法描述: 获取字典对象列表
	 * @param type
	 * @return
	 */
	public static List<SysDict> getDictList(String type) {
		Map<String, List<SysDict>> dictMap = null;
		if (dictMap == null) {
			dictMap = Maps.newHashMap();
		}
		List<SysDict> dictList = dictMap.get(type);
		if (dictList == null) {
			dictList = Lists.newArrayList();

		}
		return dictList;
	}

	/**
	 * @方法描述: 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type) {
		return JsonMapper.toJsonString(getDictList(type));
	}

	/**
	 * @方法描述 : 获取行政区域的树形菜单
	 * @return
	 */
	public static String getSysAreaTree() {
		try {
			return JsonMapper.toJsonString(sysAreaService.getSysAreaTree());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @方法描述 : 获取行政区域
	 * @return
	 */
	public static List<SysArea> getProvinceArea() {
		try {
			return sysAreaService.getArealistByParentCode(100000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
