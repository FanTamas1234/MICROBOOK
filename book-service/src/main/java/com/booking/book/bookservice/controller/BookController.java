package com.booking.book.bookservice.controller;

import com.booking.book.bookservice.dto.BookRequestDto;
import com.booking.book.bookservice.dto.BookResponseDto;
import com.booking.book.bookservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/private")
    public ResponseEntity<BookResponseDto> addBook(
            @RequestBody BookRequestDto bookRequest
    ) {
        return new ResponseEntity<>(bookService.addBook(bookRequest),HttpStatus.CREATED);
    }

    @Operation(
            description = "Endpoint for retrieving data about all books in DB in DTO format.",
            summary = "Retrieve all books that are in store.",
            responses = {
                    @ApiResponse(
                            description = "Books retrieval was successful",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            schema = @Schema(implementation = BookResponseDto.class),
                                            examples = {
                                                    @ExampleObject(
                                                            value = "[\n{\n\"bookId\": " +
                                                                    "\"3429f9af-233f-4ea5-bef3-568603d09bd0\",\n" +
                                                                    "\"cost\": \"1000.00\",\n" +
                                                                    "\"date\": \"2025-03-28 10:30:00.000000\"\n},\n" +
                                                                    "{\n\"bookId\": " +
                                                                    "\"d7ab2d68-f796-4532-a9ef-4c1243af3177\",\n" +
                                                                    "\"cost\": \"1000.00\",\n" +
                                                                    "\"date\": \"2025-03-28 10:30:30.000000\"\n}\n]"
                                                    )
                                            }
                                    )
                            }
                    )
            }
    )

    @GetMapping("/public")
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

}