/**
 * 扩展javsScript工具函数
 */
var dllwh = dllwh || {};
dllwh.data = dllwh.data || {};// 用于存放临时的数据或者对象

/**
 * 回调函数 
 */
var callbackFunHandler = function(){
	
}
/**
 * 全局配置
 */
$.ajaxSetup({
	dataType: "json",
	cache: false,
	complete:function(XMLHttpRequest,textStatus){
		// 通过XMLHttpRequest取得响应头，sessionstatus， 
		var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); 
		if (sessionstatus == "timeout") { 
			// 如果超时就处理 ，指定要跳转的页面(比如登陆页)
			top.layer.open({
				title: '系统提示',
				area: '338px',
				icon: 3,
				move: false,
				anim: -1,
				isOutAnim: false,
				content: '注：长时间未操作,请稍后重新登录.',
				btn: ['立即退出'],
				btnAlign: 'c',
				yes: function(){
					dllwh.gotoUrl("${_currConText}/loginController.shtml?doLogin");
				}
			});
		}else if(textStatus=="timeout"){
			dialogAlert("请求超时，请稍候重试...", "error");
		} else if(textStatus=="error"){
			dialogAlert("错误请求，请稍候重试...", "error");
		} else if(textStatus=="notmodified"){
			dialogAlert("错误请求，无法完成此操作", "error");
		} else if(textStatus=="parsererror"){
			dialogAlert("网络问题，请稍候重试...", "error");
		}
		callbackFunHandler();
	}
});

/**
 * js获取项目根路径
 */
dllwh.getRootPath = function() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName) + "/";
}

/**
 * 判断是否为空 不是一个空对象，或者未定义，或者不等于空字符串
 */
dllwh.isNullOrEmpty = function(obj) {
	if ((typeof (obj) == "string" && obj == "") || obj == null
			|| typeof (obj) == undefined) {
		return true;
	} else {
		return false;
	}
}

dllwh.isNotNullOrEmpty = function(obj) {
	if ((typeof (obj) == "string" && obj == "") || obj == null
			|| obj == undefined) {
		return false;
	} else {
		return true;
	}
}

// 时间设置
dllwh.currentTime = function() {
	var d = new Date(), str = '';
	str += d.getFullYear() + '年';
	str += d.getMonth() + 1 + '月';
	str += d.getDate() + '日';
	str += d.getHours() + '时';
	str += d.getMinutes() + '分';
	str += d.getSeconds() + '秒';
	return str;
}
/** 设置cookie */
dllwh.setCookie = function(name, value) {
	var exp = new Date();
	var tommorrow = new Date();
	tommorrow.setDate(tommorrow.getDate() + 1);
	tommorrow.setHours(0);
	tommorrow.setMinutes(0);
	tommorrow.setSeconds(0);
	tommorrow.setMilliseconds(0);
	exp.setTime(tommorrow.getTime()); // 明天0点过期
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}
/** 获取cookie */
dllwh.getCookie = function(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return "";
}

/** 删除cookies */
dllwh.delCookie = function(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}
/** 页面跳转 */
dllwh.gotoUrl = function(href) {
	window.location.href = href;
}

/** 毫秒数转日期格式 */
dllwh.genStrDateTime = function(data){
	if(data == null){
		return "";
	}else{
		var c = new Date(); 
		c.setTime(data);
		var mon = c.getMonth()+1;
		if(mon<10){
			mon = "0" + mon;
		}
		var dat = c.getDate();
		if(dat<10){
			dat = "0" + dat;
		}
		return c.getFullYear()+"-"+mon+"-"+dat+" "+c.getHours()+":"+c.getMinutes()+":"+c.getSeconds();
	}
}

/** 是否存在指定函数  */
dllwh.isExitsFunction = function (funcName){
	try {
		if (typeof(eval(funcName)) == "function") {
			return true;
		}
	} catch (error) {
		
	}
	return false;
}


/** 是否存在指定变量 */
dllwh.isExitsVariable = function (variableName){
	try {
		if (typeof(variableName) == "undefined") {
			// alert("value is undefined"); 
			return false;
		} else {
			// alert("value is true"); 
			return true;
		}
	} catch (error) {
		
	}
	return false;
}