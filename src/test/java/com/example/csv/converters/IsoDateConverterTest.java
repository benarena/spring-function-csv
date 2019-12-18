package com.example.csv.converters;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IsoDateConverterTest {

    @Test
    void itShouldConvertIsoString() throws Exception {
        final IsoDateConverter converter = new IsoDateConverter();
        final String isoString = "2019-12-03T10:15:30.00Z";
        assertThat(converter.convert(isoString)).isEqualTo(isoString);
    }

    @Test
    void itShouldThrowExceptionOnInvalidDate() throws Exception {
        final IsoDateConverter converter = new IsoDateConverter();
        assertThrows(CsvDataTypeMismatchException.class, () ->
            converter.convert("2019/12/03 10:15:30am"));
        assertThrows(CsvDataTypeMismatchException.class, () ->
            converter.convert("non-date string"));
    }
}
