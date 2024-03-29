package life.majiang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

	QUESTION_NOT_FOUND(2001, "你找的问题不在了,换一个试试？"),
	TARGET_NOT_FOUND(2002, "未选择任何问题或评论进行回复"),
	NO_LOGIN(2003, "当前操作需要登录，请登录后重试"),
	SYS_ERROR(2004,"服务器冒烟了，请稍后重试"),
	TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"), 
	COMMENT_NOT_FOUND(2006,"回复的评论不在了,换一个试试？"),
	CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
	READ_NOTIFICATION_FAIL(2008,"用户不匹配"),
	NOTIFICATION_NOT_FOUND(2009,"消息莫非是不翼而飞了？"),
	;
	
	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	private String message;
	private Integer code;

	private CustomizeErrorCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
