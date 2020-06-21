package com.google.sps.data;

public final class Message {
  private final String name;
  private final String email;
  private final String message;
  private final Double sentimentScore;

  public Message(String name, String email, String message, Double sentimentScore) {
    this.name = name;
    this.email = email;
    this.message = message;
    this.sentimentScore = sentimentScore;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public String getMessage() {
    return this.message;
  }

  public Double getSentimentScore() {
    return this.sentimentScore;
  }
}
