package com.my.adopet_api.dto;

public record AuthenticationDto(
        String email,
        String password
) {
}
