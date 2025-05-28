package com.booking.book.exchangeservice.controller;

import com.booking.book.exchangeservice.dto.ExchangeRequestDto;
import com.booking.book.exchangeservice.dto.ExchangeResponseDto;
import com.booking.book.exchangeservice.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PreAuthorize("hasAnyRole('MANAGER','USER')")
    @PostMapping("/private")
    public ResponseEntity<ExchangeResponseDto>  addExchange(

           @RequestBody ExchangeRequestDto request
    ) {

        ExchangeResponseDto response = exchangeService.addExchange(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/{bookId}")
    public ResponseEntity<List<ExchangeResponseDto>> getExchangesByBookId(
            @PathVariable(value = "bookId") String bookId
    ) {

        List<ExchangeResponseDto> exchanges = exchangeService.getExchangesByBookId(bookId);
        return ResponseEntity.ok(exchanges);
    }

    @PostMapping("/private/{trade}")
    public ResponseEntity<ExchangeResponseDto>  processExchange(

            @PathVariable("trade") String trade

    ){

        ExchangeResponseDto response = exchangeService.processExchange(trade);
        return ResponseEntity.ok(response);
    }
}
