package com.amazonaws.lambda.trhrekognition;

import java.io.StringReader;
import java.nio.ByteBuffer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.lambda.trhrekognition.models.RekognitionRequest;
import com.amazonaws.lambda.trhrekognition.models.RekognitionResponse;
import com.amazonaws.lambda.trhrekognition.models.ImageToUpload;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;

public class PostImageHandler implements RequestHandler<RekognitionRequest, RekognitionResponse> {

    public RekognitionResponse handleRequest(RekognitionRequest input, Context context) {
        context.getLogger().log("Input: " + input.getQueryStringParameters());
        context.getLogger().log("RequestBody: " + input.getBody());
        
        JsonReader jsonReader = Json.createReader(new StringReader(input.getBody()));
        JsonObject object = jsonReader.readObject();
        ImageToUpload imageRequest = new ImageToUpload(object.getString("imageKey"), object.getString("image"));
      
        context.getLogger().log("Key for  image: " + object.getString("imageKey"));
        
        AmazonRekognition rekognition = AmazonRekognitionClientBuilder.standard().build();
        Image image = new Image();
        image.withBytes(ByteBuffer.wrap(Base64.decodeBase64(imageRequest.imageBase64Encoded)));
       
        rekognition.indexFaces(new IndexFacesRequest()
    			.withCollectionId("trhrekognition")
    			.withImage(image)
    			.withExternalImageId(imageRequest.imageKey)
		);
        
        RekognitionResponse response = new RekognitionResponse();
    	response.setStatusCode(200);
    	response.setBody(imageRequest.imageKey);
        return response;
    }
}
