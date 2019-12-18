package com.example.csv.model;

import com.example.csv.converters.IsoDateConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import java.math.BigDecimal;
import java.time.Instant;

public class Ticket {

    @CsvBindByName(required = true)
    private String id;
    @CsvBindByName(column = "operator", required = true)
    private String operatorId;
    @CsvBindByName(column = "employee", required = true)
    private String employeeId;
    @CsvCustomBindByName(converter = IsoDateConverter.class)
    private Instant time;
    @CsvBindByName
    private BigDecimal totalAmount;
    @CsvBindByName
    private BigDecimal taxAmount;
    @CsvBindByName(required = true)
    private BigDecimal tipAmount;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(final String operatorId) {
        this.operatorId = operatorId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(final String employeeId) {
        this.employeeId = employeeId;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(final Instant time) {
        this.time = time;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(final BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(final BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(final BigDecimal tipAmount) {
        this.tipAmount = tipAmount;
    }

}
