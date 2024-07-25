package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDto {
    @NotNull
    private final Long id;
    @Size(min = 3, max = 100)
    private final String name;
    @Email
    private final String email;
}
