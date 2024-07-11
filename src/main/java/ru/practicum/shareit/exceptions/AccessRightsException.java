package ru.practicum.shareit.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AccessRightsException extends RuntimeException {
    private final String message;
}
