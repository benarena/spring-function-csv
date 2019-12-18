package com.example.csv.functions;

import com.amazonaws.services.kinesis.AmazonKinesisAsync;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.json.Jackson;
import com.example.csv.model.Ticket;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Parse a CSV file into a stream of tickets.
 */
public class ParseBasicCsvFunction implements Consumer<S3Event> {

    private static Logger logger = LoggerFactory.getLogger(ParseBasicCsvFunction.class);

//    @Value("${kinesis.stream.name}")
    private final String kinesisStream = "test-stream"; // TODO: configuration

    private final AmazonS3 s3;
    private final AmazonKinesisAsync kinesis;

    public ParseBasicCsvFunction(final AmazonS3 s3, final AmazonKinesisAsync kinesis) {
        this.s3 = s3;
        this.kinesis = kinesis;
    }

    @Override
    public void accept(final S3Event s3Event) {
        s3Event.getRecords()
            .stream()
            .map(this::getS3File)
            .map(this::parseTickets)
            .flatMap(Collection::stream)
            .forEach(this::queue);
    }

    private S3Object getS3File(final S3EventNotification.S3EventNotificationRecord record) {
        return s3.getObject(new GetObjectRequest(record.getS3().getBucket().getName(),
            record.getS3().getObject().getUrlDecodedKey()));
    }

    private List<Ticket> parseTickets(final S3Object file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getObjectContent()))) {
            final CsvToBean<Ticket> converter = new CsvToBeanBuilder<Ticket>(reader).withType(Ticket.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withOrderedResults(false)
                .build();

            return converter.parse();
        }
        catch (IOException e) {
            throw new RuntimeException("Error reading file from S3", e);
        }
    }

    private void queue(final Ticket ticket) {
        logger.trace("Parsed ticket: {}", ticket);
        final ByteBuffer data = ByteBuffer.wrap(Jackson.toJsonString(ticket).getBytes(StandardCharsets.UTF_8));
        kinesis.putRecord(kinesisStream, data, ticket.getOperatorId());
    }

}
