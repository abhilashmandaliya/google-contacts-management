package app.config;

import com.google.inject.Singleton;

import java.util.Set;

import io.dropwizard.Configuration;
import lombok.Getter;

@Singleton
@Getter
public class AppConfig extends Configuration {
  private String clientId;
  private String clientSecret;
  private Set<String> scopes;
  private String redirectionURL;
}
