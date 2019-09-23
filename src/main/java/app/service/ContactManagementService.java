package app.service;

import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;

import java.io.IOException;
import java.util.Collection;

public interface ContactManagementService {
  PeopleService getPeopleService() throws IOException;

  Collection<Person> createContacts() throws IOException;
}
