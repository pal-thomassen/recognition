package com.amazonaws.lambda.trhrekognition;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.trhrekognition.models.ImagesIndexed;
import com.amazonaws.lambda.trhrekognition.models.RekognitionResponse;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListImagesIndexedTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testListImagesIndexed() {
        ListImagesIndexed handler = new ListImagesIndexed();
        Context ctx = createContext();

        RekognitionResponse output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        if (output != null) {
            System.out.println(output.toString());
        }
    }
}
