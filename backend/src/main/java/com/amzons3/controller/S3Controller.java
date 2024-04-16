package com.amzons3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amzons3.service.AmazonS3ImageUploaderService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class S3Controller {

	@Autowired
	private AmazonS3ImageUploaderService amazonS3ImageUploaderService;
	
	/**
     * Endpoint for uploading an image to Amazon S3.
     *
     * @param multipartFile The image file to be uploaded.
     * @return The URL of the uploaded image.
     */
	@PostMapping("/uploadimage")
	public String imageUploader(@RequestParam MultipartFile multipartFile) {
		String url = amazonS3ImageUploaderService.imageUploader(multipartFile);
		System.out.println(url);
		return url;
	}
	
	 /**
     * Endpoint for retrieving URLs of all images in the S3 bucket.
     *
     * @return List of URLs of all images.
     */
	@GetMapping("/getAll")
	public List<String> getAllImages() {
		List<String> getList = amazonS3ImageUploaderService.allFileList();
		return getList;
	}
	
	 /**
     * Endpoint for retrieving the URL of a specific image by its filename.
     *
     * @param filename The name of the image file.
     * @return The URL of the specified image.
     */
	@GetMapping("/get/{filename}")
	public String getImagesByURL(@PathVariable String filename) {
	   String imageString = amazonS3ImageUploaderService.getUrlByImageName(filename);
	   return imageString;
	}
	
	 /**
     * Endpoint for deleting the image using the URL
     *
     * @param URL The URL of the image file.
     * @return The success delete message.
     */
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteImageByURL(@RequestParam String URL){
		int index = URL.indexOf(".com/") + 5;
        String fileName = URL.substring(index);
        int questionMarkIndex = fileName.indexOf("?");
        if (questionMarkIndex != -1) {
        	fileName = fileName.substring(0, questionMarkIndex);
        }
        fileName = fileName.replace(".com/", "");
		String message = amazonS3ImageUploaderService.deleteImagebyURL(fileName);
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
}
