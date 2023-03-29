package life.majiang.community.mapper;

import java.util.List;

import life.majiang.community.model.Question;

public interface QuestionExtMapper {
	int incView(Question record);
	int incCommentCount(Question record);
	List<Question> selectRelated(Question question);
}