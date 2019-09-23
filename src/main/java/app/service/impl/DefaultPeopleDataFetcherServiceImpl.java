package app.service.impl;

import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

import java.util.Collection;
import java.util.Collections;

import app.service.PeopleDataFetcherService;

public class DefaultPeopleDataFetcherServiceImpl implements PeopleDataFetcherService {

  @Override
  public Collection<Person> getPeople() {
    final Name name = new Name();
    name.setGivenName("Narendra");
    name.setMiddleName("Damodardas");
    name.setFamilyName("Modi");
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setValue("+919876543210");
    final Person person = new Person();
    person.setNames(Collections.singletonList(name));
    person.setPhoneNumbers(Collections.singletonList(phoneNumber));
    return Collections.singletonList(person);
  }
}
