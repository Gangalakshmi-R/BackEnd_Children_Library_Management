package com.examly.springapp.model;

import jakarta.persistence.Id;

public class Fine {
    @Id
    Long fineId;
    double amount;
}
