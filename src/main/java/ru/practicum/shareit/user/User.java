package ru.practicum.shareit.user;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */

@Data
public class User {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 100)
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
}
