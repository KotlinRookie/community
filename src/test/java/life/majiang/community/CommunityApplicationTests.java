package life.majiang.community;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.CommentExample;
import life.majiang.community.model.Question;

import javax.sql.DataSource;
import java.sql.SQLException;


@SpringBootTest
public class CommunityApplicationTests {
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Test
	public void test() {
		CommentExample commentExample = new CommentExample();
		commentExample.createCriteria()
				.andParentIdEqualTo(16)
				.andTypeEqualTo(1);
		
		System.out.println(commentMapper.selectByExample(commentExample));
	}

}
