package gov.va.api.health.sentinel;

import io.restassured.response.ResponseBody;

public interface ResponsesAreFunctionallyEqualCheck {
  boolean equals(ResponseBody<?> responseBody1, ResponseBody<?> responseBody2);
}
