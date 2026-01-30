package com.examly.springapp.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowId;

    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @Temporal(TemporalType.DATE)
    private Date borrowDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member child;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}