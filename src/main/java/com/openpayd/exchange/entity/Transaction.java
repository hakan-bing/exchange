package com.openpayd.exchange.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SOURCE", nullable = false)
    private String source;

    @Column(name = "TARGET", nullable = false)
    private String target;

    @Column(name = "RATE", nullable = false)
    private Double rate;

    @Column(name = "DATE", nullable = false)
    private Date date;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
