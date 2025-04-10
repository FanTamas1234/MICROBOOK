package com.booking.book.bookservice.dto;

import jakarta.validation.constraints.NotBlank;

public record BookResponseDto(
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotBlank
        String year_published
) {
}
