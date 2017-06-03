package com.amazonaws.lambda.trhrekognition.models;

public class ImageToUpload {
	public final String imageKey;
	public final String imageBase64Encoded;
	
	public ImageToUpload(String imageKey, String imageBase64Encoded) {
		this.imageKey = imageKey;
		this.imageBase64Encoded = imageBase64Encoded;
	}
}
