package web;

import com.google.api.services.people.v1.model.Person;
import com.google.inject.Inject;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import app.context.CurrentThreadContext;
import app.service.ContactManagementService;
import app.service.URLGeneratorService;
import app.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("/auth")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Inject))
public class AuthController {

  private ContactManagementService contactManagementService;
  private URLGeneratorService urlGeneratorService;

  @GET
  @Path("/redirect/code")
  public String createContacts(@QueryParam("code") String code) {
    try {
      CurrentThreadContext.setAuthCode(code);
      final Collection<Person> contacts = contactManagementService.createContacts();
      return JsonUtil.toJson(contacts.stream().map(Person::getNames).collect(Collectors.toList()));
    } catch (Throwable t) {
      log.error("Something went wrong while executing the request", t);
      return t.getMessage();
    } finally {
      CurrentThreadContext.resetAuthCode();
    }
  }

  @GET
  @Path("/consent")
  public Response createContact() {
    return Response.seeOther(UriBuilder.fromUri(urlGeneratorService.getUserConsentYRLForPeopleAPI()).build()).build();
  }
}
