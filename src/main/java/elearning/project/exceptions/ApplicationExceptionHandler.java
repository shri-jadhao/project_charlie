package elearning.project.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import elearning.project.exceptiondto.ApiException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(ResourceIdNotFoundException.class)
	public ResponseEntity<?> resourceIdNotFoundException(ResourceIdNotFoundException ex, HttpServletRequest request) {
		ApiException api = new ApiException(request.getRequestURI(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotEnrolledException.class)
	public ResponseEntity<?> notEnrolledException(NotEnrolledException ex, HttpServletRequest request) {
		ApiException api = new ApiException(request.getRequestURI(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RoleBasedAccessControlException.class)
	public ResponseEntity<?> roleBasedAccessControlException(RoleBasedAccessControlException ex,
			HttpServletRequest request) {
		ApiException api = new ApiException(request.getRequestURI(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		List<FieldError> listOfErrors = ex.getFieldErrors();
		for (FieldError err : listOfErrors) {
			sb.append("Error in Field: ").append(err.getField()).append(" -> ").append(err.getDefaultMessage())
					.append('\n');
		}
		ApiException api = new ApiException(sb.toString(), request.getRequestURI(), LocalDateTime.now());
		return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> UserNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
		ApiException api = new ApiException(request.getRequestURI(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CourseIdNotFoundException.class)
	public ResponseEntity<?> CourseIdNotFound(CourseIdNotFoundException ex, HttpServletRequest request) {
		ApiException api = new ApiException(request.getRequestURI(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserAlreadyExists.class)
	public ResponseEntity<?> UserAlreadyExists(UserAlreadyExists ex, HttpServletRequest request) {
		ApiException api = new ApiException(request.getRequestURI(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(QuizException.class)
	public ResponseEntity<?> QuizException(QuizException ex, HttpServletRequest request) {
		ApiException api = new ApiException(request.getRequestURI(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	}

}
