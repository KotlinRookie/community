package life.majiang.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
	private Integer parentId;
	private String content;
	private Integer type;
}
