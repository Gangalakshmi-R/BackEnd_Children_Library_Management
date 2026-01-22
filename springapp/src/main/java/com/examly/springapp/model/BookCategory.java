package com.examly.springapp.model;

import jakarta.persistence.Id;

public class BookCategory {
    @Id
    Long categoryId;
    String categoryName;
}
