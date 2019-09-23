package app.service;

import com.google.api.services.people.v1.model.Person;

import java.util.Collection;

public interface PeopleDataFetcherService {
  Collection<Person> getPeople();
}
