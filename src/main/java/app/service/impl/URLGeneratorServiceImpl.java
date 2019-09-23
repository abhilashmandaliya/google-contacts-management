package app.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.inject.Inject;

import app.config.AppConfig;
import app.service.URLGeneratorService;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject))
public class URLGeneratorServiceImpl implements URLGeneratorService {

  private AppConfig appConfig;

  @Override
  public String getUserConsentYRLForPeopleAPI() {
    return new GoogleAuthorizationCodeRequestUrl(appConfig.getClientId(), appConfig.getRedirectionURL(), appConfig.getScopes()).build();
  }

}
