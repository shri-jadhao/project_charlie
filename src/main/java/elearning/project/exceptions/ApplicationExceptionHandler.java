package elearning.project.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
