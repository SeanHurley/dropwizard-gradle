package com.thathurleyguy;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.thathurleyguy.resources.CasseroleResource;
import com.thathurleyguy.resources.PeopleResource;
import com.thathurleyguy.resources.PersonResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
    }

    @Override
    public void run(CasseroleConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {
        final CasseroleResource resource = new CasseroleResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        Session cassandraSession = getCassandraSession(configuration.getCassandraConfiguration());
        ResultSet execute = cassandraSession.execute("SELECT * FROM casserole.people");
        System.out.println(execute.all().toString());
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.jersey().register(new PeopleResource(cassandraSession));
        environment.jersey().register(new PersonResource());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }

    private Session getCassandraSession(CassandraConfiguration config) {
        Cluster cluster = Cluster.builder()
                .addContactPoint(config.getContactPoint())
                .build();
        Session session = cluster.connect(config.getKeyspace());

        session.execute("DROP KEYSPACE IF EXISTS " + config.getKeyspace());
        session.execute("CREATE KEYSPACE " + config.getKeyspace() +
                "  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };");
        session.execute("CREATE TABLE people (id uuid PRIMARY KEY, name text)");
        return session;
    }
}
