package com.amzons3.serviceIMPL;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.hibernate.grammars.hql.HqlParser.HourContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PublicAccessBlockConfiguration;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amzons3.exception.ImageUploaderException;
import com.amzons3.service.AmazonS3ImageUploaderService;

@Service
public class AmazonS3ImageUploaderServiceIMPL implements AmazonS3ImageUploaderService{
	
	@Autowired
	private AmazonS3 client;
	
	@Value("${app.s3.bucket}")
	private String bucketName;
	
	
	/**
     * Uploads an image to Amazon S3 bucket.
     *
     * @param image The image file to be uploaded.
     * @return The URL of the uploaded image.
     * @throws ImageUploaderException If image is null or cannot be uploaded.
     */
    @Override
    public String imageUploader(MultipartFile image) throws ImageUploaderException {
        if (image == null) {
            throw new ImageUploaderException("Image is null");
        }
        String originalFileName = image.getOriginalFilename();
        String s3FileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(image.getSize());
        try {
            client.putObject(new PutObjectRequest(bucketName, s3FileName, image.getInputStream(), objectMetadata));
            return this.getPreSignedURL(s3FileName);
        } catch (IOException e) {
            throw new ImageUploaderException("Image cannot be uploaded" + e.getMessage());
        }
    }

    /**
     * Generates a pre-signed URL for accessing objects in S3 bucket.
     *
     * @param filename The name of the file in S3 bucket.
     * @return The pre-signed URL of the object.
     */
    @Override
    public String getPreSignedURL(String filename) {
        Date expirationDate = new Date();
        long time = expirationDate.getTime();
        int hour = 24;
        time = time + hour * 60 * 60 * 1000;
        expirationDate.setTime(time);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, filename)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expirationDate);

        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
        String finalURL = url.toString();
        return finalURL;
    }

    /**
     * Retrieves a list of URLs for all files in the S3 bucket.
     *
     * @return List of URLs for all files in the bucket.
     */
    @Override
    public List<String> allFileList() {
        ListObjectsV2Request listObjectRequest = new ListObjectsV2Request().withBucketName(bucketName);
        ListObjectsV2Result listObjectRequests = client.listObjectsV2(listObjectRequest);
        List<S3ObjectSummary> s3ObjectSummaries = listObjectRequests.getObjectSummaries();
        List<String> listOfURLs = s3ObjectSummaries.stream().map(item -> this.getPreSignedURL(item.getKey())).collect(Collectors.toList());
        return listOfURLs;
    }

    /**
     * Retrieves the URL of a specific image by its filename.
     *
     * @param filename The name of the image file.
     * @return The URL of the specified image.
     */
    @Override
    public String getUrlByImageName(String filename) {
        S3Object getObject = client.getObject(bucketName, filename);
        String key = getObject.getKey();
        return getPreSignedURL(key);
    }
    
    /**
     * Deletes a file from the Amazon S3 bucket.
     *
     * @param filename The name of the file to be deleted.
     * @throws ImageUploaderException If the file cannot be deleted.
     */
    @Override
    public String deleteImagebyURL(String fileName) {
    	client.deleteObject(bucketName, fileName);
    	return "Image deleted successfully";
    }
	


}
