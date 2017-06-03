package com.amazonaws.lambda.trhrekognition.models;

public class ImageToRekognize {
	public final String imageBase64Encoded;
	
	public ImageToRekognize(String imageBase64Encoded) {
		this.imageBase64Encoded = imageBase64Encoded;
	}
}
