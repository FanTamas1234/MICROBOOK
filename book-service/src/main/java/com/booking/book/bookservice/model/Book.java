package com.booking.book.bookservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Book {
    @Id
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private Integer year_published;
    private String description;

}
