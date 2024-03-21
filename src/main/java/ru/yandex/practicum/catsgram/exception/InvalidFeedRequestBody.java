package ru.yandex.practicum.catsgram.exception;

public class InvalidFeedRequestBody extends RuntimeException {
    public InvalidFeedRequestBody() {
    }

    public InvalidFeedRequestBody(String message) {
        super(message);
    }

    public InvalidFeedRequestBody(Throwable cause) {
        super(cause);
    }
}
