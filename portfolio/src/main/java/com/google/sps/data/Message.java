package com.google.sps.data;

import java.time.LocalDateTime;

public final class Message {
  private final String name;
  private final LocalDateTime date;
  private final String email;
  private final String message;

  public Message(String name, String email, String message) {
    this.name = name;
    this.date = LocalDateTime.now();
    this.email = email;
    this.message = message;
  }

  public String getName() {
    return this.name;
  }

  public LocalDateTime getDate() {
    return this.date;
  }

  public String getEmail() {
    return this.email;
  }

  public String getMessage() {
    return this.message;
  }
}
