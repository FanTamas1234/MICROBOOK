package com.booking.book.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BookRequestDto(
        @NotBlank
        @Pattern(regexp="[0-9-]{13,17}")
        String isbn,
        @NotBlank
        String title,
        @NotBlank
        String author,
        @NotBlank
        String genre,
        @NotBlank
        String year_published,
        @NotBlank
        String description
) {
}
