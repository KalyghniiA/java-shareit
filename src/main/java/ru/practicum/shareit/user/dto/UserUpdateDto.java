package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateDto {
    @NotNull
    private final Long id;
    private final String name;
    private final String email;
}
