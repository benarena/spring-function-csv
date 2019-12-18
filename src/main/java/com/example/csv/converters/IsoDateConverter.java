package com.example.csv.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.time.Instant;
import java.time.format.DateTimeParseException;

/**
 * Convert from String is ISO 8601 format to Instant.
 */
public class IsoDateConverter extends AbstractBeanField {
    @Override
    protected Instant convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        try {
            return Instant.parse(s);
        } catch (DateTimeParseException e) {
            throw new CsvDataTypeMismatchException(s, Instant.class, e.getMessage());
        }
    }
}
