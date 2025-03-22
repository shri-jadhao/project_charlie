package elearning.project.exceptions;

public class UserExistsError extends RuntimeException{
	public UserExistsError(String message) {
		super(message);
	}

}
