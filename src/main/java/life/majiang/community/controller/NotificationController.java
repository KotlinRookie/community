package life.majiang.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import life.majiang.community.dto.NotificaitonDTO;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.model.User;
import life.majiang.community.service.NotificationService;

@Controller
public class NotificationController {
	@Autowired
	private NotificationService notificationService;

	@GetMapping("/notification/{id}")
	public String profile(HttpServletRequest request, @PathVariable(name = "id") Integer id) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "redirect:/";
		}
		NotificaitonDTO notificaitonDTO = notificationService.read(id, user);

		if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificaitonDTO.getType()
				|| NotificationTypeEnum.REPLY_QUESTION.getType() == notificaitonDTO.getType()) {
			return "redirect:/question/" + notificaitonDTO.getOuterid();
		} else {
			return "redirect:/";
		}
	}
}
