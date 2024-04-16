package com.amzons3.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class amazons3config {
	
	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String accessPassword;
	
	@Value("${cloud.aws.region.static}")
	private String region;
	
	@Bean
	public AmazonS3 client() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, accessPassword);
		AmazonS3 amazonS3 = AmazonS3ClientBuilder
		.standard()
		.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
		.withRegion(region)
		.build();
		return amazonS3;
	}
}
