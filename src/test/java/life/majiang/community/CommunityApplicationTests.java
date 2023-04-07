package life.majiang.community;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.ImgFileMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.CommentExample;
import life.majiang.community.model.ImgFile;
import life.majiang.community.model.Question;

import javax.sql.DataSource;

import static org.mockito.ArgumentMatchers.startsWith;

import java.sql.SQLException;
import java.util.Arrays;

@SpringBootTest
public class CommunityApplicationTests {

	@Autowired
	private ImgFileMapper imgMapper;


}