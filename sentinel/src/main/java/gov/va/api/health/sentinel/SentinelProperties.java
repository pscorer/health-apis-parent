package gov.va.api.health.sentinel;

import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public final class SentinelProperties {

  /** Supplies system property access-token, or throws exception if it doesn't exist. */
  public static String magicAccessToken() {
    final String magic = System.getProperty("access-token");
    checkState(!isBlank(magic), "Access token not specified, -Daccess-token=<value>");
    return magic;
  }

  /** Read api-path from a system property. */
  public static String optionApiPath(String name, String defaultValue) {
    String property = "sentinel." + name + ".api-path";
    String apiPath = System.getProperty(property, defaultValue);
    if (!apiPath.startsWith("/")) {
      apiPath = "/" + apiPath;
    }
    if (!apiPath.endsWith("/")) {
      apiPath = apiPath + "/";
    }
    log.info("Using {} api path {} (Override with -D{}=<url>)", name, apiPath, property);
    return apiPath;
  }

  /**
   * Get crawler ignores from a system property. Ignores are not factored into the crawlers result
   * if they fail.
   */
  public static String optionCrawlerIgnores(String name) {
    String ignores = System.getProperty("sentinel." + name + ".crawler.ignores");
    if (isBlank(ignores)) {
      log.info(
          "No requests ignored. (Override with -Dsentinel.{}.crawler.ignores=<ignores> "
              + "-- Place ignores in a comma separated list)",
          name);
    } else {
      log.info(
          "Ignoring the following requests: {} "
              + "(Override with -Dsentinel.{}.crawler.ignores=<ignores> "
              + "-- Place ignores in a comma separated list)",
          ignores,
          name);
    }
    return ignores;
  }

  /** Read url from a system property. */
  public static String optionUrl(String name, String defaultValue) {
    String property = "sentinel." + name + ".url";
    String url = System.getProperty(property, defaultValue);
    if (url.endsWith("/")) {
      url = url.substring(0, url.length() - 1);
    }
    log.info("Using {} url {} (Override with -D{}=<url>)", name, url, property);
    return url;
  }

  /** Read thread count from system property. */
  public static int threadCount(String name, int defaultThreadCount) {
    int threads = defaultThreadCount;
    String maybeNumber = System.getProperty(name);
    if (isNotBlank(maybeNumber)) {
      try {
        threads = Integer.parseInt(maybeNumber);
      } catch (NumberFormatException e) {
        log.warn("Bad thread count {}, assuming {}", maybeNumber, threads);
      }
    }
    log.info("Using {} threads (Override with -D{}=<number>)", threads, name);
    return threads;
  }

  /** Read url replacement from system property. */
  public String urlReplace(String name) {
    String replace = System.getProperty("sentinel." + name + ".url.replace");
    if (isBlank(replace)) {
      log.info("URL replacement disabled (Override with -Dsentinel.{}.url.replace=<url>)", name);
    } else {
      log.info("URL replacement {} (Override with -Dsentinel.{}.url.replace=<url>)", replace, name);
    }
    return replace;
  }
}
