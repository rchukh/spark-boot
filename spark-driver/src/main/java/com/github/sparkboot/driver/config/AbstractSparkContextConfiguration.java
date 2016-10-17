package com.github.sparkboot.driver.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

@Configuration
@ComponentScan("com.github.sparkboot.*")
public abstract class AbstractSparkContextConfiguration {
    private final ConfigurableEnvironment env;

    public AbstractSparkContextConfiguration(ConfigurableEnvironment env) {
        this.env = env;
    }

    @Bean
    public SparkSession sparkSession() {
        Map<String, Object> sparkConfigs = new HashMap<>();
        for (org.springframework.core.env.PropertySource<?> propertySource : env.getPropertySources()) {
            if (propertySource instanceof MapPropertySource) {
                Map<String, Object> source = ((MapPropertySource) propertySource).getSource();
                sparkConfigs.putAll(source);
                sparkConfigs.entrySet().removeIf(entry -> !entry.getKey().startsWith("spark."));
            }
        }
        // Make use of SparkSession auto-configuration feature.
        // TODO: Honor already defined system properties (It will allow to override it externally, e.g. as docker container parameters)
        sparkConfigs.forEach((key, value) -> System.setProperty(key, value.toString()));
        return SparkSession.builder().getOrCreate();
    }
}
