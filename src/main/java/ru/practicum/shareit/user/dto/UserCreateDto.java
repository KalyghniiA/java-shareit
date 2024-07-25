package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDto {
    @NotBlank
    @Size(min = 3, max = 100)
    private final String name;
    @Email
    @NotBlank
    private final String email;
}
