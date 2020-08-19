package org.company.configurations;

import org.jboss.resteasy.annotations.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class ConfigurationDB {

    private static final Map<Integer, Configuration> configurationDB = new ConcurrentHashMap<>();
    private static final AtomicInteger idCounter = new AtomicInteger();

    public static Integer createConfiguration(String content, Status status) {
        Configuration c = new Configuration();
        c.setId(idCounter.incrementAndGet());
        c.setContent(content);
        c.setStatus(status);
        configurationDB.put(c.getId(), c);

        return c.getId();
    }

    public static Configuration getConfiguration(Integer id) {
        return configurationDB.get(id);
    }

    public static List<Configuration> getAllConfigurations() {
        return new ArrayList<>(configurationDB.values());
    }

    public static Configuration removeConfiguration(Integer id) {
        return configurationDB.remove(id);
    }

    public static Configuration updateConfiguration(Integer id, Configuration c) {
        return configurationDB.put(id, c);
    }
}