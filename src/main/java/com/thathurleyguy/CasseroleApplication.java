package com.thathurleyguy;

import com.thathurleyguy.resources.CasseroleResource;
import com.thathurleyguy.resources.PeopleResource;
import com.thathurleyguy.resources.PersonResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class CasseroleApplication extends Application<CasseroleConfiguration> {

    public static void main(String[] args) throws Exception {
        new CasseroleApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<CasseroleConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<CasseroleConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(CasseroleConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(CasseroleConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {
        final CasseroleResource resource = new CasseroleResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        final PersonDAO dao = jdbi.onDemand(PersonDAO.class);
        environment.jersey().register(new PeopleResource(dao));
        environment.jersey().register(new PersonResource(dao));
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}
