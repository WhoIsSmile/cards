package laughing.cards.exception;

import laughing.cards.constant.ErrorEnum;

public class CardException extends RuntimeException {

	/**
	 * 
	 */
	private ErrorEnum errorEnum;

	private static final long serialVersionUID = -4536296605349281544L;

	public CardException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CardException(ErrorEnum errorEnum) {
		super(errorEnum.getErrorMsg());
		this.errorEnum = errorEnum;
	}

	public CardException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CardException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CardException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CardException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ErrorEnum getErrorEnum() {
		return errorEnum;
	}

	public void setErrorEnum(ErrorEnum errorEnum) {
		this.errorEnum = errorEnum;
	}

}
