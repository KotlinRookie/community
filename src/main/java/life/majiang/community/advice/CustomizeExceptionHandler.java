package life.majiang.community.advice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;

/*
 * 定制异常处理
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

	@ExceptionHandler(Exception.class)
	ModelAndView handle(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response) {
		String contentType = request.getContentType();
		if ("application/json".equals(contentType)) {
			ResultDTO resultDTO = null;
			// 返回json
			if (e instanceof CustomizeException) {
				resultDTO = ResultDTO.errorOf((CustomizeException) e);
			} else {
				resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
			}
			
			try {
				response.setContentType("application/json");
				response.setStatus(200);
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(JSON.toJSONString(resultDTO));
				writer.close();
			} catch (IOException ioe) {
			}
			return null;
		} else {
			// 错误页面跳转
			if (e instanceof CustomizeException) {
				model.addAttribute("message", e.getMessage());
			} else {
				model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
			}
			return new ModelAndView("error");
		}
	}
}
