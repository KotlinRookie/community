package life.majiang.community.dto;

import lombok.Data;

@Data
public class NotificaitonDTO {
	private Integer id;
	private Long gmtCreate;
	private Integer status;
	private Integer notifier;
	private String outerTitle;
	private String notifierName;
	private Integer outerid;
	private String typeName;
	private Integer type;
}
