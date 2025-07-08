package com.example.plusproject.domain.reservation.exception;

public class ReservationNotFound extends RuntimeException {
    public ReservationNotFound(String message) {
        super(message);
    }
}
