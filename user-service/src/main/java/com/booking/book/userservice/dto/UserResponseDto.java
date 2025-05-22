package com.booking.book.userservice.dto;

public record UserResponseDto(

        String id,
        String surname,
        String name,
        String username,
        String email

) { }