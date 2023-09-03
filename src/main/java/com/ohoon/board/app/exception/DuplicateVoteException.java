package com.ohoon.board.app.exception;

public class DuplicateVoteException extends RuntimeException {

    public DuplicateVoteException(String message) {
        super(message);
    }

    public DuplicateVoteException(String message, Throwable cause) {
        super(message, cause);
    }
}
