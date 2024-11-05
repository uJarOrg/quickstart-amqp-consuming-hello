package com.iqkv.incubator.quickstart.amqpconsuminghello.model;

import java.io.Serial;
import java.io.Serializable;

public record Greeting(String message) implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
}
