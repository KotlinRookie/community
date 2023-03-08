package life.majiang.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import life.majiang.community.dto.PaginationDTO;
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
	
	public PaginationDTO list(Integer page, Integer size) {
		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalCount = questionMapper.count();
		paginationDTO.setPagination(totalCount,page,size);
		if(page<1) {
			page = 1;
		}
		if(page>paginationDTO.getTotalPage()) {
			page = paginationDTO.getTotalPage();
		}
		
		//size*(page-1)
		Integer offset = size * (page-1);
		
		List<Question> questions = questionMapper.list(offset,size);
		List<QuestionDTO> questionDTOList = new ArrayList<>();
		
		for (Question question : questions) {
			User user = userMapper.finById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			
			//使用BeanUtils.copyProperties，快速把question的属性拷贝到questionDTO中
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		
		paginationDTO.setQuestions(questionDTOList);
		return paginationDTO;
	}

	public PaginationDTO list(Integer userId, Integer page, Integer size) {
		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalCount = questionMapper.countByUserId(userId);
		paginationDTO.setPagination(totalCount,page,size);
		if(page<1) {
			page = 1;
		}
		if(page>paginationDTO.getTotalPage()) {
			page = paginationDTO.getTotalPage();
		}
		
		//size*(page-1)
		Integer offset = size * (page-1);
		
		List<Question> questions = questionMapper.listByUserId(userId,offset,size);
		List<QuestionDTO> questionDTOList = new ArrayList<>();
		
		for (Question question : questions) {
			User user = userMapper.finById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			
			//使用BeanUtils.copyProperties，快速把question的属性拷贝到questionDTO中
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		
		paginationDTO.setQuestions(questionDTOList);
		return paginationDTO;
	}

	public QuestionDTO getById(Integer id) {
		Question question = questionMapper.getById(id);
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		User user = userMapper.finById(question.getCreator());
		questionDTO.setUser(user);
		return questionDTO;
	}

}
