package com.booking.book.bookservice.repository;

import com.booking.book.bookservice.model.Book;
import com.booking.book.bookservice.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, UUID> {

    @Query("SELECT ub FROM UserBook ub WHERE ub.bookId.isbn = :bookId AND ub.userId = :userId")
    List<UserBook> findAllByBookIdAndUserId(String bookId, UUID userId);
}
