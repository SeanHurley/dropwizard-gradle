package com.thathurleyguy;

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
        // nothing to do yet
    }

    @Override
    public void run(CasseroleConfiguration configuration,
                    Environment environment) {
        final CasseroleResource resource = new CasseroleResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}
