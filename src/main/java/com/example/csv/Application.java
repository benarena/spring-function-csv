package com.example.csv;

import com.amazonaws.services.kinesis.AmazonKinesisAsync;
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClientBuilder;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.csv.functions.ParseBasicCsvFunction;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootConfiguration
public class Application implements ApplicationContextInitializer<GenericApplicationContext> {

    private static AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
    private static AmazonKinesisAsync kinesis = AmazonKinesisAsyncClientBuilder.defaultClient();

    public static void main(String[] args) {
        FunctionalSpringApplication.run(Application.class, args);
    }

    @Override
    public void initialize(GenericApplicationContext context) {
        context.registerBean("parseBasicCsvFunction",
            FunctionRegistration.class,
            () -> new FunctionRegistration<>(new ParseBasicCsvFunction(s3, kinesis)).type(
                FunctionType.from(S3Event.class).to(Void.class).getType()));
    }
}
