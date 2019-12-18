package com.example.csv.functions;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@FunctionalSpringBootTest
class ParseBasicCsvFunctionTest {

    @Autowired
    private FunctionCatalog catalog;

    @Test
    void accept() {
        Function<Flux<S3Event>, Flux<Void>> function = catalog.lookup(Function.class, "parseBasicCsvFunction");
        assertNotNull(function);
    }
}
