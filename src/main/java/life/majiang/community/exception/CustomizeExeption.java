package life.majiang.community.exception;

public class CustomizeExeption extends RuntimeException {

	private String message;
	private Integer code;

	public CustomizeExeption(ICustomizeErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}

}
