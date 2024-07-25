package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class User {
    private Long id;
    @NotBlank
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
}
