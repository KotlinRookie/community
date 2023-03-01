package life.majiang.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;

@Service
public class QuestionService {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private UserMapper userMapper;
	
	public List<QuestionDTO> list() {
		List<Question> questions = questionMapper.list();
		List<QuestionDTO> questionDTOList = new ArrayList<>();
		for (Question question : questions) {
			User user = userMapper.finById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			//使用BeanUtils.copyProperties，快速把question的属性拷贝到questionDTO中
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		
		return questionDTOList;
	}

}
