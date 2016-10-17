package com.github.sparkboot.driver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

@Profile("dev")
@PropertySource("classpath:spark-dev.properties")
public class SparkDevContextConfiguration extends AbstractSparkContextConfiguration {

    @Autowired
    public SparkDevContextConfiguration(ConfigurableEnvironment env) {
        super(env);
    }
}
