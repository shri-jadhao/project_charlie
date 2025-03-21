package elearning.project.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import elearning.project.exceptiondto.ApiException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	@ExceptionHandler(exception=ResourceIdNotFoundException.class)
	public ResponseEntity<?> resourseIdNotFoundException(ResourceIdNotFoundException ex,HttpServletRequest request){
		ApiException api = new ApiException(request.getRequestURI(), ex.getMessage(),LocalDateTime.now());
		return new ResponseEntity<>(api,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(exception=NotEnrolledException.class)
	public ResponseEntity<?> NotEnrolledException(NotEnrolledException ex,HttpServletRequest request){
		ApiException api = new ApiException(request.getRequestURI(), ex.getMessage(),LocalDateTime.now());
		return new ResponseEntity<>(api,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(exception=RoleBasedAccessControlException.class)
	public ResponseEntity<?> RoleBasedAccessControlException(RoleBasedAccessControlException ex,HttpServletRequest request){
		ApiException api = new ApiException(request.getRequestURI(), ex.getMessage(),LocalDateTime.now());
		return new ResponseEntity<>(api,HttpStatus.BAD_REQUEST);
	}
	
	//Method ArgumentNotValidException class is a predefined exception class which has 
	@ExceptionHandler(exception = MethodArgumentNotValidException.class)
	public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		// This is the method going to handle the restaurentIdnotfoundexception
		StringBuilder sb=new StringBuilder();
		List<FieldError> listOfErrors = ex.getFieldErrors();
		for (FieldError err : listOfErrors) {
			
			 sb.append("Error in Feild:"+err.getField()+"->"+err.getDefaultMessage()+'\n');
		}
		ApiException api=new ApiException(sb.toString(),request.getRequestURI(),LocalDateTime.now());
		return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);

	}
}
