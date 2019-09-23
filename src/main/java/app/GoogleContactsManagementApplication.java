package app;

import app.config.AppConfig;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class GoogleContactsManagementApplication extends Application<AppConfig> {

  public static void main(String[] args) throws Exception {
    new GoogleContactsManagementApplication().run(args);
  }

  @Override
  public void run(AppConfig configuration, Environment environment) throws Exception {
  }

  @Override
  public void initialize(Bootstrap<AppConfig> bootstrap) {
    bootstrap.addBundle(GuiceBundle.builder()
                                   .enableAutoConfig("app", "web")
                                   .modules(new AppModule())
                                   .build());
  }
}
