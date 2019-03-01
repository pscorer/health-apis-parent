package gov.va.api.health.sentinel;

import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Environment {
  LAB,
  LOCAL,
  PROD,
  QA,
  STAGING;

  static {
    String env = System.getProperty("sentinel", "LOCAL").toUpperCase(Locale.ENGLISH);
    log.info(
        "Using {} Sentinel environment (Override with -Dsentinel=LAB|LOCAL|QA|PROD|STAGING)", env);
  }

  /** Parse the system property 'sentinel' into the appropriate enum. */
  static Environment get() {
    String env = System.getProperty("sentinel", "LOCAL").toUpperCase(Locale.ENGLISH);
    switch (env) {
      case "LAB":
        return Environment.LAB;
      case "LOCAL":
        return Environment.LOCAL;
      case "PROD":
        return Environment.PROD;
      case "QA":
        return Environment.QA;
      case "STAGING":
        return Environment.STAGING;
      default:
        throw new IllegalArgumentException("Unknown sentinel environment: " + env);
    }
  }
}
