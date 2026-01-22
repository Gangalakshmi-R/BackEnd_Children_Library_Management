package com.examly.springapp.model;

import java.sql.Date;

import jakarta.persistence.Id;

public class Borrow {
    @Id
    Long borrowId;
    Date returnDate;
    Date borrowDate; 
}
