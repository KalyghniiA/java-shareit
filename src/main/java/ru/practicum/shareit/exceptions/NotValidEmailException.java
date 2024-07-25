package ru.practicum.shareit.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotValidEmailException extends RuntimeException {
    private final String message;
}
