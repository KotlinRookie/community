package life.majiang.community.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import life.majiang.community.dto.CommentCreateDTO;
import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
		}

		if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
			return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
		}

		Comment comment = new Comment();
		comment.setParentId(commentCreateDTO.getParentId());
		comment.setContent(commentCreateDTO.getContent());
		comment.setType(commentCreateDTO.getType());
		comment.setGmtModified(System.currentTimeMillis());
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setCommentator(user.getId());
		comment.setLikeCount(0);
		commentService.insert(comment, user);
		return ResultDTO.okOf();
	}

	@ResponseBody
	@RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
	public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Integer id) {
		List<CommentDTO> commentDTOs = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
		return ResultDTO.okOf(commentDTOs);
	}
}
