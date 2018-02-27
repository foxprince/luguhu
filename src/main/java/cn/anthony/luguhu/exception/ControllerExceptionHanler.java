package cn.anthony.luguhu.exception;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import cn.anthony.luguhu.api.JsonResponse;

@ControllerAdvice
public class ControllerExceptionHanler {
	private static Logger logger = LoggerFactory.getLogger(ControllerExceptionHanler.class);
	private static final List<String> EXPOSABLE_FIELDS = Arrays.asList("timestamp", "status", "error", "message", "path");
    @Autowired private ErrorAttributes errorAttributes;
    
	//@ExceptionHandler(value = Exception.class)
	//@ResponseBody
	public ResponseEntity<JsonResponse> handleException(Exception exception, HttpServletRequest request) {
		logger.error("系统异常:" + exception.getMessage());
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Map<String, Object> body = this.getErrorAttributes(request);
		return new ResponseEntity<JsonResponse>(new JsonResponse(500,"系统异常",body), status);
	}

	@ExceptionHandler(value = SQLException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<String> handleSQLException(Exception exception, HttpServletRequest request) {
		logger.error("数据库错误!", exception);
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
		WebRequest requestAttributes = new ServletWebRequest(request);
		final boolean WITHOUT_STACK_TRACE = false;
		Map<String, Object> attributes = errorAttributes.getErrorAttributes(requestAttributes, WITHOUT_STACK_TRACE);
		// log exception before removing it
		attributes.keySet().removeIf(key -> !EXPOSABLE_FIELDS.contains(key));
		return attributes;
	}
}
