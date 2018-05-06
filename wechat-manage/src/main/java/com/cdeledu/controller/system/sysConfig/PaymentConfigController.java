package com.cdeledu.controller.system.sysConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cdeledu.controller.BaseController;
import com.cdeledu.service.pay.PaymentService;

/**
 * 
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 支付配置管理
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建时间: 2018年5月1日 下午5:59:51
 * @版本: V1.0
 * @since: JDK 1.7
 */
@Controller
@RequestMapping("system/payment/config")
public class PaymentConfigController extends BaseController {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	PaymentService paymentService;
	
	@RequestMapping("")
	public ModelAndView index(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/payment/config");
		try {
			mv.addObject("paymentConfigList", paymentService.getPaymentConfigList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * @方法:支付详细信息
	 * @创建人:独泪了无痕
	 * @return
	 */
	@RequestMapping(value="details")
	public ModelAndView details(Integer ownerId){
		ModelAndView mv = this.getModelAndView();
		try {
			mv.addObject("paymentConfig", paymentService.getPaymentConfigInfo(ownerId));
			mv.addObject("paymentBank", paymentService.getPaymentConfigBank(ownerId));
			mv.addObject("notExistPayBank", paymentService.getNotExistPaymentBank(ownerId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("system/payment/details");
		return mv;
	}
}
