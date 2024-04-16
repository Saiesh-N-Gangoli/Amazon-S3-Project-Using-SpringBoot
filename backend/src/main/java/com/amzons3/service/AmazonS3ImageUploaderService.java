package com.amzons3.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amzons3.exception.ImageUploaderException;

public interface AmazonS3ImageUploaderService {

	/**
     * Uploads an image to Amazon S3 bucket.
     *
     * @param image The image file to be uploaded.
     * @return The URL of the uploaded image.
     * @throws ImageUploaderException If image is null or cannot be uploaded.
     */
	public String imageUploader(MultipartFile image);
	
	
    /**
     * Generates a pre-signed URL for accessing objects in S3 bucket.
     *
     * @param filename The name of the file in S3 bucket.
     * @return The pre-signed URL of the object.
     */
	public String getPreSignedURL(String filename);
	
	
    /**
     * Generates a pre-signed URL for accessing objects in S3 bucket.
     *
     * @param filename The name of the file in S3 bucket.
     * @return The pre-signed URL of the object.
     */
	public List<String> allFileList();
	
	
    /**
     * Retrieves a list of URLs for all files in the S3 bucket.
     *
     * @return List of URLs for all files in the bucket.
     */
	public String getUrlByImageName(String Filename);


	/**
	 * Deletes a file from the Amazon S3 bucket.
	 *
	 * @param filename The name of the file to be deleted.
	 * @throws ImageUploaderException If the file cannot be deleted.
	 */
	String deleteImagebyURL(String fileName);
	
}
