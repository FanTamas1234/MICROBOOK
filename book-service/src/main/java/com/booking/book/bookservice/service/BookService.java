package com.booking.book.bookservice.service;

import com.booking.book.bookservice.dto.*;
import com.booking.book.bookservice.kafka.ExchangeTradeResponseProducer;
import com.booking.book.bookservice.mapper.BookMapper;
import com.booking.book.bookservice.model.Book;
import com.booking.book.bookservice.model.UserBook;
import com.booking.book.bookservice.repository.BookRepository;
import com.booking.book.bookservice.repository.UserBookRepository;
import com.booking.book.bookservice.util.JWTAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final JWTAuthConverter jwtAuthConverter;
    private final UserBookRepository userBookRepository;
    private final ExchangeTradeResponseProducer exchangeTradeResponseProducer;

//    @Autowired
//    public BookService(BookMapper bookMapper, BookRepository bookRepository) {
//        this.bookMapper = bookMapper;
//        this.bookRepository = bookRepository;
//    }

    public BookResponseDto addBook(BookRequestDto bookRequest) {
        Book book = bookMapper.toBook(bookRequest);
        Book persistedBook = bookRepository.save(book);
        return bookMapper.toBookResponseDto(persistedBook);
    }

    public List<BookResponseDto> getAllBooks(
    ) {
        List<Book> tickets = bookRepository.findAll();
        return tickets.stream()
                .map(bookMapper::toBookResponseDto)
                .toList();
    }

    public UserBookResponseDto addBookToUser(UserBookRequestDto request) {

        UUID userId = UUID.fromString(jwtAuthConverter.retrieveJwtFromSecurityContext().getClaim("sub"));
        UserBook transientBook = bookMapper.toUserBook(userId, request);
        UserBook savedBook = userBookRepository.save(transientBook);


        return bookMapper.toUserBookResponseDto(savedBook);
    }

    public void processExchange(

            ExchangeTradeRequestDto request
    ){
        UserBook initiator = userBookRepository.findAllByBookIdAndUserId(request.initiatorBookId(),UUID.fromString(request.initiatorId()))
                .getFirst();
        UserBook receiver = userBookRepository.findAllByBookIdAndUserId(request.receiverBookId(),UUID.fromString(request.receiverId()))
                .getFirst();

        UUID intermediate = initiator.getUserId();
        initiator.setUserId(receiver.getUserId());
        receiver.setUserId(intermediate);

        userBookRepository.saveAll(List.of(initiator, receiver));

        exchangeTradeResponseProducer.produceExchangeTradeResponse(new ExchangeTradeResponseDto(request.exchangeId(), "CLOSED"));
    }
}
