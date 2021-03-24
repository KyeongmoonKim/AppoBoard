package user;

public class LoginFailException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2955337102322286564L;

	public LoginFailException(String errorMessage) {
        super(errorMessage);
    }
}
