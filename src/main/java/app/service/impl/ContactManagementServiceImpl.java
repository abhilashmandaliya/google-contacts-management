package app.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleService.Builder;
import com.google.api.services.people.v1.PeopleService.People;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import app.config.AppConfig;
import app.context.CurrentThreadContext;
import app.service.ContactManagementService;
import app.service.PeopleDataFetcherService;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject))
public class ContactManagementServiceImpl implements ContactManagementService {

  private AppConfig appConfig;
  private PeopleDataFetcherService peopleDataFetcherService;

  @Override
  public PeopleService getPeopleService() throws IOException {
    HttpTransport httpTransport = new NetHttpTransport();
    JacksonFactory jsonFactory = new JacksonFactory();
    GoogleTokenResponse tokenResponse = getGoogleAuthorizationCodeTokenRequest(httpTransport, jsonFactory).execute();
    GoogleCredential credential = getCredential(httpTransport, jsonFactory, tokenResponse);
    return new Builder(httpTransport, jsonFactory, credential).build();
  }

  private GoogleCredential getCredential(HttpTransport httpTransport, JacksonFactory jsonFactory, GoogleTokenResponse tokenResponse) {
    return new GoogleCredential.Builder()
        .setTransport(httpTransport)
        .setJsonFactory(jsonFactory)
        .setClientSecrets(appConfig.getClientId(), appConfig.getClientSecret())
        .build()
        .setFromTokenResponse(tokenResponse);
  }

  private GoogleAuthorizationCodeTokenRequest getGoogleAuthorizationCodeTokenRequest(HttpTransport httpTransport,
                                                                                     JacksonFactory jsonFactory) {
    return new GoogleAuthorizationCodeTokenRequest(
        httpTransport, jsonFactory, appConfig.getClientId(), appConfig.getClientSecret(), CurrentThreadContext.getAuthCode(),
        appConfig.getRedirectionURL());
  }

  @Override
  public Collection<Person> createContacts() throws IOException {
    final People people = getPeopleService().people();
    final Collection<Person> contactsToCreate = peopleDataFetcherService.getPeople();
    final Collection<Person> createdContacts = new ArrayList<>(contactsToCreate.size());
    for (Person person : contactsToCreate) {
      createdContacts.add(people.createContact(person).execute());
    }
    return createdContacts;
  }

  private List<PhoneNumber> getPhoneNumbers(String s, Set<String> invalidEntries) {
    return Arrays.stream(s.split(",")).map(str -> {
      try {
        Long.parseLong(str);
      }
      catch (Exception e) {
        invalidEntries.add(str);
      }
      final PhoneNumber phoneNumber = new PhoneNumber();
      phoneNumber.setValue(str.trim());
      return phoneNumber;
    }).collect(Collectors.toList());
  }


}
