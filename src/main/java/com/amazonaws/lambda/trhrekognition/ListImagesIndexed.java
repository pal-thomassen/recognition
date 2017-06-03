package com.amazonaws.lambda.trhrekognition;

import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.stream.JsonGenerator;

import com.amazonaws.lambda.trhrekognition.models.RekognitionResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.ListFacesRequest;
import com.amazonaws.services.rekognition.model.ListFacesResult;

public class ListImagesIndexed implements RequestHandler<Object, RekognitionResponse> {

    @Override
    public RekognitionResponse handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);

        AmazonRekognition rekognition = AmazonRekognitionClientBuilder.standard().build();
        ListFacesResult result = rekognition.listFaces(new ListFacesRequest().withCollectionId("trhrekognition"));
        
        List<String> keys = result.getFaces().stream()
		.map(face -> {
        	return face.getExternalImageId();
        })
		.collect(Collectors.toList());  
        
        StringWriter strWriter = new StringWriter();
        JsonGenerator gen = Json.createGenerator(strWriter);
        gen.writeStartArray();
        keys.forEach(key -> {
        	if (key != null) {
        		gen.write(key);
        	}
        });
        gen.writeEnd();
        gen.flush();
        strWriter.flush();
        
        RekognitionResponse response = new RekognitionResponse();
        response.setBody(strWriter.toString());
        
        return response;
    }

}
