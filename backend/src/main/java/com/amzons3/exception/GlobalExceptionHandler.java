package com.amzons3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.amazonaws.services.s3.model.AmazonS3Exception;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	/**
     * Handles the exception thrown during image upload to S3 bucket.
     *
     * @param imageUploaderException The exception thrown during image upload.
     * @return ResponseEntity with an error message and status code.
     */
	@ExceptionHandler(ImageUploaderException.class)
	public ResponseEntity<ResponseMessage> handleImageUploadException(ImageUploaderException imageUploaderException) {
		ResponseEntity<ResponseMessage> responseMessage = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ResponseMessage.builder()
						.message("Error uploading file to S3 Bucket")
						.httpStatuscode(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.build());
		return responseMessage;
	}
	
	/**
     * Handles the Amazon S3 exception.
     *
     * @param exception The Amazon S3 exception.
     * @return ResponseEntity with an error message and status code.
     */
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<ResponseMessage> handleAmazonS3Exception(AmazonS3Exception exception){
		ResponseEntity<ResponseMessage> message = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ResponseMessage.builder()
						.message("Some exception caught in Amazon S3")
						.httpStatuscode(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.build());
		return message;
	}
	
	/**
     * Handles the NoResourceFoundException.
     *
     * @param exception The NoResourceFoundException.
     * @return ResponseEntity with an error message and status code.
     */
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ResponseMessage> handleNoResourceFoundException(NoResourceFoundException exception){
		ResponseEntity<ResponseMessage> message = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ResponseMessage.builder()
						.message("No Resource Found")
						.httpStatuscode(HttpStatus.NO_CONTENT.value())
						.build());
		return message;
	}
	
	
	/**
     * Handles the All Other Exceptions.
     *
     * @param exception The NoResourceFoundException.
     * @return ResponseEntity with an error message and status code.
     */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseMessage> handleAllOtherException(Exception exception){
		ResponseEntity<ResponseMessage> message = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		.body(ResponseMessage.builder()
				.message("Some uncaught Exception")
				.httpStatuscode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.build());
		return message;
	}
}
