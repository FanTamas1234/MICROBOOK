package com.booking.book.exchangeservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID initiator_id;
    private UUID receiver_id;
    private UUID initiator_book_id;
    private UUID receiver_book_id;
    private TradeStatus status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private LocalDateTime completion_date;
}
