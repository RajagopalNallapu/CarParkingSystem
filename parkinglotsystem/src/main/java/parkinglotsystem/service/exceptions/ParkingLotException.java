package com.parkinglot.service.exceptions;

public class ParkingLotException extends RuntimeException {
	private String message;

	public ParkingLotException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
