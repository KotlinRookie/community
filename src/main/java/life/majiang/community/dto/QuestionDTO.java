package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
	private Integer id;
	private String title;
	private String description;
	private String tag;
	private Long gmtCreate;
	private Long gmtModified;
	private Integer creator;
	private Integer viewCount;
	private Integer commentCount;
	private Integer likeCount;
	private User user; // 为了获取头像，在Question的实体类基础上，新加入User实体类
}
