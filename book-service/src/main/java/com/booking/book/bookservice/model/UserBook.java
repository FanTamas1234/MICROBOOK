package com.booking.book.bookservice.model;

import io.micrometer.core.instrument.Meter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserBook {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID userId;
    @ManyToOne
    @JoinColumn(name = "bookId",referencedColumnName = "isbn")
    @ToString.Exclude
    private Book bookId;
    private Condition condition;
    private BookStatus status;
    @CreatedDate
    private LocalDateTime addedAt;
    private String comment;

}
