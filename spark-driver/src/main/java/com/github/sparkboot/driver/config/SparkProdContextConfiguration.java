package com.github.sparkboot.driver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

@Profile("prod")
@PropertySource("classpath:spark-prod.properties")
public class SparkProdContextConfiguration extends AbstractSparkContextConfiguration {

    @Autowired
    public SparkProdContextConfiguration(ConfigurableEnvironment env) {
        super(env);
    }
}
