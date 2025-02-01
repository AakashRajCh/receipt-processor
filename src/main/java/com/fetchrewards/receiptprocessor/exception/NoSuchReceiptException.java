package com.fetchrewards.receiptprocessor.exception;

/**
 * Exception thrown when a requested receipt is not found.
 */
public class NoSuchReceiptException extends RuntimeException {
    public NoSuchReceiptException(String message) {
        super(message);
    }
}
