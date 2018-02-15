package com.amazonaws.lambda.trhrekognition;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.trhrekognition.models.RekognitionRequest;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

    private static RekognitionRequest input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    @Test
    public void testLambdaFunctionHandler() {
       
    }
}
