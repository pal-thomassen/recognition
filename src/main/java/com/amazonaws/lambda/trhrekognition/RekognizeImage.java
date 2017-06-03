package com.amazonaws.lambda.trhrekognition;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.ByteBuffer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonGenerator;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.lambda.trhrekognition.models.ImageToRekognize;
import com.amazonaws.lambda.trhrekognition.models.RekognitionRequest;
import com.amazonaws.lambda.trhrekognition.models.RekognitionResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;

public class RekognizeImage implements RequestHandler<RekognitionRequest, RekognitionResponse> {

    @Override
    public RekognitionResponse handleRequest(RekognitionRequest input, Context context) {
        context.getLogger().log("Input: " + input.getBody());
        JsonReader jsonReader = Json.createReader(new StringReader(input.getBody()));
        JsonObject object = jsonReader.readObject();
        ImageToRekognize imageToRekognize = new ImageToRekognize(object.getString("image"));
      
        AmazonRekognition rekognition = AmazonRekognitionClientBuilder.standard().build();
        Image image = new Image();
        image.withBytes(ByteBuffer.wrap(Base64.decodeBase64(imageToRekognize.imageBase64Encoded)));
       
        SearchFacesByImageResult result = rekognition.searchFacesByImage(
    		new SearchFacesByImageRequest()
        		.withCollectionId("trhrekognition")
        		.withImage(image)
		);
      
        StringWriter strWriter = new StringWriter();
        JsonGenerator gen = Json.createGenerator(strWriter);
        gen.writeStartObject();
    	gen.write("num_rekognized", result.getFaceMatches().size());
	        gen.writeStartArray("matches");
	        result.getFaceMatches().forEach(match -> {
	        	if (match.getFace().getExternalImageId() != null) {
	        		gen.writeStartObject();
		        	gen.write("id", match.getFace().getExternalImageId());
		        	gen.write("similarity", match.getSimilarity());
		        	gen.writeEnd();
	        	}
	        });
	        gen.writeEnd();
        gen.writeEnd();
        gen.flush();
        strWriter.flush();
        
        RekognitionResponse response = new RekognitionResponse();
        response.setBody(strWriter.toString());
        
        return response;
    }

}
;