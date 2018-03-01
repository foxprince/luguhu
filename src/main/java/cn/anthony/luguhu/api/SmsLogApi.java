package cn.anthony.luguhu.api;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.anthony.luguhu.domain.SmsLog;
import cn.anthony.luguhu.repository.SmsLogRepository;
import cn.anthony.luguhu.service.GenericService;
import cn.anthony.luguhu.util.MLRTSms;
import cn.anthony.luguhu.web.GenericRestController;

@RestController
@RequestMapping(value = "/api/sms")
public class SmsLogApi extends GenericRestController<SmsLog, Long> {
	
	@Autowired
	SmsLogRepository smsLogRepo;
	
	public SmsLogApi(GenericService<SmsLog, Long> service) {
		super(service);
	}
	
	@RequestMapping("/code")
	public JsonResponse send(String phone) {
		String ret = "fail";
		//生成验证码
		String code = createCode();
		String content = "[翡翠湾]您的验证码是"+code+",10分钟内输入有效。";
		SmsLog smsLog = new SmsLog();
		smsLog.setChannel("美联软通");
		smsLog.setCode(code);
		smsLog.setMsg(content);
		smsLog.setPhone(phone);
		smsLog.setUsed(false);
		//发送
		String result = MLRTSms.send(phone, content);
		smsLog.setSendStatus(result);
		if(result.startsWith("success"))
			ret = "success";
		return new JsonResponse(ret);
	}
	
	@RequestMapping("/validCode")
	public JsonResponse validCode(String phone,String code) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -10);
		SmsLog smsLog = smsLogRepo.findByPhoneAndCodeAndUsedAndCtimeGt(phone, code,false,new Timestamp(cal.getTimeInMillis()));
		if(smsLog!=null){
			smsLog.setUsed(true);
			smsLogRepo.save(smsLog);
			return new JsonResponse("success");
		}
		return JsonResponse.fail(105, "短信验证码错误");
	}

	private String createCode() {
		return String.valueOf((int) ((Math.random()*9000)+1000));
	}
}
