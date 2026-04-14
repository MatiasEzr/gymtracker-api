package com.matias.gymtracker.exceptions;

public class DayAlreadyAssignedException extends RuntimeException {
  public DayAlreadyAssignedException(String message) {
    super(message);
  }
}
