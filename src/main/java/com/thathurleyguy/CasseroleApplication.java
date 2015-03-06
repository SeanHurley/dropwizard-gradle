package com.thathurleyguy;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.thathurleyguy.configuration.CassandraConfiguration;
import com.thathurleyguy.configuration.CasseroleConfiguration;
import com.thathurleyguy.configuration.RedisConfiguration;
import com.thathurleyguy.resources.GroupResource;
import com.thathurleyguy.resources.ItemResource;
import com.thathurleyguy.resources.ItemsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.Jedis;

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
        Session cassandraSession = getCassandraSession(configuration.getCassandraConfiguration());
        Jedis jedis = getJedisSession(configuration.getRedisConfiguration());
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.jersey().register(new ItemResource(cassandraSession, jedis));
        environment.jersey().register(new ItemsResource(cassandraSession, jedis));
        environment.jersey().register(new GroupResource(cassandraSession, jedis));
        environment.healthChecks().register("template", healthCheck);
    }

    private Session getCassandraSession(CassandraConfiguration config) {
        Cluster cluster = Cluster.builder()
                .addContactPoint(config.getContactPoint())
                .build();
        Session session = cluster.connect(config.getKeyspace());

        session.execute("DROP KEYSPACE IF EXISTS " + config.getKeyspace());
        session.execute("CREATE KEYSPACE " + config.getKeyspace() +
                "  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };");
        session.execute("CREATE TABLE users (id uuid PRIMARY KEY, name text)");
        session.execute("CREATE TABLE items ( group_id text, item_id text, data text, PRIMARY KEY (group_id, item_id) ) WITH CLUSTERING ORDER BY (item_id ASC)");
        return session;
    }

    private Jedis getJedisSession(RedisConfiguration config) {
        Jedis jedis = new Jedis(config.getHostname(), Integer.valueOf(config.getPort()));
        return jedis;
    }
}
