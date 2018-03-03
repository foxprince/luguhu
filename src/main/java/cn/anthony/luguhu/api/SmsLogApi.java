package cn.anthony.luguhu.api;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.exceptions.ClientException;

import cn.anthony.luguhu.domain.SmsLog;
import cn.anthony.luguhu.repository.SmsLogRepository;
import cn.anthony.luguhu.service.GenericService;
import cn.anthony.luguhu.util.AliSms;
import cn.anthony.luguhu.util.SmsResult;
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
	public JsonResponse send(String mobile) {
		boolean ret = false;
		//生成验证码
		String code = createCode();
		String content = "验证码"+code+"，您正在尝试变更重要信息，请妥善保管账户信息。";
		SmsLog smsLog = new SmsLog();
		smsLog.setChannel("阿里云短信");
		smsLog.setCode(code);
		smsLog.setMsg(content);
		smsLog.setPhone(mobile);
		smsLog.setUsed(false);
		try {
			//发送
			SmsResult result = AliSms.sendCode(mobile, code);
			logger.info(result.toString());
			smsLog.setSendStatus(result.isResult()+"");
			smsLog.setMsgId(result.getReturnId());
			if(result.isResult())
				ret = true;
		} catch (ClientException e) {
			e.printStackTrace();
		}
		smsLogRepo.saveAndFlush(smsLog);
		logger.info(smsLog.toString());
		if(ret)
			return new JsonResponse(ret);
		else
			return JsonResponse.fail(5, "发送失败");
	}
	
	@RequestMapping("/validCode")
	public JsonResponse validCode(String phone,String code) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -10);
		SmsLog smsLog = smsLogRepo.findByPhoneAndCodeAndUsedAndCtimeGreaterThan(phone, code,false,new Timestamp(cal.getTimeInMillis()));
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
