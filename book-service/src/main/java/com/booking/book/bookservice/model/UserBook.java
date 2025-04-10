package com.booking.book.bookservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserBook {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID user_id;
    @ManyToOne
    @JoinColumn(name = "book_id",referencedColumnName = "isbn")
    @ToString.Exclude
    private Book book_id;
    private Condition condition;
    private BookStatus status;
    private LocalDateTime added_at;
    private String comment;

}
