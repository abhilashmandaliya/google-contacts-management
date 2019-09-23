package app;

import com.google.inject.Singleton;

import app.config.AppConfig;
import app.service.ContactManagementService;
import app.service.PeopleDataFetcherService;
import app.service.URLGeneratorService;
import app.service.impl.ContactManagementServiceImpl;
import app.service.impl.DefaultPeopleDataFetcherServiceImpl;
import app.service.impl.URLGeneratorServiceImpl;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class AppModule extends DropwizardAwareModule<AppConfig> {

  @Override
  protected void configure() {
    super.configure();
    bind(URLGeneratorService.class).to(URLGeneratorServiceImpl.class).in(Singleton.class);
    bind(PeopleDataFetcherService.class).to(DefaultPeopleDataFetcherServiceImpl.class).in(Singleton.class);
    bind(ContactManagementService.class).to(ContactManagementServiceImpl.class).in(Singleton.class);
  }
}
