package com.booking.book.exchangeservice.repository;

import com.booking.book.exchangeservice.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, UUID> {
    List<Exchange> findAllByInitiatorBookIdEquals(String bookId);
}
