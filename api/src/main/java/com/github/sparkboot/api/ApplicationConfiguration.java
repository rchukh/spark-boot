package com.github.sparkboot.api;

import com.github.sparkboot.driver.config.SparkDevContextConfiguration;
import com.github.sparkboot.driver.config.SparkProdContextConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.github.sparkboot.*")
@EnableConfigurationProperties
@Import(value = {SparkProdContextConfiguration.class, SparkDevContextConfiguration.class})
@SpringBootApplication
public class ApplicationConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfiguration.class, args);
    }

}
