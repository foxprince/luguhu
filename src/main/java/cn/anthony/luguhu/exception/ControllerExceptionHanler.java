package cn.anthony.luguhu.exception;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.anthony.luguhu.api.JsonResponse;

@ControllerAdvice
public class ControllerExceptionHanler {
	private static Logger logger = LoggerFactory.getLogger(ControllerExceptionHanler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseEntity<JsonResponse> handleException(Exception exception, HttpServletRequest request) {
		//logger.error("系统异常:", exception);
		return new ResponseEntity<JsonResponse>(new JsonResponse("系统异常"),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = SQLException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity handleSQLException(Exception exception, HttpServletRequest request) {
		logger.error("数据库错误!", exception);
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
