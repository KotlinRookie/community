package life.majiang.community.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.model.User;

@Service
public class QuestionService {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionExtMapper questionExtMapper;
	@Autowired
	private UserMapper userMapper;

	public PaginationDTO list(Integer page, Integer size) {
		PaginationDTO paginationDTO = new PaginationDTO();

		Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());

		paginationDTO.setPagination(totalCount, page, size);
		if (page < 1) {
			page = 1;
		}
		if (page > paginationDTO.getTotalPage()) {
			page = paginationDTO.getTotalPage();
		}

		// size*(page-1)
		Integer offset = size * (page - 1);
		QuestionExample questionExample = new QuestionExample();
		questionExample.setOrderByClause("gmt_create desc");
		List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,
				new RowBounds(offset, size));
		List<QuestionDTO> questionDTOList = new ArrayList<>();

		for (Question question : questions) {
			User user = userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();

			// 使用BeanUtils.copyProperties，快速把question的属性拷贝到questionDTO中
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}

		paginationDTO.setQuestions(questionDTOList);
		return paginationDTO;
	}

	public PaginationDTO list(Integer userId, Integer page, Integer size) {
		PaginationDTO paginationDTO = new PaginationDTO();

		QuestionExample questionExample = new QuestionExample();
		questionExample.createCriteria().andCreatorEqualTo(userId);
		Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());

		paginationDTO.setPagination(totalCount, page, size);
		if (page < 1) {
			page = 1;
		}
		if (page > paginationDTO.getTotalPage()) {
			page = paginationDTO.getTotalPage();
		}

		// size*(page-1)
		Integer offset = size * (page - 1);

		QuestionExample example = new QuestionExample();
		example.createCriteria().andCreatorEqualTo(userId);

		List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
		List<QuestionDTO> questionDTOList = new ArrayList<>();

		for (Question question : questions) {
			User user = userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();

			// 使用BeanUtils.copyProperties，快速把question的属性拷贝到questionDTO中
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}

		paginationDTO.setQuestions(questionDTOList);
		return paginationDTO;
	}

	public QuestionDTO getById(Integer id) {
		Question question = questionMapper.selectByPrimaryKey(id);
		if (question == null) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		User user = userMapper.selectByPrimaryKey(question.getCreator());
		questionDTO.setUser(user);
		return questionDTO;
	}

	public void createOrUpdate(Question question) {
		if (question.getId() == null) {
			// 如果为null，说明是第一次创建提问
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModified(question.getGmtCreate());
			question.setViewCount(0);
			question.setLikeCount(0);
			question.setCommentCount(0);
			questionMapper.insert(question);
		} else {
			// 如果不是，则更新
			Question updateQuestion = new Question();
			updateQuestion.setGmtModified(System.currentTimeMillis());
			updateQuestion.setTitle(question.getTitle());
			updateQuestion.setDescription(question.getDescription());
			updateQuestion.setTag(question.getTag());
			QuestionExample example = new QuestionExample();
			example.createCriteria().andIdEqualTo(question.getId());
			int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
			if (updated != 1) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		}
	}

	public void incView(Integer id) {
		Question question = new Question();
		question.setId(id);
		question.setViewCount(1);
		questionExtMapper.incView(question);
	}

	public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
		if (StringUtils.isBlank(queryDTO.getTag())) {
			return new ArrayList<>();
		}
		String[] tags = StringUtils.split(queryDTO.getTag(), ",");
		String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
		Question question = new Question();
		question.setId(queryDTO.getId());
		question.setTag(regexpTag);

		// 把 questions 转换成 QuestionDTO
		List<Question> questions = questionExtMapper.selectRelated(question);
		List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(q, questionDTO);
			return questionDTO;
		}).collect(Collectors.toList());
		return questionDTOS;
	}
}
